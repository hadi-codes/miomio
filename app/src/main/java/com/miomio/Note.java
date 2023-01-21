package com.miomio;

import java.io.Serializable;

/**
 * Note class that represents a note
 */
public class Note implements Serializable {


    public static final String NOTE = "NOTE";
    private long id;

    private final long creatdAt;

    private String title;

    private String content;

    /**
     * The is of the note
     *
     * @return long id
     */
    public long getId() {
        return id;
    }

    /**
     * Timestamp of when the note was created
     *
     * @return long timestamp
     */
    public long getCreatedAt() {
        return creatdAt;
    }

    /**
     * The title of the note
     *
     * @return String title
     */
    public String getTitle() {
        return title;
    }

    /**
     * The content of the note
     *
     * @return String content
     */
    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Used after adding a new note to the database
     * to set the id of the note
     *
     * @param id of the note
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Used to set the content of the note
     *
     * @param content of the note
     */
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
