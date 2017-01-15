package com.company.java.levelp.level2.clientserverapplication;

public class MessageContainer implements Comparable {
    private String author;
    private String user;
    private String message;
    private long time;

    public MessageContainer(String author, String user, String message,
                            long time) {
        this.author = author;
        this.user = user;
        this.message = message;
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }

    @Override
    public int compareTo(Object o) {
        MessageContainer m2 = (MessageContainer)o;
        if (m2.getTime() > this.getTime())
            return -1;
        if (m2.getTime() < this.getTime())
            return 1;
        return 0;
    }

}









