package com.company.java.levelp.level2.clientserverapplication.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="password")
    private String password;
    @Column(name="lastOfflineTime")
    private Timestamp lastOfflineTime;
    @Column(name="offline")
    private Boolean offline;

    public User() {
    }

    public User(String name, String password,
                Timestamp lastOfflineTime, Boolean offline) {
        this.name = name;
        this.password = password;
        this.lastOfflineTime = lastOfflineTime;
        this.offline = offline;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getLastOfflineTime() {
        return lastOfflineTime;
    }

    public void setLastOfflineTime(Timestamp lastOfflineTime) {
        this.lastOfflineTime = lastOfflineTime;
    }

    public Boolean isOffline() {
        return offline;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
    }
}











