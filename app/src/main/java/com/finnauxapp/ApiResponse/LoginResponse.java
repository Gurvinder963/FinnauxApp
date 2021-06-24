package com.finnauxapp.ApiResponse;

public class LoginResponse {


    /**
     * CODE : 1
     * EmpId : 3
     * FirstName : Ram
     * Email : Ram@gmail.com
     * ProfilePic : ashok.png
     * RoleId : 3
     * RoleName : DataEntry
     * Token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjMiLCJuYmYiOjE2MDI4MzQxMTgsImV4cCI6MTYwMzAwNjkxOCwiaWF0IjoxNjAyODM0MTE4fQ.kdrfzwrPwdRS2SpuNHgt71F8Te4nDeNKzmpncLtYs2c
     * Emp_Password : 123456
     */

    private int CODE;
    private int EmpId;
    private String FirstName;
    private String Email;
    private String ProfilePic;
    private int RoleId;
    private String RoleName;
    private String Token;
    private String Emp_Password;

    public int getCODE() {
        return CODE;
    }

    public void setCODE(int CODE) {
        this.CODE = CODE;
    }

    public int getEmpId() {
        return EmpId;
    }

    public void setEmpId(int EmpId) {
        this.EmpId = EmpId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String ProfilePic) {
        this.ProfilePic = ProfilePic;
    }

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int RoleId) {
        this.RoleId = RoleId;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getEmp_Password() {
        return Emp_Password;
    }

    public void setEmp_Password(String Emp_Password) {
        this.Emp_Password = Emp_Password;
    }
}
