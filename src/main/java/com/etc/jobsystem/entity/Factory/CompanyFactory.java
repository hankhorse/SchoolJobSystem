package com.etc.jobsystem.entity.Factory;

import com.etc.jobsystem.entity.Notice;
import com.etc.jobsystem.utils.NoticeUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class CompanyFactory extends NoticeFactory{


    protected abstract String getMsg();

    //传入一个消息类 进行加工 获取process  每一个process都不同 自己定义消息的内容
    protected void getProcessClazz(Notice notice) {
        MyProcess processNotice = NoticeUtils.getProcessNotice(notice);
        this.process = processNotice;
    }

    }
