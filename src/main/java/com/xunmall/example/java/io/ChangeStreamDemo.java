package com.xunmall.example.java.io;

import java.io.*;

/**
 * Created by yanjing_wang on 2017/5/23.
 */
public class ChangeStreamDemo {

    public static void main(String[] args) throws Exception{

        File file = new File("d:"+ File.separator + "text" + File.separator + "demo.txt");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        OutputStream outputStream = new FileOutputStream(file);
        Writer writer = new OutputStreamWriter(outputStream);
        String data = "最喜欢阳光明媚的日子里，去看山花烂漫";
        writer.write(data);
        writer.close();
    }


}
