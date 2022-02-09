package com.xunmall.example.java.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RSACoderTest {
    private String publicKey;
    private String privateKey;

    @Before
    public void setUp() throws Exception {
        Map<String, Object> keyMap = RSACoder.initKey();

        publicKey = RSACoder.getPublicKey(keyMap);
        privateKey = RSACoder.getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);
    }

    @Test
    public void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);

        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData, privateKey);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        assertEquals(inputStr, outputStr);


    }

    @Test
    public void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String inputStr = "sign";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);

        byte[] decodedData = RSACoder.decryptByPublicKey(encodedData, publicKey);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        assertEquals(inputStr, outputStr);

        System.err.println("私钥签名——公钥验证签名");
        // 产生签名
        String sign = RSACoder.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);

        // 验证签名
        boolean status = RSACoder.verify(encodedData, publicKey, sign);
        System.err.println("状态:\r" + status);
        assertTrue(status);

    }

    @Test
    public void testCodeMsg() throws Exception {
        String message = "bac7a73d4cdbd21b802db7f50d71977251261cf90c72f7d159b3bc91a8a63cab5f4ea7d07eb8d82fb0e604276566a3895e41ac8ade41b81c1d21b326bcafe5c3de5eddaa33c78102ba065412cb126d708ad692aebff39554ce1dbc4ee9f3d3a0133ae58bcf981fd38674662010a8465847527a7d996b0215f2c428bb92c08777";
        byte[] encryptedBytes = message.getBytes();
        int length = encryptedBytes.length;
        int decryptLength = 128;
        File file2 = new File("D:\\tmp\\android_11.h");
        byte[] privateKey = FileUtils.readFileToByteArray(file2);

        int segment = length / decryptLength;
        int rest = length - decryptLength * segment;
        byte[] result = null;
        for (int i = 0; i < segment; i++) {
            byte[] temp = ArrayUtils.subarray(encryptedBytes, i * decryptLength, (i + 1) * decryptLength);
            byte[] decrypted = RSACoder.decryptByPrivateKeyRSA(temp, Base64.encodeBase64String(privateKey));
            result = ArrayUtils.addAll(result, decrypted);
        }

        if (rest > 0) {
            byte[] temp = ArrayUtils.subarray(encryptedBytes, decryptLength * segment, length);
            byte[] decrypted = RSACoder.decryptByPrivateKeyRSA(temp, Base64.encodeBase64String(privateKey));
            result = ArrayUtils.addAll(result, decrypted);
        }
        System.out.println(new String(result));
    }


    @Test
    public void testDecordMesg() throws Exception {
        String message = "i love you";
        File file = new File("D:\\tmp\\android_11");
        byte[] publicKey = FileUtils.readFileToByteArray(file);
        String publicString = Base64.encodeBase64String(publicKey);
        System.out.println(publicString);
        byte[] encryptedBytes = RSACoder.encryptByPublicKey(message.getBytes(), publicString);
        File file2 = new File("D:\\tmp\\android_11.h");
        byte[] privateKey = FileUtils.readFileToByteArray(file2);
        System.out.println(Base64.encodeBase64String(privateKey));

        int length = encryptedBytes.length;
        int decryptLength = 128;

        int segment = length / decryptLength;
        int rest = length - decryptLength * segment;
        byte[] result = null;
        for (int i = 0; i < segment; i++) {
            byte[] temp = ArrayUtils.subarray(encryptedBytes, i * decryptLength, (i + 1) * decryptLength);
            byte[] decrypted = RSACoder.decryptByPrivateKeyRSA(temp, Base64.encodeBase64String(privateKey));
            result = ArrayUtils.addAll(result, decrypted);
        }

        if (rest > 0) {
            byte[] temp = ArrayUtils.subarray(encryptedBytes, decryptLength * segment, length);
            byte[] decrypted = RSACoder.decryptByPrivateKeyRSA(temp, Base64.encodeBase64String(privateKey));
            result = ArrayUtils.addAll(result, decrypted);
        }
        System.out.println(new String(result));
    }

}
