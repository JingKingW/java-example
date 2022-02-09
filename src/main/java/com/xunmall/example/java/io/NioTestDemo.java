package com.xunmall.example.java.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author Gimgoog
 * @date 2018/7/17
 */
public class NioTestDemo {

    public static void main(String[] args) throws IOException {
        String resource = "F:\\abc.text";
        String dest = "F:\\xyz.text";
        nioCopeFile(resource,dest);
    }

    public static void nioCopeFile(String resource,String dest) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(resource);
        FileOutputStream fileOutputStream = new FileOutputStream(dest);
        FileChannel inFileChannel = fileInputStream.getChannel();
        FileChannel outFileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while(true){
            byteBuffer.clear();
            int len = inFileChannel.read(byteBuffer);
            if(len == -1){
                break;
            }
            byteBuffer.flip();
            outFileChannel.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

}
