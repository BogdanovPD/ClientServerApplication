package com.company.java.levelp.level2.clientserverapplication.client.view;

import com.company.java.levelp.level2.clientserverapplication.client.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DefaultGUI implements ClientGUI {
    private Controller controller;
    private JButton sendButton = new JButton("Send");
    private JButton exitButton = new JButton("Exit");
    private JPanel mainPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel labelAndReceiverPanel = new JPanel();
    private JPanel sendAndExitPanel = new JPanel();
    private JList list;
    private DefaultListModel listModel = new DefaultListModel();
    private JScrollPane scroll;
    private JLabel label = new JLabel("Receiver: ");
    private JTextField messageTo = new JTextField("");
    private JButton clearReceiver = new JButton("Clear");
    private JTextArea textArea = new JTextArea("");
    private JTextArea message = new JTextArea("");
    private boolean authResult = false;
    JFrame frame = new JFrame("My Chat");
    private Authorization au;
    private boolean regResult = false;

    @Override
    public void build() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 400);
        frame.setResizable(false);
        messageTo.setEnabled(false);
        setList(new JList(getListModel()));
        scroll = new JScrollPane(getList());
        scroll.setPreferredSize(new Dimension(250, 500));
        scroll.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createEmptyBorder(9, 5, 5, 5),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                        scroll.getBorder()));

        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    messageTo.setText(list.getSelectedValue().toString());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(message.getText());
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.exit();
            }
        });

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        labelAndReceiverPanel.setLayout(gridbag);

        labelAndReceiverPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                        scroll.getBorder()));
        labelAndReceiverPanel.add(label, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        labelAndReceiverPanel.add(messageTo, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        labelAndReceiverPanel.add(clearReceiver, c);

        clearReceiver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageTo.setText("");
            }
        });

        c = new GridBagConstraints();

        sendAndExitPanel.setLayout(gridbag);

        sendAndExitPanel.add(sendButton, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        sendAndExitPanel.add(exitButton, c);

        message.setFont(new Font("Serif", Font.ITALIC, 16));
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        JScrollPane scrollPaneMessage = new JScrollPane(message);
        scrollPaneMessage.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneMessage.setPreferredSize(new Dimension(250, 350));
        scrollPaneMessage.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                        scrollPaneMessage.getBorder()));

        rightPanel.setLayout(new GridLayout(4, 1));
        rightPanel.add(scroll);
        rightPanel.add(labelAndReceiverPanel);
        rightPanel.add(scrollPaneMessage);
        rightPanel.add(sendAndExitPanel);


        textArea.setFont(new Font("Serif", Font.ITALIC, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEnabled(false);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 350));
        areaScrollPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                        areaScrollPane.getBorder()));

        leftPanel.add(areaScrollPane);

        mainPanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public void authorization() {
        frame.setEnabled(false);
        au = new Authorization(this);
        au.build();
        while (!authResult()){}
    }

    @Override
    public void sendMessage(String message) {
        if (messageTo.getText().equals("")){
            controller.sendMessage(message);
        }
        else
            controller.sendMessageTo(message, messageTo.getText().trim());
    }

    @Override
    public void setOnline(ArrayList<String> list) {
        listModel.clear();
        for (String s : list)
            listModel.addElement(s);
    }

    public void setList(JList list) {
        this.list = list;
    }

    public DefaultListModel getListModel() {
        return listModel;
    }

    public JList getList() {
        return list;
    }

    public boolean authResult() {
        return this.authResult;
    }

    public void setAuthResult(boolean authResult) {
        if (authResult == false){
            au.setInfo("Incorrect login or password");
        }
        else {
            frame.setEnabled(true);
            au.stop();
        }
        this.authResult = authResult;
    }

    public void setRegResult(boolean regResult) {
        if (regResult == false){
            au.getReg().setInfo("Registration failed");
        }
        else {
            //controller.getOnline();
            frame.setEnabled(true);
            au.getReg().stop();
            au.stop();
        }
        this.regResult = regResult;
    }

    public Authorization getAu() {
        return au;
    }

    @Override
    public void newMessageToChat(String text){
        textArea.append(text+"\n");
    }

}













