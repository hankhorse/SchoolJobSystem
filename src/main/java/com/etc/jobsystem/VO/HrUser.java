package com.etc.jobsystem.VO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HrUser {

    /**
     * userPhone :
     * userEmail :
     * companyProof :
     */

    private String userPhone;
    private String userEmail;
    private String companyProof;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCompanyProof() {
        return companyProof;
    }

    public void setCompanyProof(String companyProof) {
        this.companyProof = companyProof;
    }
}
