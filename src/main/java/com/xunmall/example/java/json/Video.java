package com.xunmall.example.java.json;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyanjing on 2018/10/10.
 */
@Data
public class Video implements Serializable {
    private String name;
    private String category;
    private List<Map<String, String>> playSource;
    private List<Map<String, String>> playBack;
}
