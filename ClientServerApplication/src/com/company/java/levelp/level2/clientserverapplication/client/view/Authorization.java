package com.company.java.levelp.level2.clientserverapplication.client.view;

import com.company.java.levelp.level2.clientserverapplication.client.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Authorization {
    private DefaultGUI gui;
    private Registration reg;
    JFrame frame = new JFrame("Authorization");
    private JButton authorizeButton = new JButton("   Log in   ");
    private JButton registrationButton = new JButton("Registration");
    private JButton exitButton = new JButton("Exit");
    private JPanel mainPanel = new JPanel();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JLabel labelLogin = new JLabel("Login: ");
    private JLabel labelPassword = new JLabel("Password: ");
    private JTextField login = new JTextField();
    private JPasswordField password = new JPasswordField();
    private JLabel info = new JLabel();

    public Authorization(DefaultGUI gui) {
        this.gui = gui;
    }

    public void build(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 210, 230);
        frame.setResizable(false);
        mainPanel.setLayout(new GridLayout(2, 1));

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        panel1.setLayout(gridbag);

        panel1.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                        panel1.getBorder()));
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.WEST;
        c.weightx = 0;
        panel1.add(labelLogin, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        panel1.add(login, c);

        c = new GridBagConstraints();

        c.gridwidth = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        panel1.add(labelPassword, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        panel1.add(password, c);

        c.gridwidth = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        panel1.add(info, c);

        c = new GridBagConstraints();

        panel2.setLayout(gridbag);

        panel2.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                        panel2.getBorder()));
        panel2.add(authorizeButton, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(registrationButton, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(exitButton, c);


        mainPanel.add(panel1);
        mainPanel.add(panel2);

        frame.add(mainPanel);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.getController().noNameExit();
                frame.dispose();
                gui.frame.dispose();
            }
        });

        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setEnabled(false);
                reg = new Registration(gui);
                reg.build();
            }
        });

        authorizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.getController().authorization(
                        login.getText(),
                        String.valueOf(password.getPassword()));
            }
        });

        frame.setVisible(true);

    }

    public void setInfo(String text) {
        info.setText(text);
    }

    public void stop(){
        frame.dispose();
    }

    public Registration getReg() {
        return reg;
    }
}













