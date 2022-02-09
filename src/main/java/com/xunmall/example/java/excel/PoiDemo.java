package com.xunmall.example.java.excel;

import com.xunmall.example.java.gift.Gift;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wangyanjing
 * @date 2018/10/17
 */
public class PoiDemo {

    @Test
    public void testOutExcel() throws IOException {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("hello");
        HSSFRow hssfRow = hssfSheet.createRow(0);
        HSSFCell hssfCell = hssfRow.createCell(2);
        hssfCell.setCellValue("hello world");
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:\\abc.xls"));
        hssfWorkbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    @Test
    public void testInExcel() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("d:\\abc.xls"));
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        HSSFRow hssfRow = hssfSheet.getRow(0);
        HSSFCell hssfCell = hssfRow.getCell(2);
        String cellValue = hssfCell.getStringCellValue();
        System.out.println("第一行第三列的值是: " + cellValue);
        fileInputStream.close();
    }

    @Test
    public void testOutExcel2007() throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("hello");
        XSSFRow xssfRow = xssfSheet.createRow(0);
        XSSFCell xssfCell = xssfRow.createCell(2);
        xssfCell.setCellValue("this is 2007 excel");
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:\\abc.xlsx"));
        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    @Test
    public void testOutExcelPoiUtil() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:\\abcd.xls"));
        List<ExcelSheetPO> list = new ArrayList<>();
        ExcelSheetPO po = new ExcelSheetPO();
        po.setTitle("测试excel文件导出");
        po.setSheetName("第一个Sheet");
        String[] heads = {"主键", "名称", "概率"};
        po.setHeaders(heads);
        List<Gift> gifts = new ArrayList<>();
        Gift gift = new Gift();
        gift.setId(1);
        gift.setName("IPhone X");
        gift.setProb(30);
        Gift gift2 = new Gift();
        gift2.setId(2);
        gift2.setName("Watch 4");
        gift2.setProb(25);
        Gift gift3 = new Gift();
        gift3.setId(2);
        gift3.setName("IPad 2018");
        gift3.setProb(45);
        gifts.add(gift);
        gifts.add(gift2);
        gifts.add(gift3);
        List<List<Object>> dataList = new ArrayList<>();
        if (gifts != null && gifts.size() > 0) {
            for (Gift item : gifts) {
                List<Object> col = new ArrayList<>();
                col.add(item.getId());
                col.add(item.getName());
                col.add(item.getProb());
                dataList.add(col);
            }
        }
        po.setDataList(dataList);
        list.add(po);
        ExcelPoiUtil.createWorkbookAtOutStream(ExcelVersion.V2003, list, fileOutputStream, true);
    }

    @Test
    public void testInExcelPoiUtil() throws IOException {
        File file = new File("d:\\abcd.xls");
        List<ExcelSheetPO> list = ExcelPoiUtil.readExcel(file, null, null);
        if (list != null && list.size() > 0) {
            list.forEach(item -> {
                System.out.println(item.toString());
            });
        }
    }

    //Excel2003 一张表最大支持65536行数据
    @Test
    public void testExcel2003Operation() throws IOException {
        Long startTime = System.currentTimeMillis();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet();
        for (int i = 0; i < 10000; i++) {
            HSSFRow hssfRow = sheet.createRow(i);
            for (int j = 0; j < 10; j++) {
                HSSFCellUtil.createCell(hssfRow, j, String.valueOf(Math.random()));
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:\\excel2003.xlsx"));
        hssfWorkbook.write(fileOutputStream);
        fileOutputStream.close();
        System.out.println(System.currentTimeMillis() - startTime);
    }

    //Excel2007  XSSFWorkbook 导出1万条数据需要8秒左右，10万条数据需要40秒左右，不是太适合导出大批量数据
    @Test
    public void testExcel2007Operation() throws IOException {
        Long startTime = System.currentTimeMillis();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        Sheet sheet = xssfWorkbook.createSheet();
        for (int i = 0; i < 100000; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < 10; j++) {
                CellUtil.createCell(row, j, String.valueOf(Math.random()));
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:\\excel2007.xlsx"));
        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.close();
        System.out.println(System.currentTimeMillis() - startTime);
    }

    @Test
    public void testExcel2007Operation2() throws IOException {
        Long startTime = System.currentTimeMillis();
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        Sheet sheet = sxssfWorkbook.createSheet();
        for (int i = 0; i < 100000; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < 10; j++) {
                CellUtil.createCell(row, j, String.valueOf(Math.random()));
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:\\excel2007_sxssf.xlsx"));
        sxssfWorkbook.write(fileOutputStream);
        fileOutputStream.close();
        System.out.println(System.currentTimeMillis() - startTime);
    }


}
