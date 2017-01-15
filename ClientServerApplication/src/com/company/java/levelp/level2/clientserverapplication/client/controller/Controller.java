package com.company.java.levelp.level2.clientserverapplication.client.controller;

import com.company.java.levelp.level2.clientserverapplication.client.ClientModel;
import com.company.java.levelp.level2.clientserverapplication.client.view.ClientGUI;

import java.util.ArrayList;

public interface Controller {
    void setModel(ClientModel model);
    void setView(ClientGUI view);
    void sendMessage(String message);
    void sendMessageTo(String message, String username);
    void receiveMessage(String message);
    void getOnline();
    void setOnline(ArrayList<String> list);
    void authorization(String login, String password);
    void authorizationResult(boolean result);
    void registration(String login, String password);
    void registrationResult(boolean result);
    void exit();
    void noNameExit();
}














