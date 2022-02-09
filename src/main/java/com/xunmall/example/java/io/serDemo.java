package com.xunmall.example.java.io;

import java.io.*;

/**
 * Created by yanjing_wang on 2017/5/28 0028.
 */
class Person implements Serializable {
    private String name;
    private int age;

    @Override
    public String  toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

}


public class serDemo {

    public static void main(String[] agrs) throws Exception{
        //Person person = new Person("zhangsan",23);
        //ser(person);
        dser();
    }
    public static void ser(Person person) throws Exception{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("d:" + File.separator + "person.ser")));
        objectOutputStream.writeObject(person);
        objectOutputStream.close();
    }

    public static void dser() throws Exception{
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("d:" + File.separator + "person.ser")));
        Object obj = objectInputStream.readObject();
        System.out.println(obj);
        objectInputStream.close();
    }

}
