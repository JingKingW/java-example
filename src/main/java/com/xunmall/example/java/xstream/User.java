package com.xunmall.example.java.xstream;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by wangyanjing on 2018/11/28.
 */
@Data
public class User {
    private int userId;
    private String userName;
    private String password;
    private int credits;
    private String lastIp;
    private Date lastVisit;
    private List logs;
}
