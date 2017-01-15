package com.company.java.levelp.level2.clientserverapplication.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "message_history")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long messageId;
    @Column(name="autor")
    private String autor;
    @Column(name="message")
    private String message;
    @Column(name="receiver")
    private String receiver;
    @Column(name="time")
    private Timestamp time;
    @Column(name="isRead")
    private Boolean isRead;

    public Message(String autor, String message, String receiver,
                   Timestamp time, Boolean isRead) {
        this.autor = autor;
        this.message = message;
        this.receiver = receiver;
        this.time = time;
        this.isRead = isRead;
    }

    public Message() {
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}











