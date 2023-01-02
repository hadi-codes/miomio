package com.miomio;

import java.time.LocalDateTime;
import java.util.UUID;

public class Note {

    private long id;

    private final long creatdAt;
    private String title;
    private String content;

    public long getId() {
        return id;
    }

    public long getCreatdAt() {
        return creatdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    Note() {
        this.creatdAt = System.currentTimeMillis();

    }

    Note(long id, String title, String content, long creatdAt) {
        this.id = id;
        this.creatdAt = creatdAt;
        this.title = title;
        this.content = content;
    }

}
