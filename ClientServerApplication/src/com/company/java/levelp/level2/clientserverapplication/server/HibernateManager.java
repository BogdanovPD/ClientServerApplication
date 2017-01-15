package com.company.java.levelp.level2.clientserverapplication.server;

import com.company.java.levelp.level2.clientserverapplication.entities.Message;
import com.company.java.levelp.level2.clientserverapplication.entities.User;
import com.company.java.levelp.level2.clientserverapplication.MessageContainer;
import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.*;

import java.sql.Timestamp;
import java.util.*;

public class HibernateManager implements DAOInterface {
    private static HibernateManager instance;
    private SessionFactory sessionFactory = buildSessionFactory();
    private Map<String, String> usersMap = new LinkedHashMap<>();

    private HibernateManager() {}

    public static HibernateManager getInstance(){
        HibernateManager local = instance;
        if (local == null){
            synchronized (DBManager.class){
                local = instance;
                if (local == null)
                    local = new HibernateManager();
            }
        }
        return local;
    }

    private SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .buildServiceRegistry();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    //Message interface

    @Override
    public void addMessage(MessageContainer messageContainer) {
        int read = userOnline(messageContainer.getUser());
        Message message = new Message(messageContainer.getAuthor(),
                messageContainer.getMessage(), messageContainer.getUser(),
                new Timestamp(new Date().getTime()), read == 1);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(message);
        session.getTransaction().commit();
    }

    @Override
    public ArrayList<String> getUnread(String receiver, Date date) {
        ArrayList<String> messageArray = new ArrayList<String>();
        Object param = new Timestamp(date.getTime());
        Session session = sessionFactory.openSession();
        List<Message> messageList = session.createCriteria(Message.class)
                .add(Restrictions.eq("isRead", false))
                .add(Restrictions.lt("time", param))
                .add(Restrictions.eq("receiver", receiver)).list();
        for (Message message : messageList){
            messageArray.add(message.getAutor() + " to "
                    + ((message.getMessage().contains("@for_all"))
                    ?  "all" : message.getReceiver())
                    + " " + message.getTime().toString()
                    .substring(0, message.getTime().toString().length()-5)
                    + " : " + ((message.getMessage().contains("@for_all"))
                    ? message.getMessage()
                    .substring(0, message.getMessage().length()-8)
                    : message.getMessage()));
        }
        return messageArray;
    }

    @Override
    public void markRead(String receiver) {
        Session session = sessionFactory.openSession();
        List<Message> list = session.createCriteria(Message.class)
                .add(Restrictions.eq("receiver", receiver))
                .add(Restrictions.eq("isRead", false)).list();
        session = sessionFactory.openSession();
        for (Message message : list){
            session.beginTransaction();
            session.saveOrUpdate(message);
            session.getTransaction().commit();
        }
    }

    //Authorization interface

    @Override
    public Map<String, String> getUsers() {
        Session session = sessionFactory.openSession();
        List<User> users = session.createCriteria(User.class).list();
        for (User user : users){
            usersMap.put(user.getName(), user. getPassword());
        }
        return usersMap;
    }

    @Override
    public boolean getAuthorizationResult(String username, String password) {
        getUsers();

        for (Map.Entry<String, String> m : usersMap.entrySet()) {
            if (m.getKey().equals(username) && m.getValue().equals(password)) {
                setOnline(username);
                return true;
            }
        }

        return false;
    }

    @Override
    public void setOffline(String username) {
        User user = getByName(username);
        user.setOffline(true);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
    }

    @Override
    public void setOnline(String username) {
        User user = getByName(username);
        user.setOffline(false);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
    }

    @Override
    public int userOnline(String username) {
        User user = getByName(username);
        if (user.isOffline())
            return 0;
        else
            return 1;
    }

    @Override
    public void addNewUser(String login, String password) {
        User user = new User(login, password, null, false);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

    }

    //service

    private User getByName(String name) {
        Session session = sessionFactory.openSession();
        User user = (User) session.createCriteria(User.class)
                .add(Restrictions.like("name", name))
                .uniqueResult();
        return user;
    }

//    public void addUser(User user) {
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        session.save(user);
//        session.getTransaction().commit();
//    }

}










