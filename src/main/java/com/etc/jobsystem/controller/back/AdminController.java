package com.etc.jobsystem.controller.back;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.etc.jobsystem.entity.Company;
import com.etc.jobsystem.entity.Notice;
import com.etc.jobsystem.entity.ResponseResult;
import com.etc.jobsystem.entity.User;
import com.etc.jobsystem.enums.RoleEnum;
import com.etc.jobsystem.enums.notice.ResultEnum;
import com.etc.jobsystem.service.CompanyService;
import com.etc.jobsystem.service.NoticeService;
import com.etc.jobsystem.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin")

public class AdminController {
    @Resource
    NoticeService noticeService;

    @Resource
    CompanyService companyService;

    @Resource
    UserService userService;


/**
 * 管理员同意添加公司   传入消息的id
* */

    @PutMapping("passAddCompany/{noticeId}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult PassAddCompany(@PathVariable int noticeId){
        //先修改本条消息的内容在进行其他操作
        Notice one = noticeService.getById(noticeId);
        one.setNoticeResult(ResultEnum.PASS.getCode());
        noticeService.updateById(one);
        //进行其他的操作
        //1.将公司加入这个发送者的名下
        //2.生成uuid作为其凭证
        //3.发送消息通知用户
        //4.修改用户角色
        //1
        Company company = JSON.parseObject(one.getNoticeContent(), Company.class);
        if (Objects.isNull(company) )return ResponseResult.okResult("公司为空");
        company.setCompanyOwnerid(one.getNoticeSenderId());

        //2
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        company.setCompanyProof(uuid);
        //修改公司
        companyService.save(company);
        //3      V
        Notice notice = noticeService.AdminPassAddCompanyMsg(one);

        //4
        User user = userService.getById(one.getNoticeSenderId());
        user.setUserRole(RoleEnum.COMPANY_ROLE.getCode());
        userService.updateById(user);

        return ResponseResult.okResult(notice);
    }


    @PutMapping("refuseAddCompany/{noticeId}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult refuseAddCompany(@PathVariable int noticeId){
        /**
         * 1.先修改本条消息的内容在进行其他操作
         * 2.发送消息给拥有者
         * */
        //1.先修改本条消息的内容在进行其他操作
        Notice one = noticeService.getById(noticeId);
        one.setNoticeResult(ResultEnum.NONE_PASS.getCode());
        noticeService.updateById(one);
        //2.发送消息给拥有者

        Notice notice = noticeService.AdminRefuseAddCompany(one);

        return ResponseResult.okResult(notice);
    }
//查看凭证展示公司信息
    @GetMapping("getCompanyByNotice/{noticeId}")
    public ResponseResult getCompanyByNotice(@PathVariable int noticeId){
        Notice notice = noticeService.getById(noticeId);
        String noticeContent = notice.getNoticeContent();
        Company company = JSON.parseObject(noticeContent, Company.class);
        return ResponseResult.okResult(company);

    }

    //展示所有消息
    @GetMapping("getAllNotice")
    public ResponseResult getAllNotice(){

        List<Notice> list1 = noticeService.list(new LambdaQueryWrapper<Notice>().orderByDesc(Notice::getCreateTime));
        List<Notice> list = list1.stream()
                .filter(notice -> notice.getNoticeReceiverId() == null)
                .collect(Collectors.toList());


        return ResponseResult.okResult(list);
    }

}
