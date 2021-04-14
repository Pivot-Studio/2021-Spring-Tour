package pers.zhang.pojo;

public class Blog {
    private int id;
    private int authorId;
    private String content;

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", content='" + content + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
