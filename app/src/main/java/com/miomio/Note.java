package com.miomio;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Note implements Serializable {
    public static final String NOTE = "NOTE";

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

    /**
     * Used after adding a new note to the database
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    Note() {
        this.creatdAt = System.currentTimeMillis();
        this.title = "";
        this.content = "";

    }

    Note(long id, String title, String content, long creatdAt) {
        this.id = id;
        this.creatdAt = creatdAt;
        this.title = title;
        this.content = content;
    }

    boolean isEmpty() {
        return title.isEmpty() && content.isEmpty();
    }

}
