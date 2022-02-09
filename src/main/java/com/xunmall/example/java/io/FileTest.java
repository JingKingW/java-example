package com.xunmall.example.java.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by SHCL on 2018/8/2.
 */
public class FileTest {

    public static void main(String[] args) {
        String filePath = "d://test.txt";
        String data = "人这一生最大的幸福就在于能够做自己喜欢的自己";
        writeFile(filePath, data);
        readDate(filePath);
    }

    public static void readDate(String filePaht) {
        File file = new File(filePaht);

        try {
            FileReader fileReader = new FileReader(file);
            StringBuilder stringBuilder = new StringBuilder();
            int value = -1;

            while ((value = fileReader.read()) != -1) {
                char v = (char) value;
                stringBuilder.append(v);
            }

            System.out.println(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String filePaht, String data) {
        File file = new File(filePaht);

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
