package com.xunmall.example.java.io;

import java.io.*;

/**
 * Created by yanjing_wnag on 2017/5/28 0028.
 */
public class CopyDemo {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("需要传入两个目录用于拷贝！");
            System.exit(1);
        }
        File inputFile = new File(args[0]);
        if (!inputFile.exists()) {
            System.out.println("目标文件不存在，请更换目录！");
            System.exit(1);
        }
        File outputFile = new File(args[1]);
        if (outputFile.exists()) {
            System.out.println("拷贝的文件已存在，请换一个！");
        }

        Long start = System.currentTimeMillis();
        InputStream input = new FileInputStream(inputFile);
        OutputStream output = new FileOutputStream(outputFile);
        copy(input, output);
        input.close();
        output.close();
        Long end = System.currentTimeMillis();
        System.out.println("花费的时间是：" + (end - start));

    }

    public static void copy(InputStream input, OutputStream output) throws Exception {
        int temp = 0;
        byte[] data = new byte[2048];
        while((temp = input.read(data))!=-1){
            output.write(data,0,temp);
        }
    }


}
