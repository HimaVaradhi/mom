package in.amruthashala.momapp.requestmodel;

public class Dataresponse {
    public String access_token;
    public String refresh_token;
    public UserDataresponse user_data;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public UserDataresponse getUser_data() {
        return user_data;
    }

    public void setUser_data(UserDataresponse user_data) {
        this.user_data = user_data;
    }
}
