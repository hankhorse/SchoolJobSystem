package com.etc.jobsystem.entity.Factory.Msg;

import com.etc.jobsystem.entity.Factory.CompanyFactory;
import com.etc.jobsystem.enums.notice.MsgTypeEnum;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AdminPassCompanyMsg extends CompanyFactory {

    int noticeMsgType = MsgTypeEnum.PASS_ADD_COMPANY.getCode();
    public AdminPassCompanyMsg() {
        super();
    }

    @Override
    protected String getMsg() {
        this.notice.setNoticeMsgType(noticeMsgType);
        return "管理员通过你添加的公司" +this.process.getNoticeContent();
    }


}
