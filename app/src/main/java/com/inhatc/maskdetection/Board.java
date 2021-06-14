package com.inhatc.maskdetection;

public class Board {
    private String id;
    private String title;
    private String content;
    private String regdet;

    public Board(){};
    public Board(String id, String title, String content, String regdet) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.regdet = regdet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRegdet() {
        return regdet;
    }

    public void setRegdet(String regdet) {
        this.regdet = regdet;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", regdet='" + regdet + '\'' +
                '}';
    }
}
