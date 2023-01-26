package com.etc.jobsystem.controller.font;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.etc.jobsystem.DTO.JobDTO;
import com.etc.jobsystem.VO.JobVO;
import com.etc.jobsystem.entity.*;
import com.etc.jobsystem.exception.MyException;
import com.etc.jobsystem.exception.enums.StateEnums;
import com.etc.jobsystem.mapper.JobMapper;
import com.etc.jobsystem.resolve.CurrentUser;
import com.etc.jobsystem.service.CompanyHrService;
import com.etc.jobsystem.service.CompanyService;
import com.etc.jobsystem.service.JobService;
import com.etc.jobsystem.service.UserService;
import com.etc.jobsystem.utils.BeanCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestMapping("job")
@RestController
public class JobController {

    //读锁
    ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private final Lock rlock = reentrantReadWriteLock.writeLock();

    @Resource
    JobService jobService;

    @Resource
    UserService userService;

    @Resource
    CompanyService companyService;

    @Resource
    JobMapper jobMapper;

    @Resource
    CompanyHrService companyHrService;

//----------------Get请求的部分--------------Get请求的部分---------------------Get请求的部分----------------------------


    //分页查询
    @GetMapping("admin/{current}/{limit}")
    public JsonResult<JobVO> adminPage(@PathVariable long current, @PathVariable long limit) {
        Page<Job> objectPage = new Page<>(current, limit);
        jobService.page(objectPage, null);
        long total = objectPage.getTotal();
        List<Job> records = objectPage.getRecords();
        List<JobVO> jobVOList = records.stream()
                .map(job -> {
                    JobVO jobVO = BeanCopyUtils.copyBean(job, JobVO.class);
                    //查找公司和用户
                    User hr = userService.getById(jobVO.getUserId());
                    CompanyHr link = companyHrService.getOne(new LambdaQueryWrapper<CompanyHr>().eq(CompanyHr::getHrId, hr.getUserId()));
                    Company company = companyService.getById(link.getCompanyId());
                    jobVO.set(hr.getUserName(), company);
                    return jobVO;
                })
                .collect(Collectors.toList());

        return new JsonResult<JobVO>(total, jobVOList);

    }

    //HR分页查询  需要拦截
    @GetMapping("{current}/{limit}")
    public JsonResult<Job> page(@PathVariable long current, @PathVariable long limit, @CurrentUser User user) {

        Page<Job> objectPage = new Page<>(current, limit);
        LambdaQueryWrapper<Job> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Job::getUserId, user.getUserId());
        jobService.page(objectPage, lambdaQueryWrapper);
        long total = objectPage.getTotal();
        List<Job> records = objectPage.getRecords();
        return new JsonResult<Job>(total, records);

    }


    //普通用户 - 获取全部工作
    @GetMapping("getAll")
    public JsonResult getAllJob() {
        try {
            rlock.lock();
            //创建DTO
            List<JobDTO> list = new ArrayList<>();
            for (Job job : jobService.list()) {
                JobDTO jobDTO = new JobDTO(job);
                jobDTO.setHrName(userService.getById(
                        job.getUserId()).getUserName());
                jobDTO.setCompanyName(companyService.getOne(
                        new QueryWrapper<Company>()
                                .eq("company_id", companyHrService
                                        .getOne(new QueryWrapper<CompanyHr>()
                                                .eq("hr_id", job.getUserId())).getCompanyId()))
                        .getCompanyName());
                jobDTO.setCompanyAddress(companyService.getOne(
                        new QueryWrapper<Company>()
                                .eq("company_id", companyHrService
                                        .getOne(new QueryWrapper<CompanyHr>()
                                                .eq("hr_id", job.getUserId())).getCompanyId()))
                        .getCompanyAddress());

                list.add(jobDTO);
            }
            return new JsonResult(list);
        } finally {
            rlock.unlock();
        }
    }

    //  HR 根据userId 查询自己发布的工作
    @GetMapping("getByUserId/{userId}")
    public JsonResult getJobByUserId(@PathVariable int userId) {

        return new JsonResult
                (jobService.list(
                        new QueryWrapper<Job>().eq("user_id", userId)));

    }

    // 用户 根据 <工作名字> 来查询工作 - 模糊查询
    @GetMapping("getByJobName/{jobName}")
    public JsonResult getJobByJobName(@PathVariable String jobName) {

        return new JsonResult
                (jobService.list(
                        new QueryWrapper<Job>().like("job_name", jobName)));

    }

    @GetMapping("ss")
    public List get() {
        List list = jobMapper.selectObjs(new QueryWrapper<Job>().eq("user_id", 4));
        return list;
    }

//----------------Post请求的部分---------------Post请求的部分-------------------Post请求的部分----------------------------

    //新增工作 - 传入userId 懒得上锁了
    @PostMapping("createJob/{userId}")
    public JsonResult insertJob(@PathVariable int userId, @Valid Job job, BindingResult br) {
        if (br.hasErrors()) {
            throw new MyException(StateEnums.ADD_ERROR.getCode(), "前端数据填写不完整");
        }
        job.setUserId(userId);
        if (jobService.save(job)) {
            return new JsonResult(StateEnums.ADD_SUCCESS.getMessage());
        } else {
            throw new MyException(StateEnums.ADD_ERROR.getCode(), "添加异常");
        }
    }

//----------------Put请求的部分---------------Put请求的部分-------------------Put请求的部分----------------------------

    //修改工作信息 - 传回一个JOB对象
    @PutMapping("/admin")
    public JsonResult updateJob(Job job) {

        if (jobService.getById(job.getJobId()) != null) {
            if (jobService.updateById(job)) {
                return new JsonResult(StateEnums.SUCCESS.getMessage(), "修改成功");
            } else {
                throw new MyException(StateEnums.EDIT_ERROR.getCode(), "他奶奶滴，修改失败了，看看job数据有没有错");
            }
        } else {
            throw new MyException(StateEnums.EDIT_ERROR.getCode(), "job_id没传回后端");
        }
    }

//----------------Delete请求的部分--------------Delete请求的部分-------------------Delete请求的部分----------------------------


    //删除发布的工作 - 传回一个对象的jobId
    @DeleteMapping("admin/deleteByJobId/{jobId}")
    public JsonResult deleteById(@PathVariable int jobId) {
        if (jobService.removeById(jobId)) {
            return new JsonResult("删除成功");
        } else {
            throw new MyException(StateEnums.REMOVE_ERROR, "删除失败，该id不存在，傻逼小米售后什么时候去势阿？");

        }
    }
}
