package com.etc.jobsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.etc.jobsystem.entity.Notice;
import com.etc.jobsystem.entity.ResponseResult;
import org.springframework.transaction.annotation.Transactional;


public interface NoticeService extends IService<Notice> {


//    ResponseResult sendNotice(Notice notice);

    Notice RequireAddCompanyMsg(Notice notice);

    Notice AdminPassAddCompanyMsg(Notice notice);

    Notice AdminRefuseAddCompany(Notice notice);
}
