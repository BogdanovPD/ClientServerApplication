package com.company.java.levelp.level2.clientserverapplication.server;

import com.company.java.levelp.level2.clientserverapplication.MessageContainer;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ServerClientEntity extends Thread{
    private Server server;
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private String username;

    public ServerClientEntity(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
        start();
    }

    public void run(){
        try {
            reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String firstMessage;
            String messageFromClient;
            MessageContainer messageContainer;

            while (!((firstMessage = reader.readLine()) == null)) {

                if (firstMessage.equals("@server:exit")){
                    exit();
                    return;
                }

                String operation;
                String[] firstMessageArray = firstMessage.split(" ");

                operation = firstMessageArray[0];

                if (operation.equals("@reg"))
                    if (server.addNewUser(firstMessageArray[1],
                            firstMessageArray[2])) {
                        username = firstMessageArray[1];
                        sendToClient("@reg_OK " + username);
                        sendToClient("You logging in as " + username);
                        server.sendToAllClients(server.getServerClients());
                        System.out.println(username + " connected!");
                        break;
                    } else
                        sendToClient("@reg_Failed " +
                                "Perhaps, user with following name has already exist."
                                + " Try again with new user name");
                else if (HibernateManager.getInstance()
                        .getAuthorizationResult(
                                firstMessageArray[1], firstMessageArray[2])) {
                    username = firstMessageArray[1];
                    sendToClient("@auth_OK " + username);
                    sendToClient("You logging in as " + username);
                    System.out.println(username + " connected!");
                    server.sendToAllClients(server.getServerClients());
                    ArrayList<String> unreadMessages = HibernateManager
                            .getInstance().getUnread(username, new Date());
                    for (String m : unreadMessages){
                        sendToClient(m);
                    }
                    if (unreadMessages.size() > 0)
                        HibernateManager.getInstance().markRead(username);
                    break;
                } else
                    sendToClient("@auth_Failed User with following name" +
                            " and password doesn't exist.");
            }

            while ((messageFromClient = reader.readLine()) != null){
                messageContainer = new Gson().fromJson(messageFromClient,
                        MessageContainer.class);
                if (messageContainer.getUser() != null)
                    if (messageContainer.getUser().equals("all")) {
                        server.sendToAllClients(
                                messageContainer.getAuthor() + ":"
                                + messageContainer.getMessage());
                        addToHistoryForAll(messageContainer);
                    }
                    else {
                        server.sendToClient(messageContainer.getAuthor() + ":"
                                + messageContainer.getMessage(),
                                messageContainer.getUser());
                        server.sendToClient(
                                messageContainer.getAuthor() + " to "
                                + messageContainer.getUser() + ":"
                                        + messageContainer.getMessage(), username);
                        addToHistory(messageContainer);
                    }
                else {
                    if (messageContainer.getMessage().equals("exit")) {
                        System.out.println(username + " disconnected!");
                        HibernateManager.getInstance().setOffline(username);
                        exit();
                        server.remove(this);
                        server.sendToAllClients(server.getServerClients());
                        break;
                    }
                    else if (messageContainer.getMessage().equals("getUsers"))
                        sendToClient(server.getServerClients());
                    else
                        System.out.println("received message: "
                                + messageContainer.getMessage());

                }
            }
        } catch (IOException e) {

        }

    }

    public void sendToClient(String message){
        try {
            writer = new PrintWriter(clientSocket.getOutputStream());
            writer.println(message);
            writer.flush();
        } catch (IOException e) {

        }
    }

//    public void stopClientThread(){
//        interrupt();
//        try {
//            sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if (isAlive())
//            stop();
//    }

    public boolean isConnected(){
        return clientSocket.isBound();
    }

    public void exit(){
        sendToClient("@exit");
        try {
            reader.close();
            writer.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }

    public void addToHistory(MessageContainer messageContainer){
        server.addMessageToHistory(messageContainer);
    }

    public void addToHistoryForAll(MessageContainer messageContainer){
        MessageContainer newMessageContainer;
        for (Map.Entry<String, String> m : server.getUsers().entrySet()){
            if (!m.getKey().equals(messageContainer.getAuthor())) {
                newMessageContainer = new MessageContainer(
                        messageContainer.getAuthor(),
                        m.getKey(), messageContainer.getMessage()
                        + "@for_all", messageContainer.getTime());
                addToHistory(newMessageContainer);
            }
        }
    }

    public String getUsername() {
        return username;
    }

}










