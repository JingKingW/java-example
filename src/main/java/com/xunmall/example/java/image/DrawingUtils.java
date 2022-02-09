package com.xunmall.example.java.image;

import com.xunmall.example.java.util.FileUtils;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * Java 图片生成
 *
 * @author houzhenghai
 * @date 2018年10月19日 下午4:59:35
 */
public class DrawingUtils {

    private static float jPEGcompression = 0.75f;// 图片清晰比率

    private final static String IMG_UPLOAD_PATH = "";


    /**
     * @return : java.lang.String
     * @Description : 将二维码图片和文字生成到一张图片上
     * @Param : originalImg 原图
     * @Param : qrCodeImg 二维码地址
     * @Param : shareDesc 图片文字
     * @Author : houzhenghai
     * @Date : 2018/8/15
     */
    public static String generateImg(String originalImg, String qrCodeImg, String shareDesc) throws Exception {
        // 加载原图图片
        BufferedImage imageLocal = ImageIO.read(new URL(originalImg));
        // 加载用户的二维码
        BufferedImage imageCode = ImageIO.read(new URL(qrCodeImg));
        // 以原图片为模板
        Graphics2D g = imageLocal.createGraphics();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.setComposite(ac);
        g.setBackground(Color.WHITE);
        // 在模板上添加用户二维码(地址,左边距,上边距,图片宽度,图片高度,未知)
        g.drawImage(imageCode, 400, imageLocal.getHeight() - 190, 160, 158, null);
        // 设置文本样式
        g.setFont(new Font("微软雅黑", Font.PLAIN, 40));
        g.setColor(Color.red);
        // 计算文字长度，计算居中的x点坐标
        g.drawString(shareDesc, imageLocal.getWidth() - 330, imageLocal.getHeight() - 530);

        // 设置文本样式
        g.setFont(new Font("微软雅黑", Font.PLAIN + Font.BOLD, 16));
        g.setColor(Color.WHITE);
        // 计算文字长度，计算居中的x点坐标
        String caewm = "长按二维码";
        g.drawString(caewm, 105, imageLocal.getHeight() - 10);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        saveAsJPEG(imageLocal, out);
        out.close();
        return saveImageToDisk(FileUtils.parse(out));
    }

    /**
     * @return : java.lang.String
     * @Description : 将二维码图片成到一张图片上
     * @Param : originalImg 原图
     * @Param : qrCodeImg 二维码地址
     * @Author : houzhenghai
     * @Date : 2018/8/15
     */
    public static String mergeImg(String originalImg, String qrCodeImg) throws Exception {
        // 加载原图图片
        BufferedImage imageLocal = ImageIO.read(new URL(originalImg));
        // 加载用户的二维码
        BufferedImage imageCode = ImageIO.read(new URL(qrCodeImg));
        // 以原图片为模板
        Graphics2D g = imageLocal.createGraphics();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(ac);
        g.setBackground(Color.WHITE);
        // 在模板上添加用户二维码(地址,左边距,上边距,图片宽度,图片高度,未知)
        g.drawImage(imageCode, 400, imageLocal.getHeight() - 190, 160, 158, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        saveAsJPEG(imageLocal, out);
        out.close();
        return saveImageToDisk(FileUtils.parse(out));
    }


    /**
     * 以JPEG编码保存图片
     *
     * @param imageToSave 要处理的图像图片
     * @param fos         文件输出流
     * @throws IOException
     */
    private static void saveAsJPEG(BufferedImage imageToSave, ByteArrayOutputStream fos) throws IOException {
        ImageWriter imageWriter = ImageIO.getImageWritersBySuffix("jpg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
        imageWriter.setOutput(ios);
        if (jPEGcompression >= 0 && jPEGcompression <= 1f) {
            // new Compression
            JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(jPEGcompression);

        }
        // new Write and clean up
        ImageIO.setUseCache(false);
        imageWriter.write(new IIOImage(imageToSave, null, null));
        ios.close();
        imageWriter.dispose();
    }

    /**
     * 图片流远程上传
     */
    private static String urlImgDownInputStream(InputStream inStream) throws Exception {
        Long fileName = System.currentTimeMillis();
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(IMG_UPLOAD_PATH);// 文件服务器上传图片地址
            MultipartEntity mpEntity = new MultipartEntity();
            ContentBody contb = new InputStreamBody(inStream, fileName + ".png");
            mpEntity.addPart("Filedata", contb);
            post.setEntity(mpEntity);
            HttpResponse httpResponse = httpclient.execute(post);
            HttpEntity entity = httpResponse.getEntity();
            String jsonStr = EntityUtils.toString(entity);
            JSONObject ob = JSONObject.fromObject(jsonStr);
            if (!ob.isEmpty() && ob.containsKey("pic_id")) {
                return ob.getString("pic_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return null;
    }

    private static String saveImageToDisk(InputStream inStream) throws Exception {
        Long fileName = System.currentTimeMillis();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File("d:\\" + fileName + ".jpg");
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据
        outStream.write(data);
        //关闭输出流
        outStream.close();
        return imageFile.getAbsolutePath();
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * test
     *
     * @param args
     * @throws
     */
    public static void main(String[] args) {
        long starttime = System.currentTimeMillis();
        System.out.println("开始：" + starttime);
        try {
            String originalImg = "http://ossmh.jj1699.cn/uploads/picture/20190605/70981ea3085740caa3c5621ba609f493.png";
            String qrCodeImg = "http://ossmh.jj1699.cn/test/uploads/qrcode/generalize/e938b5798fda47728c2dcdbfef795f74.png";
            String shareDesc = "原价9999.99元";
            //String img = generateImg(originalImg, qrCodeImg, shareDesc);
            String img = mergeImg(originalImg, qrCodeImg);
            System.out.println("生成完毕,url=" + img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束：" + (System.currentTimeMillis() - starttime) / 1000);
    }

}