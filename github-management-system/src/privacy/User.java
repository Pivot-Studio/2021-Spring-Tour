package privacy;

public class User {
    private String name;
    private String password;
    private String access_token;
    private String myEmailAccount;
    private String myEmailPassword;
    public User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getMyEmailAccount() {
        return myEmailAccount;
    }

    public void setMyEmailAccount(String myEmailAccount) {
        this.myEmailAccount = myEmailAccount;
    }

    public String getMyEmailPassword() {
        return myEmailPassword;
    }

    public void setMyEmailPassword(String myEmailPassword) {
        this.myEmailPassword = myEmailPassword;
    }
}
