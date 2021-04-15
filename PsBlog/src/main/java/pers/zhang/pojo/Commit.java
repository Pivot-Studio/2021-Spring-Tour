package pers.zhang.pojo;

public class Commit {
    private int cid;
    private int committerId;
    private String ccontent;
    private int target;

    @Override
    public String toString() {
        return "Commit{" +
                "cid=" + cid +
                ", committerId=" + committerId +
                ", ccontent='" + ccontent + '\'' +
                ", target=" + target +
                '}';
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCommitterId() {
        return committerId;
    }

    public void setCommitterId(int committerId) {
        this.committerId = committerId;
    }

    public String getCcontent() {
        return ccontent;
    }

    public void setCcontent(String ccontent) {
        this.ccontent = ccontent;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
