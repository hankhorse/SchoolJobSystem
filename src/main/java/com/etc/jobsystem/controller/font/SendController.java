package com.etc.jobsystem.controller.font;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.etc.jobsystem.DTO.UserDTO;
import com.etc.jobsystem.VO.UserAllVO;
import com.etc.jobsystem.entity.*;
import com.etc.jobsystem.enums.AppHttpCodeEnum;
import com.etc.jobsystem.exception.MyException;
import com.etc.jobsystem.exception.enums.StateEnums;
import com.etc.jobsystem.mapper.JobMapper;
import com.etc.jobsystem.mapper.SendMapper;
import com.etc.jobsystem.resolve.CurrentUser;
import com.etc.jobsystem.service.JobService;
import com.etc.jobsystem.service.SendService;
import com.etc.jobsystem.service.UserInfoService;
import com.etc.jobsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("send")
public class SendController {

    @Resource
    SendService sendService;


    @Resource
    JobService jobService;

    @Resource
    UserService userService;

    @Resource
    UserInfoService userInfoService;

    @Resource
    JobMapper jobMapper;

//----------------Post请求的部分--------------Post请求的部分---------------------Post请求的部分----------------------------

    //添加Send -投递简历 - 传给后端userId 和 jobId
    @PostMapping("createSend/{jobId}")
    public ResponseResult createSend(@PathVariable int jobId, @CurrentUser User user) {

        Send send = new Send();
        send.setJobId(jobId);
        send.setUserId(user.getUserId());
        send.setSendId(0);
        long count = sendService.count(new LambdaQueryWrapper<Send>().eq(Send::getUserId, send.getUserId()).eq(Send::getJobId, jobId));
        if (count>=1){  //已经投递过
            return ResponseResult.errorResult(AppHttpCodeEnum.SEND_EXISTS);
        }
        if (sendService.save(send)) {
            return ResponseResult.okResult("添加成功");
        } else {
            throw new MyException(StateEnums.ADD_ERROR.getCode(), "添加异常");
        }
    }

//----------------Delete请求的部分--------------Delete请求的部分---------------------Delete请求的部分----------------------------


//----------------Put请求的部分--------------Put请求的部分---------------------Put请求的部分----------------------------


//----------------Get请求的部分--------------Get请求的部分---------------------Get请求的部分----------------------------

    //    //HR 根据工作 查看投递的人 - 传给后端jobId  这个需要拦截
    @GetMapping("getSend/{jobId}")
    public JsonResult getSend(@PathVariable int jobId) {
        LinkedList<UserAllVO> list = new LinkedList<>();
        for (Send send : sendService.list(new QueryWrapper<Send>().eq("job_id", jobId))) {
            User user = userService.getById(send.getUserId());
            UserInfo userInfo = userInfoService.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUserId, send.getUserId()));
            if (Objects.isNull(userInfo)) userInfo = new UserInfo();
            UserAllVO userAllVO = new UserAllVO(user, userInfo);
            list.add(userAllVO);
        }
        return new JsonResult(list);
    }

    //HR 根据自己，查看全部向自己投递的人
    //我不信谁能看得懂
    //...
    @GetMapping("getSendByUserId/{userId}")
    public JsonResult getSendByUsrid(@PathVariable int userId) {
        LinkedList<UserDTO> list = new LinkedList<>();

        List jobIdList = jobMapper.selectObjs(new QueryWrapper<Job>().eq("user_id", userId)); //{jobId:1,2,3}
        for (Object jobId : jobIdList) {
            for (Send send : sendService.list(new LambdaQueryWrapper<Send>().eq(Send::getJobId, jobId))) {
                UserDTO userDTO = new UserDTO(userService.getById(send.getUserId()));
                userDTO.setJobName(jobService.getById((int) jobId).getJobName());  //设置投递的名称
                list.add(userDTO);
            }
        }
        return new JsonResult(list);
    }


    //hr 获取单个工作的所有投递人的所有信息


}
