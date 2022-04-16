public class DefaultdatabaseURL {

    private String url;
    private String user;
    private String password;


    public DefaultdatabaseURL(){

        url = "jdbc:mysql://127.0.0.1:3306/project";
        user = "root";
        password = "Tom_yin0818";

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
