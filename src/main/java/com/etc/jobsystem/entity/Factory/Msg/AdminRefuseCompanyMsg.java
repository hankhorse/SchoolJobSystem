package com.etc.jobsystem.entity.Factory.Msg;

import com.etc.jobsystem.entity.Factory.CompanyFactory;
import com.etc.jobsystem.enums.notice.MsgTypeEnum;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AdminRefuseCompanyMsg extends CompanyFactory {

    int noticeMsgType = MsgTypeEnum.NOT_PASS_ADD_COMPANY.getCode();
    public AdminRefuseCompanyMsg() {
        super();
    }

    @Override
    protected String getMsg() {
        this.notice.setNoticeMsgType(noticeMsgType);
        return "管理员拒绝你添加的公司" +this.process.getNoticeContent() +" 请重新申请";
    }


}
