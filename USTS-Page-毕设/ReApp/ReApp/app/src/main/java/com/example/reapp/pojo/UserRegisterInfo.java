package com.example.reapp.pojo;
/**
 * Auto-generated: 2021-03-19 12:26:43
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class UserRegisterInfo {

    private String userName;
    private String userPassword;
    private String userLoginName;
    private String userEmail;

    public UserRegisterInfo(String userName, String userPassword, String userLoginName, String userEmail) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userLoginName = userLoginName;
        this.userEmail = userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }
    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserEmail() {
        return userEmail;
    }

}
