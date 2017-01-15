package com.company.java.levelp.level2.clientserverapplication.client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientReceivingThread extends Thread {
    private Socket clientSocket;
    private BufferedReader reader;
    private volatile Client client;

    public ClientReceivingThread(Client client, Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.client = client;
        start();
    }

    public void run(){
        try {
            reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String messageFromServer;
            String[] messageFromServerArray;
            String[] usersMas;
            ArrayList<String> usersList;
            while (!(messageFromServer = reader.readLine()).equals("@exit")
                    && clientSocket.isConnected()){
                if (messageFromServer.contains("@UsersList")) {
                    usersMas = messageFromServer.split("@@");
                    usersList = new ArrayList<String>();
                    for (int i = 1; i < usersMas.length; i++) {
                        usersList.add(usersMas[i]);
                    }
                    client.getController().setOnline(usersList);
                }
              else if (messageFromServer.contains("@reg")) {
                    messageFromServerArray = messageFromServer.split(" ");
                    System.out.println(messageFromServer);
                    if (messageFromServerArray[0].equals("@reg_Failed"))
                        client.getController().registrationResult(false);
                    else {
                        client.getController().registrationResult(true);
                        client.setUsername(messageFromServerArray[1]);
                        client.setAuth_OK(true);
                    }
                }
                else if (messageFromServer.contains("@auth")) {
                    messageFromServerArray = messageFromServer.split(" ");
                    System.out.println(messageFromServer);
                    if (messageFromServerArray[0].equals("@auth_Failed"))
                        client.getController().authorizationResult(false);
                    else {
                        client.setUsername(messageFromServerArray[1]);
                        client.setAuth_OK(true);
                        client.getController().authorizationResult(true);
                    }
                }
                else{
                    System.out.println(messageFromServer);
                    client.getController().receiveMessage(messageFromServer);
                }
            }
            reader.close();
            clientSocket.close();
            client.stop();
        } catch (IOException e) {

        }
    }

}














