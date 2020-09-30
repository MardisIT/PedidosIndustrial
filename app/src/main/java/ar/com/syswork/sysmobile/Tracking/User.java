package ar.com.syswork.sysmobile.Tracking;

public class User {
    private String Email;
    private String Password;

    public  User(){
        this.Email="sertecomcell@prospeccionclaro.com.ec";
        this.Password="00";

    }
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
