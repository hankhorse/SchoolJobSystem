package com.etc.jobsystem.VO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etc.jobsystem.entity.Notice;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestVO {
    private Notice notice;
    private JSONObject noticeSender;

    private JSONObject noticeReceiver;

    private JSONObject noticeContent;

    public TestVO(Notice notice) {
        this.notice = notice;
        this.noticeSender = JSON.parseObject(notice.getNoticeSender());
        this.noticeReceiver = JSON.parseObject(notice.getNoticeReceiver());
        this.noticeContent = JSON.parseObject(notice.getNoticeContent());
    }
}
