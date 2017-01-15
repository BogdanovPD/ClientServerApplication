package com.company.java.levelp.level2.clientserverapplication.server;

import com.company.java.levelp.level2.clientserverapplication.MessageContainer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Server {
    private static final int PORT = 8080;
    private ServerSocket serverSocket;
    private volatile ArrayList<ServerClientEntity> serverClients;
    private volatile Map<String, String> users;

    public void start(){
        serverClients = new ArrayList<ServerClientEntity>();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    for (ServerClientEntity sce : serverClients){
//                        if (!sce.isConnected()){
//                            System.out.println(sce.getUsername() + " crash disconnected");
//                            sce.stopClientThread();
//                            remove(sce);
//                        }
//                    }
//                }
//            }
//        }).start();
        users = HibernateManager.getInstance().getUsers();
        try {
            serverSocket = new ServerSocket(PORT);
            while (true){
                Socket clientSocket = serverSocket.accept();
                serverClients.add(new ServerClientEntity(clientSocket, this));
            }
        } catch (IOException e) {

        }

    }

    public synchronized void remove(ServerClientEntity serverClient) {
        serverClients.remove(serverClient);
    }

    public void sendToAllClients(String message){
        for (ServerClientEntity serverClient : serverClients) {
                 serverClient.sendToClient(message);
        }
    }

    public void sendToClient(String message, String username){
        for (ServerClientEntity serverClient : serverClients){
            if (serverClient.getUsername().equals(username))
                serverClient.sendToClient(message);
        }
    }

    public String getServerClients() {
        StringBuilder sb = new StringBuilder();
        sb.append("@UsersList@@");
        for (ServerClientEntity serverClient : serverClients) {
            if (serverClient != null)
                sb.append(serverClient.getUsername() + "@@");
        }
        return sb.toString().trim();
    }

    public Map<String, String> getUsers(){
        return users;
    }

    public synchronized boolean addNewUser(String login, String password){
        if (users == null){
            users = new LinkedHashMap<String, String>();
        }
        if (checkBeforeAdd(login)) {
            users.put(login, password);
            HibernateManager.getInstance().addNewUser(login, password);
            return true;
        }
            return false;
    }

    public boolean checkBeforeAdd(String login){
            for (Map.Entry<String, String> m : users.entrySet()) {
                if (m.getKey().equals(login))
                    return false;
            }
        return true;
    }

    public synchronized void addMessageToHistory(MessageContainer message){
        HibernateManager.getInstance().addMessage(message);
    }

}










