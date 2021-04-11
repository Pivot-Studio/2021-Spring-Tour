package pojo;

import org.springframework.stereotype.Component;

@Component
public class Blog {
    private String id;
    private String author;
    private String content;

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
