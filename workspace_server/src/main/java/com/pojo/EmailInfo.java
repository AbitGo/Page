package com.pojo;

public class EmailInfo {
    private String verificationCode;
    private String subject;

    public EmailInfo(String subject,String verificationCode) {
        this.verificationCode = verificationCode;
        this.subject = subject;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
