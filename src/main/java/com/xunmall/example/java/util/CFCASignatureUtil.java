package com.xunmall.example.java.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.*;
import java.util.Date;

/**
 * Created by Gimgoog on 2018/6/13.
 */
public class CFCASignatureUtil {

    private static final String KEY_STORE = "JKS";

    private static final String X509 = "X.509";

    /**
     * 获取到KeyStore秘钥库信息
     */
    private static KeyStore getKeyStroe(String keyStorePath, String password) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        FileInputStream fileInputStream = new FileInputStream(keyStorePath);
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE);
        keyStore.load(fileInputStream, password.toCharArray());
        fileInputStream.close();
        return keyStore;
    }

    /**
     * 获取到证书信息(通过秘钥库和别名的方式获取)
     */
    private static Certificate getCertificate(String keyStorePath, String password, String alias) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        KeyStore keyStore = getKeyStroe(keyStorePath, password);
        return keyStore.getCertificate(alias);
    }

    /**
     * 获取证书信息（通过公钥文件获取）
     */
    private static Certificate getCertificate(String certificatePath) throws IOException, CertificateException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
        FileInputStream fileInputStream = new FileInputStream(certificatePath);
        Certificate certificate = certificateFactory.generateCertificate(fileInputStream);
        fileInputStream.close();
        return certificate;
    }

    /**
     * 由keyStore获取私钥信息
     */
    private static PrivateKey getPrivateKey(String keyStorePath, String password, String alias) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, UnrecoverableKeyException {
        KeyStore keyStore = getKeyStroe(keyStorePath, password);
        return (PrivateKey) keyStore.getKey(alias, password.toCharArray());
    }

    /**
     * 由证书获取公钥信息
     */
    private static PublicKey getPublicKey(String certificatePath) throws IOException, CertificateException {
        return getCertificate(certificatePath).getPublicKey();
    }

    /**
     * 验证证书是否过期
     */
    public static boolean verifyCertificate(Certificate certificate, Date date) {
        boolean status = true;
        try {
            X509Certificate x509Certificate = (X509Certificate) certificate;
            x509Certificate.checkValidity(date);
        } catch (CertificateExpiredException e) {
            status = false;
            e.printStackTrace();
        } catch (CertificateNotYetValidException e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 使用私钥进行加密操作
     *
     * @return
     */
    public static byte[] encryptByPrivateKey(String keystorePath, String password, String alias, byte[] data) throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        PrivateKey privateKey = getPrivateKey(keystorePath, password, alias);
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 使用私钥进行解密密操作
     *
     * @return
     */
    public static byte[] decryptByPrivateKey(String keystorePath, String password, String alias, byte[] data) throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        PrivateKey privateKey = getPrivateKey(keystorePath, password, alias);
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 使用公钥进行加密操作
     */
    public static byte[] encryptByPublicKey(String certificatePath, byte[] data) throws IOException, CertificateException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        PublicKey publicKey = getPublicKey(certificatePath);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 使用公钥进行解密操作
     */
    public static byte[] decryptByPublicKey(String certificatePath, byte[] data) throws IOException, CertificateException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        PublicKey publicKey = getPublicKey(certificatePath);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 签名数据
     */
    public static byte[] sign(String keystorePath, String password, String alias, byte[] sign) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, UnrecoverableKeyException, InvalidKeyException, SignatureException {
        //获得证书
        X509Certificate x509Certificate = (X509Certificate) getCertificate(keystorePath, password, alias);
        // 获得私钥
        PrivateKey privateKey = getPrivateKey(keystorePath, password, alias);
        // 构建签名
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initSign(privateKey);
        signature.update(sign);
        return signature.sign();
    }

    /**
     * 验证签名
     */
    public static boolean verifySign(String certificatePath, byte[] data, byte[] sign) throws Exception {
        // 获得证书
        X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);
        // 获得公钥
        PublicKey publicKey = x509Certificate.getPublicKey();
        // 构建签名
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }

    public static void main(String[] args) throws Exception {
        String keyStrorePath = "F:\\wangyanjing.keystore";
        String password = "abc123";
        String alias = "wangyanjing";
        String certificatePaht = "F:\\wangyanjing.cer";

        String miwen = "this is my cat";
        System.out.println("加密前数据:" + miwen);
        //私钥加密
        byte[] encrypt = CFCASignatureUtil.encryptByPrivateKey(keyStrorePath, password, alias, miwen.getBytes());
        //公钥解密
        byte[] decrypt = CFCASignatureUtil.decryptByPublicKey(certificatePaht, encrypt);
        System.out.println("解密后数据:" + new String(decrypt));

        //签名
        byte[] sign = CFCASignatureUtil.sign(keyStrorePath, password, alias, encrypt);

        //验证签名
        boolean verifySign = CFCASignatureUtil.verifySign(certificatePaht, encrypt, sign);

        System.out.println(verifySign);


    }

}
