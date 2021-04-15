package pers.zhang.utils;

public class JudgementUtile {
    private String status;

    public void setStatus(int judgement){
        if(judgement!=0){
            status="OK";
        }else {
            status="FALSE";
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
