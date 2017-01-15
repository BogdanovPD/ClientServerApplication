package com.company.java.levelp.level2.clientserverapplication.client.view;

import com.company.java.levelp.level2.clientserverapplication.client.controller.Controller;

import java.util.ArrayList;

public interface ClientGUI {
    void build();
    void setController(Controller controller);
    void authorization();
    void sendMessage(String message);
    void setOnline(ArrayList<String> list);
    void setAuthResult(boolean result);
    void setRegResult(boolean result);
    void newMessageToChat(String text);
}













