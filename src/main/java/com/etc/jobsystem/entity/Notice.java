package com.etc.jobsystem.entity;
import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.etc.jobsystem.enums.notice.IsReadEnum;
import com.etc.jobsystem.enums.notice.ResultEnum;
import lombok.*;
/**
 * (ProcessNotice)实体类
 *
 * @author makejava
 * @since 2022-11-07 13:08:04
 */
@Data
@NoArgsConstructor
@TableName("notice")
public class Notice implements Serializable {
    private static final long serialVersionUID = -62272998536044715L;

    @TableId(type = IdType.AUTO)
    private Integer noticeId;

    private Integer noticeSenderId;

    private String noticeSender;

    private Integer noticeReceiverId;

    private String noticeReceiver;

    private String noticeContent;

    private String noticeMsg;

    private Date createTime;

    /**消息的类型
     *    1为添加公司
     * */
    private Integer noticeMsgType;
    /**
     * 0为已读 1为未读
     */
    private Integer noticeIsread;
    /**
     * 消息的通知类型 0：通知消息  1：操作消息
     */
    private Integer noticeActionType;
    /**
     * 消息的结果  -1：未审核 0：通过 1：未通过
     */
    private Integer noticeResult;



    public Notice(Integer senderId,String senderBO,Integer receiverId, String receiverBO, String contentBO,int option) {
        this.noticeSenderId=senderId;
        this.noticeSender = senderBO;
        this.noticeReceiverId=receiverId;
        this.noticeReceiver = receiverBO;
        this.noticeContent = contentBO;
        this.createTime=new Date();
        //设置为未读
        this.noticeIsread= IsReadEnum.NOT_READ.getCode();
        //设置未操作
        this.noticeResult= ResultEnum.NONE_OPTION.getCode();
        //设置操作类型
        this.noticeActionType=option;

    }



}

