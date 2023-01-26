package com.etc.jobsystem.controller.back;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.etc.jobsystem.entity.*;
import com.etc.jobsystem.enums.AppHttpCodeEnum;
import com.etc.jobsystem.exception.MyException;
import com.etc.jobsystem.exception.enums.StateEnums;
import com.etc.jobsystem.resolve.CurrentUser;
import com.etc.jobsystem.service.CompanyHrService;
import com.etc.jobsystem.service.CompanyService;
import com.etc.jobsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RestController
@RequestMapping("company")
public class CompanyController {

    @Resource
    UserService userService;
    @Resource
    CompanyService companyService;
    @Autowired
    CompanyHrService companyHrService;
    //重入锁
    private final ReentrantLock reentrantLock = new ReentrantLock();
    //读写锁
    private final ReentrantReadWriteLock RWLock = new ReentrantReadWriteLock();
    private final Lock rlock = RWLock.readLock();


//----------------Get请求的部分--------------Get请求的部分---------------------Get请求的部分----------------------------


    //分页查询
    @GetMapping("/admin/page/{current}/{limit}")
    public JsonResult<Company> page(@PathVariable long current, @PathVariable long limit) {
        Page<Company> objectPage = new Page<>(current, limit);
        companyService.page(objectPage, null);
        long total = objectPage.getTotal();
        List<Company> records = objectPage.getRecords();
        return new JsonResult<>(total, records);

    }

    //查询公司详细信息 - 根据ownerId
    @GetMapping("getByOwnerId/{ownerId}")
    public JsonResult getInfoByOwnerId(@PathVariable int ownerId) {
        try {
            //读锁
            rlock.lock();
            QueryWrapper<Company> queryWrapper =
                    new QueryWrapper<Company>().eq("company_ownerid", ownerId);
            Company c = companyService.getOne(queryWrapper);
            if (c != null) {
                return new JsonResult(c);
            }

        } finally {
            rlock.unlock();
        }
        return new JsonResult(StateEnums.FIND_ERROR.getMessage(), "查询异常");
    }

    //查看全部公司
    @GetMapping("getAll")
    public JsonResult getAllCompany() {
        return new JsonResult(companyService.list());
    }


    //获取一个公司所有的hr  传入用户id
    @GetMapping("getAllHr")
    public ResponseResult getAllHr(@CurrentUser User user) {
//        System.out.println("这是用户id" + ownerId);
        return companyHrService.getAllHr(String.valueOf(user.getUserId()));
    }

    //根据凭证查找公司是否存在
    @GetMapping("getByProof")
    public ResponseResult getByProof(String companyProof) {

        long count = companyService.count(new LambdaQueryWrapper<Company>().eq(Company::getCompanyProof, companyProof));
        if (count <= 0) return ResponseResult.errorResult(AppHttpCodeEnum.COMPANYCODE_ERRO);
        return ResponseResult.okResult(true);
    }


    //获取当前用户对应的公司信息
    @GetMapping("getCompanyByCurrentHr")
    public ResponseResult getCompanyByCurrentHr(@CurrentUser User user) {

        /**
         * 1.用当前id获取hrcompany的companyid
         * 2.查找公司
         * */
        //1
        CompanyHr hr = companyHrService.getOne(new LambdaQueryWrapper<CompanyHr>().eq(CompanyHr::getHrId, user.getUserId()));
        if (Objects.isNull(hr)) return ResponseResult.okResult("没找到hr");
        //2
        Company company = companyService.getOne(new LambdaQueryWrapper<Company>().eq(Company::getCompanyId, hr.getCompanyId()));
        if (Objects.isNull(company)) return ResponseResult.okResult("没找到公司");

        return ResponseResult.okResult(company);
    }
//----------------Post请求的部分---------------Post请求的部分-------------------Post请求的部分----------------------------

    //公司创建，不传入用户的ID
    @PostMapping("/admin/create")
    public JsonResult createC(Company company) {
        if (companyService.save(company)) {
            return new JsonResult(StateEnums.SUCCESS.getCode(), "成功");
        } else {
            throw new MyException(StateEnums.REMOVE_ERROR.getCode(), "失败");
        }
    }

    //公司创建,传入用户的id
    @PostMapping("createCompany/{ownerId}")
    public JsonResult createCompany(@PathVariable int ownerId, @Valid Company company, BindingResult br) {
        //先判断 @valid注解是否生效
        if (br.hasErrors()) {
            throw new MyException(StateEnums.ADD_ERROR.getCode(), "公司数据不全");
        }
        try {
            //上锁
            reentrantLock.lock();
            company.setCompanyOwnerid(ownerId);
            if (companyService.save(company)) {
                //将用户权限设为 3
                User user = userService.getById(ownerId);
                user.setUserRole("3");
                userService.updateById(user);
                return new JsonResult(StateEnums.ADD_SUCCESS.getMessage());
            }

        } catch (Exception e) {
            throw new MyException(StateEnums.ADD_ERROR.getCode(), "公司添加异常");
        } finally {
            reentrantLock.unlock();
        }
        return new JsonResult("后端操作异常");
    }


//----------------Put请求的部分---------------Put请求的部分-------------------Put请求的部分----------------------------


    //公司修改信息 - 根据CompanyID
    @PutMapping("/admin")
    public synchronized JsonResult updateCompany(@Valid Company company) {
        //构造对象信息-查找公司
        // QueryWrapper<Company> q  = new QueryWrapper<Company>().eq("company_id",company.getCompanyId());
        if (companyService.getById(company.getCompanyId()) != null) {

            if (companyService.updateById(company)) {
                return new JsonResult(StateEnums.SUCCESS.getCode(), "修改成功");
            } else {
                return new JsonResult(StateEnums.EDIT_ERROR.getCode(), "修改失败");
            }
        } else {
            return new JsonResult("改公司不存在");
        }

    }

    //----------------Delete请求的部分---------------Delete请求的部分-------------------Delete请求的部分----------------------------
    @DeleteMapping("/admin/{companyId}")
    public JsonResult deleteCompany(@PathVariable int companyId) {
        if (companyService.removeById(companyId)) {
            return new JsonResult("成功删除");

        } else {
            throw new MyException(StateEnums.FAIL.getCode(), "删除失败");
        }
    }


}
