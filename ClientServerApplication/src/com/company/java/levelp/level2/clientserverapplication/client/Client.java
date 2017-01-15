package com.company.java.levelp.level2.clientserverapplication.client;

import com.company.java.levelp.level2.clientserverapplication.client.controller.Controller;
import com.company.java.levelp.level2.clientserverapplication.MessageContainer;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

public class Client implements ClientModel {
    private Controller controller;
    private static final String IP = "localhost";
    private static final int PORT = 8080;
    private volatile Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private String username;
    private boolean auth_OK = false;
    private ClientReceivingThread crt;

    @Override
    public void initialize(){
        try {
            clientSocket = new Socket(IP, PORT);
            new ClientReceivingThread(this, clientSocket);
            writer = new PrintWriter(clientSocket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        catch (IOException e) {

        }
    }

    @Override
    public void sendMessage(String text) {
        Gson gson = new Gson();
        MessageContainer messageContainer = new MessageContainer(
                username, "all", text, new Date().getTime());
        String payload = gson.toJson(messageContainer);
        writer.println(payload);
        writer.flush();
    }

    @Override
    public void sendMessageTo(String text, String user) {
        Gson gson = new Gson();
        MessageContainer messageContainer = new MessageContainer(
                username, user, text, new Date().getTime());
        String payload = gson.toJson(messageContainer);
        writer.println(payload);
        writer.flush();
    }

    public void setAuth_OK(boolean auth_OK) {
        this.auth_OK = auth_OK;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void authorization(String login, String password){
        writer.println("@authorization " + login + " " + password);
        writer.flush();
    }

    @Override
    public void registration(String login, String password){
        writer.println("@reg " + login + " " + password);
        writer.flush();
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void getOnlineUsers(){
        writer.println((new Gson()).toJson(
                new MessageContainer(username, null, "getUsers",
                        new Date().getTime())));
        writer.flush();
    }

    @Override
    public void noNameExit(){
        writer.println("@server:exit");
        writer.flush();
    }

    @Override
    public void exit() {
        Gson gson = new Gson();
        MessageContainer messageContainer = new MessageContainer(username, null,
                "exit", new Date().getTime());
        String payload = gson.toJson(messageContainer);
        writer.println(payload);
        writer.flush();
    }

    public void stop(){
        writer.close();
        System.exit(0);
    }

}














