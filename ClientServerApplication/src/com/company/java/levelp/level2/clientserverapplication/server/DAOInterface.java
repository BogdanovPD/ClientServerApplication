package com.company.java.levelp.level2.clientserverapplication.server;

import com.company.java.levelp.level2.clientserverapplication.MessageContainer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public interface DAOInterface {
    void addMessage(MessageContainer messageContainer);
    ArrayList<String> getUnread(String receiver, Date date);
    void markRead(String receiver);
    Map<String, String> getUsers();
    boolean getAuthorizationResult(String username, String password);
    void setOffline(String username);
    void setOnline(String username);
    int userOnline(String username);
    void addNewUser(String login, String password);
}










