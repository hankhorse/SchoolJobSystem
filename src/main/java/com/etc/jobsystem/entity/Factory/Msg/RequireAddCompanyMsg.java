package com.etc.jobsystem.entity.Factory.Msg;

import com.etc.jobsystem.entity.Factory.CompanyFactory;
import com.etc.jobsystem.enums.notice.MsgTypeEnum;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class RequireAddCompanyMsg extends CompanyFactory {

    int noticeMsgType = MsgTypeEnum.ADD_COMPANY.getCode();
    public RequireAddCompanyMsg() {
        super();
    }

    @Override
    protected String getMsg() {
        this.notice.setNoticeMsgType(noticeMsgType);
        return "用户"+this.process.getNoticeSenderName()+ "请求添加公司：" +this.process.getNoticeContent();
    }


}
