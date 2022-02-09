package com.xunmall.example.java.oom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author wangyj03@zenmen.com
 * @description
 * @date 2021/5/7 15:14
 */
public class ReadByHeapByteBufferTest {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        File data = new File("data.txt");
        FileChannel fileChannel = new RandomAccessFile(data,"rw").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(4 * 1024 *1024);
        for (int i =0; i < 1000; i ++){
            Thread.sleep(1000);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        fileChannel.read(buffer);
                        buffer.clear();
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
