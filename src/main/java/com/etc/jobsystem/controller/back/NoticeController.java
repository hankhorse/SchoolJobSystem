package com.etc.jobsystem.controller.back;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.etc.jobsystem.entity.Company;
import com.etc.jobsystem.entity.Factory.Msg.RequireAddCompanyMsg;
import com.etc.jobsystem.entity.Notice;
import com.etc.jobsystem.entity.ResponseResult;
import com.etc.jobsystem.entity.User;
import com.etc.jobsystem.enums.RoleEnum;
import com.etc.jobsystem.enums.notice.ActionTypeEnum;
import com.etc.jobsystem.resolve.CurrentUser;
import com.etc.jobsystem.service.CompanyService;
import com.etc.jobsystem.service.NoticeService;
import com.etc.jobsystem.service.UserService;
import com.etc.jobsystem.utils.NoticeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("notice")

public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @Autowired
    CompanyService companyService;

    @Resource
    UserService userService;

    @GetMapping()
    public ResponseResult getNotice(@CurrentUser User user){

        List<Notice> list = noticeService.list(new LambdaQueryWrapper<Notice>().eq(Notice::getNoticeReceiverId, user.getUserId()).orderByDesc(Notice::getCreateTime));
        return ResponseResult.okResult(list);

    }


//    @PostMapping("sendNotice")
//    @Transactional(rollbackFor = Exception.class)
//    public ResponseResult sendNotice(@CurrentUser User user){
////        System.out.println("接受的数据"+user);
//        Company company = companyService.getById(2);
//        String userBO = JSON.toJSONString(user);
//        String companyBO = JSON.toJSONString(company);
//        Notice notice = new Notice();
//        notice.setNoticeSender(userBO);
//        notice.setNoticeContent(companyBO);
//        return noticeService.sendNotice(notice);
//}

    /**
     *       传入 公司信息 发送请求
     * */
    @PostMapping("requireAddCompany")
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult requireAddCompany(@RequestBody Company company, @CurrentUser User user){
        System.out.println("接受的数据company"+company);
        System.out.println("接受的数据user"+user);
//        System.out.println("接受的数据"+company);
        //把javabean转换成notice类
        Notice firstNotice = NoticeUtils.getNoticeFromJavaBeanToString(user, null, company, ActionTypeEnum.ACTION_TYPE.getCode());
//        firstNotice.setNoticeByNoticeCodeEnum(NoticeCodeEnum.NONE_READ_ACTION_NOPASS);
        Notice notice = noticeService.RequireAddCompanyMsg(firstNotice);

        return ResponseResult.okResult(user);

    }
}
