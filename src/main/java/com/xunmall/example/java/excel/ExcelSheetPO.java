package com.xunmall.example.java.excel;


import lombok.Data;

import java.util.List;

/**
 * 定义表格的数据对象
 * @author wangyanjing
 */
@Data
public class ExcelSheetPO {

    /**
     * sheet的名称
     */
    private String sheetName;

    /**
     * 表格标题
     */
    private String title;

    /**
     * 头部标题集合
     */
    private String[] headers;

    /**
     * 数据集合
     */
    private List<List<Object>> dataList;

}
