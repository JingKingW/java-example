package com.xunmall.example.java.stream;

/**
 * Created by Gimgoog on 2017/11/28.
 */
public class Student implements Comparable<Student>{
    private Long id;
    private String username;
    private String password;
    private String address;
    private Integer age;

    public Student() {
        super();
    }

    public Student(Long id, String username, String password, String address, Integer age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.address = address;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public int compareTo(Student student) {
        return username.compareTo(student.getUsername());
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}
