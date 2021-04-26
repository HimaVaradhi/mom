package in.amruthashala.momapp.requestmodel;

public class Myloginresponse {
    public Dataresponse data;
    public String status;
    public int statuscode;

    public Dataresponse getData() {
        return data;
    }

    public void setData(Dataresponse data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }


}
