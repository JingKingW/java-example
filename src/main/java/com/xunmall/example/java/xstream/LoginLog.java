package com.xunmall.example.java.xstream;

import lombok.Data;

import java.util.Date;

/**
 * Created by wangyanjing on 2018/11/28.
 */
@Data
public class LoginLog {
    private int loginLogId;
    private int userId;
    private String ip;
    private Date loginDate;
}
