package leftovers.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

/**
 * Created by Hiki on 2017/6/14.
 */
@Entity
@Table(name = "article")
public class Article {

    @Id
    @Column(name = "aid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int aid;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "username")
    private String username;

    @Column(name = "time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String time;

    @Column(name = "readNum")
    private int readNum;

    public Article() {
    }

    public Article(String title, String content, String username, String time, int readNum) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.time = time;
        this.readNum = readNum;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }
}
