package com.company.java.levelp.level2.clientserverapplication.client;

import com.company.java.levelp.level2.clientserverapplication.client.controller.Controller;

public interface ClientModel {
    void initialize();
    void authorization(String login, String password);
    void registration(String login, String password);
    void setController(Controller controller);
    void getOnlineUsers();
    void exit();
    void noNameExit();
    void sendMessage(String text);
    void sendMessageTo(String text, String user);
}














