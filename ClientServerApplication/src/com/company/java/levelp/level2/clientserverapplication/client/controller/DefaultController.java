package com.company.java.levelp.level2.clientserverapplication.client.controller;

import com.company.java.levelp.level2.clientserverapplication.client.ClientModel;
import com.company.java.levelp.level2.clientserverapplication.client.view.ClientGUI;

import java.util.ArrayList;

public class DefaultController implements Controller {
    private ClientGUI view;
    private ClientModel model;

    @Override
    public void setView(ClientGUI view) {
        this.view = view;
    }

    @Override
    public void setModel(ClientModel model) {
        this.model = model;
    }

    @Override
    public void sendMessage(String message) {
        model.sendMessage(message);
    }

    @Override
    public void sendMessageTo(String message, String username) {
        model.sendMessageTo(message, username);
    }

    @Override
    public void receiveMessage(String message) {
        view.newMessageToChat(message);
    }

    @Override
    public void getOnline(){
        model.getOnlineUsers();
    }

    @Override
    public void setOnline(ArrayList<String> list) {
        view.setOnline(list);
    }

    @Override
    public void authorization(String login, String password) {
        model.authorization(login, password);
    }

    @Override
    public void authorizationResult(boolean result) {
        view.setAuthResult(result);
    }

    @Override
    public void registration(String login, String password) {
        model.registration(login, password);
    }

    @Override
    public void registrationResult(boolean result) {
        view.setRegResult(result);
    }

    @Override
    public void exit() {
        model.exit();
    }

    @Override
    public void noNameExit() {
        model.noNameExit();
    }

}














