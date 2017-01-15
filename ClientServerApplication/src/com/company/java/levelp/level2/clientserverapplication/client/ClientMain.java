package com.company.java.levelp.level2.clientserverapplication.client;

import com.company.java.levelp.level2.clientserverapplication.client.controller.Controller;
import com.company.java.levelp.level2.clientserverapplication.client.controller.DefaultController;
import com.company.java.levelp.level2.clientserverapplication.client.view.ClientGUI;
import com.company.java.levelp.level2.clientserverapplication.client.view.DefaultGUI;

public class ClientMain {
    public static void main(String[] args) {
        //new Client().start();
        ClientModel model = new Client();
        model.initialize();
        Controller controller = new DefaultController();
        ClientGUI gui = new DefaultGUI();
        model.setController(controller);
        controller.setModel(model);
        controller.setView(gui);
        gui.setController(controller);
        gui.build();
        gui.authorization();
    }
}














