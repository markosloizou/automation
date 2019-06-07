import sun.security.util.Password;

import java.util.Arrays;

public class AgencyCredentials {
    private Agency agency;
    private String uname;
    private char[] pwd;
    private String FTPuname;
    private char[] FTPpwd;

    public AgencyCredentials(Agency a, String name, char[] p, String ftpname, char[] ftpp) {
        agency = a;
        uname = name;
        pwd = p;
        FTPuname = ftpname;
        FTPpwd = ftpp;
    }

    public void clear()
    {
        pwd = new char[] {'a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a'};
        FTPpwd = new char[] {'a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a','a'};
    }

    public Agency getAgency() {
        return agency;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public char[] getPwd() {
        return pwd;
    }

    public void setPwd(char[] pwd) {
        this.pwd = pwd;
    }

    public String getFTPuname() {
        return FTPuname;
    }

    public char[] getFTPpwd() {
        return FTPpwd;
    }
}
