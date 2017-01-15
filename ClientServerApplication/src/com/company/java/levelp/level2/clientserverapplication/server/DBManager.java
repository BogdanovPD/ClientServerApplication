package com.company.java.levelp.level2.clientserverapplication.server;

import com.company.java.levelp.level2.clientserverapplication.MessageContainer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class DBManager implements DAOInterface {
    private static DBManager instance;
    private Connection conn = newConnection();
    private Map<String, String> users = new LinkedHashMap<>();

    private DBManager() {}

    public static DBManager getInstance(){
        DBManager local = instance;
        if (local == null){
            synchronized (DBManager.class){
                local = instance;
                if (local == null)
                    local = new DBManager();
            }
        }
        return local;
    }

    private Connection newConnection(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/chat_db",
                    "root", "v12345V#");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //Message interface

    public void addMessage(MessageContainer messageContainer){
        int read = userOnline(messageContainer.getUser());
        Date date = new Date();
        Object param = new Timestamp(date.getTime());
        try {
            Statement st = conn.createStatement();
            String query = "INSERT INTO message_history " +
                    "(autor, message, receiver, time, isRead) " +
                    "VALUES " + "('" + messageContainer.getAuthor()
                    + "', '" + messageContainer.getMessage()
                    + "', '" + messageContainer.getUser() + "', '"
                    + param + "', '" + read + "')";
            st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getUnread(String receiver, Date date) {
        ArrayList<String> messageArray = new ArrayList<String>();
        Object param = new Timestamp(date.getTime());
        Statement st = null;
        try {
            st = conn.createStatement();
            String query = "SELECT autor, message, receiver, time " +
                    "FROM message_history " +
                    "WHERE isRead = '0' " +
                    "AND time < '" + param
                    + "' AND receiver = '" + receiver
                    + "' ORDER BY time";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                messageArray.add(rs.getString(1) + " to "
                        + ((rs.getString(2).contains("@for_all"))
                        ?  "all" : rs.getString(3))
                        + " " + rs.getString(4)
                        .substring(0, rs.getString(4).length()-5)
                        + " : " + ((rs.getString(2).contains("@for_all"))
                        ? rs.getString(2)
                        .substring(0, rs.getString(2).length()-8)
                        : rs.getString(2) ));
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messageArray;
    }

    public void markRead(String receiver){
        Statement st = null;
        try {
            st = conn.createStatement();
            String query = "UPDATE message_history SET isRead = '1' " +
                    "WHERE receiver = '" + receiver +"' AND isRead = '0'";
            st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Authorization interface

    public Map<String, String> getUsers(){
        try {
            Statement st = conn.createStatement();
            String query = "SELECT * FROM user";
            ResultSet res = st.executeQuery(query);
            users = new LinkedHashMap<>();
            while (res.next()){
                users.put(res.getString(2), res.getString(3));
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean getAuthorizationResult(String username, String password){

        getUsers();

        for (Map.Entry<String, String> m : users.entrySet()) {
            if (m.getKey().equals(username) && m.getValue().equals(password)) {
                setOnline(username);
                return true;
            }
        }

        return false;
    }

    public void setOffline(String username){
        Date date = new Date();
        Object param = new Timestamp(date.getTime());
        try {
            Statement st = conn.createStatement();
            String query = "UPDATE user " +
                    "SET offline = '1', lastOfflineTime = '" + param
                    + "' WHERE name =  '" + username + "'";
            st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setOnline(String username){
        try {
            Statement st = conn.createStatement();
            String query = "UPDATE user SET offline = '0' " +
                    "WHERE name =  '" + username + "'";
            st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int userOnline(String username){
        try {
            Statement st = conn.createStatement();
            String query = "SELECT offline FROM user " +
                    "WHERE name = '" + username + "'";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()){
                if (Integer.parseInt(rs.getString(1)) == 0)
                    return Integer.parseInt(rs.getString(1))+1;
                else
                    return Integer.parseInt(rs.getString(1))-1;
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Registration interface

    public void addNewUser(String login, String password){
        try {
            Statement st = conn.createStatement();
            String query = "INSERT INTO user (name, password) " +
                    "VALUES " + "('" + login + "', '" + password + "')";
            st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}










