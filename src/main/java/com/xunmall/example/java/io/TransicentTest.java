package com.xunmall.example.java.io;

import java.io.*;

/**
 *
 * @author Gimgoog
 * @date 2018/7/13
 */
public class TransicentTest {

    public static void main(String[] args) {
        User user = new User();
        user.setUserName("liuyunpiaopiao");
        user.setPassword("abc123456");
        System.out.println("before: username: " + user.getUserName() + " password: " + user.getPassword());
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("f://abc.text"));
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            user.setUserName("jeremy");
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("f://abc.text"));
            user = (User) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("before: username: " + user.getUserName() + " password: " + user.getPassword());

    }

}

class User implements Serializable {

    private  String userName;

    private transient String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}