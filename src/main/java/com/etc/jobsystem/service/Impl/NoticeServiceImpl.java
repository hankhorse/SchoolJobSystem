package com.etc.jobsystem.service.Impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.etc.jobsystem.entity.Factory.Msg.AdminPassCompanyMsg;
import com.etc.jobsystem.entity.Factory.Msg.AdminRefuseCompanyMsg;
import com.etc.jobsystem.entity.Factory.Msg.RequireAddCompanyMsg;
import com.etc.jobsystem.entity.Notice;
import com.etc.jobsystem.entity.ResponseResult;
import com.etc.jobsystem.enums.notice.ActionTypeEnum;
import com.etc.jobsystem.mapper.NoticeMapper;
import com.etc.jobsystem.service.NoticeService;
import com.etc.jobsystem.utils.NoticeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    RequireAddCompanyMsg requireAddCompanyMsg;

@Resource
    AdminRefuseCompanyMsg adminRefuseCompanyMsg;

    @Autowired
    AdminPassCompanyMsg adminPassCompanyMsg;
    @Override
    public Notice RequireAddCompanyMsg(Notice notice) {
        Notice notice1 = requireAddCompanyMsg.getNotice(notice);
        baseMapper.insert(notice1);

        return notice1;
    }

    @Override
    public Notice AdminPassAddCompanyMsg(Notice notice) {
        //这个方法会将发送者和接受者反转
        Notice notice1 = NoticeUtils.ReverseSender(notice, ActionTypeEnum.NOTICE_TYPE.getCode());
        //添加了消息
        Notice notice2 = adminPassCompanyMsg.getNotice(notice1);
        //发送消息
        int a = baseMapper.insert(notice2);

        return notice2;
    }

    @Override
    public Notice AdminRefuseAddCompany(Notice notice) {
        //这个方法会将发送者和接受者反转
        Notice notice1 = NoticeUtils.ReverseSender(notice, ActionTypeEnum.NOTICE_TYPE.getCode());

        Notice notice2 = adminRefuseCompanyMsg.getNotice(notice1);

        //发送消息
        baseMapper.insert(notice2);
        return notice2;
    }


//    @Override
//    public ResponseResult sendNotice(Notice notice) {
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        simpleDateFormat.format(date);
//        System.out.println("当前日期为"+date);
//        notice.setCreateTime(date);
//         baseMapper.insert(notice);
//        return ResponseResult.okResult(new TestVO(notice));
//    }
}
