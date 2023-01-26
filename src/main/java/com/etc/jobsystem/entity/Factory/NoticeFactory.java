package com.etc.jobsystem.entity.Factory;

import com.etc.jobsystem.entity.Notice;
import com.etc.jobsystem.utils.NoticeUtils;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class NoticeFactory {
    protected MyProcess process;
    protected Notice notice;


    protected abstract String getMsg();

    protected abstract void getProcessClazz(Notice notice);
    //模板方法 流程固定  返回修改了msg的消息
    //任何消息方法只需要注入spring容器后调用这个方法传入消息就可以获得修改后的消息信息
    /*
    *    操作流程：
    *    创建一个消息对象   调用utils中的方法将需要传入的javabean对象转换成string 获取notice对象
    *    将需要的消息名称注入容器中 直接调用消息的getnotice方法即可获得全新的notice
    *
    * */
    public   Notice getNotice(Notice notice){
        notice.setNoticeId(null);
        this.notice=notice;
        //先调用第二层的方法将notice对象中的javabean转换的string对象的名称剥离出来存入process中
       this.getProcessClazz(notice);
       //调用最底层子类重写的方法设置消息的内容
        String msg = this.getMsg();
        this.notice.setNoticeMsg(msg);
        return this.notice;
    }


}
