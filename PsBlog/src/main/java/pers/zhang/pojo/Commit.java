package pers.zhang.pojo;

public class Commit {
    private int cid;
    private int commiter;
    private String ccontent;
    private int target;

    @Override
    public String toString() {
        return "Commit{" +
                "cid=" + cid +
                ", commiter=" + commiter +
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

    public int getCommiter() {
        return commiter;
    }

    public void setCommiter(int commiter) {
        this.commiter = commiter;
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
