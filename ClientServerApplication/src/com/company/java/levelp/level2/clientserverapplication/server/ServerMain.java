package com.company.java.levelp.level2.clientserverapplication.server;

import com.company.java.levelp.level2.clientserverapplication.entities.User;

import java.sql.Timestamp;
import java.util.Date;

public class ServerMain {
    public static void main(String[] args) {
        new Server().start();
        //HibernateManager.getInstance().addUser(new User("testHibernateUser", "hibernate", new Timestamp(new Date().getTime()), true));
    }
}










