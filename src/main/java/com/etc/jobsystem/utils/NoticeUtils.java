package com.etc.jobsystem.utils;

import com.alibaba.fastjson.JSON;
import com.etc.jobsystem.entity.Company;
import com.etc.jobsystem.entity.Factory.MyProcess;
import com.etc.jobsystem.entity.Notice;
import com.etc.jobsystem.entity.User;
import org.springframework.data.relational.core.sql.In;

import java.util.Objects;

public class NoticeUtils {

    //不同的内容只需要添加新的process子类即可

    /*
    将消息类转换成加工的消息类以此来获取消息msg
    *   这个方法只是为了将string的对象转换成javabean后 返回单独的内容 发送者名字之类的 用于书写名称
    * */
    public static MyProcess getProcessNotice(Notice notice){
        //转换出发送者和接受者
        User sender = JSON.parseObject(notice.getNoticeSender(), User.class);
        User receiver = JSON.parseObject(notice.getNoticeReceiver(), User.class);
        Company company =JSON.parseObject(notice.getNoticeContent(), Company.class);
        //这里会有空指针问题
        //判断这些javabean中的值不为空才传入负责返回null
        String sendername=null;
        String receivername=null;
        String contentname=null;
        if (!Objects.isNull(sender)){sendername=sender.getUserName();}
        if (!Objects.isNull(receiver)){receivername=receiver.getUserName();}
        if (!Objects.isNull(company)){contentname=company.getCompanyName();}
        return new MyProcess(sendername,receivername,contentname);
    }

    //创建一个新的消息 由传入的javabean作为内容 返回消息 将javabean转换成String
    public static Notice getNoticeFromJavaBeanToString(User sender,User receiver,Company company,int option){
        String senderBO =Objects.isNull(sender)?null: JSON.toJSONString(sender);
        String receiverBO = Objects.isNull(receiver)?null:JSON.toJSONString(receiver);
        String companyBO = Objects.isNull(company)?null: JSON.toJSONString(company);
        Integer senderid=Objects.isNull(sender)?null:sender.getUserId();
        Integer receiverid=Objects.isNull(receiver)?null:sender.getUserId();
        //获取三种信息的string类型
        return new Notice(senderid,senderBO,receiverid,receiverBO,companyBO,option);
    }

    //反转
    public static Notice ReverseSender(Notice notice,int option){

        return new Notice(notice.getNoticeReceiverId(),notice.getNoticeReceiver(),notice.getNoticeSenderId(), notice.getNoticeSender(),notice.getNoticeContent(),option);
    }
}
