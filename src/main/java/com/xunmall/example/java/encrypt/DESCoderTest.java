package com.xunmall.example.java.encrypt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Gimgoog on 2018/6/6.
 */
public class DESCoderTest {
    @Test
    public void test() throws Exception {
        String inputStr = "DES";
        String key = DESCoder.initKey();
        System.err.println("原文:\t" + inputStr);

        System.err.println("密钥:\t" + key);

        byte[] inputData = inputStr.getBytes();
        inputData = DESCoder.encrypt(inputData, key);

        System.err.println("加密后:\t" + DESCoder.encryptBASE64(inputData));

        byte[] outputData = DESCoder.decrypt(inputData, key);
        String outputStr = new String(outputData);

        System.err.println("解密后:\t" + outputStr);

        assertEquals(inputStr, outputStr);
    }
}
