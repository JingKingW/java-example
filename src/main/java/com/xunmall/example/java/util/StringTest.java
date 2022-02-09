package com.xunmall.example.java.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2017/1/3
 */
public class StringTest {


    public static void main(String[] args) {
        System.out.println(String.format("%s,你好", "王彦京"));
        String string = "【网格金服】尊敬的用户，您好！您的账户成功投标#money#元，历史收益率#rate#%，请及时留意账户信息， 网格金服感谢您的关注与支持！";
        Map<String, String> map = new HashMap();
        map.put("#money#", "130000");
        map.put("#rate#", "13");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            string = string.replace(entry.getKey(), entry.getValue());
        }

        System.out.println(string);
        String status = "\"1,2\"";
        String[] statusArray = status.split(",");
        for (String id : statusArray) {
            System.out.println(Integer.parseInt(id.contains("\"") ? id.replace("\"", "") : id));
        }

    }

    @Test
    public void testStringSpile() {
        String target = "1:基本\n" +
                "2:会员\n" +
                "3:邮件\n" +
                "5:代理\n" +
                "6:API接口\n" +
                "7:推广配置\n" +
                "8:运营配置\n" +
                "9:签到福利\n" +
                "10:作者\n" +
                "11:支付\n" +
                "99:系统";
        String[] string = target.split("\n");
        for (int i = 0; i < string.length; i++) {
            String topValue = string[i];
            if (topValue != null) {
                String[] item = topValue.split(":");
                System.out.println(item[0]);
                System.out.println(item[1]);
            }
        }
    }

    @Test
    public void testStirngConvert() {
        String pushMessage = "嗨！您上次看的漫画还在等您哦~点击继续阅读\n" +
                "\n" +
                "<a href='http://" + "7wqrew.dafasdf.net" + "/chapter/read/ " + 123 + "\\/" + 23 + "'>《" + "小龙" + "》</a>\n" +
                "\n" +
                "您还有一笔订单待支付，首充即可获得双倍金币！\n" +
                "\n" +
                "☞ <a href='http://" + "7wqrew.dafasdf.net" + "/pay/index'>点击立即充值</a>";

        System.out.println(StringEscapeUtils.escapeHtml(pushMessage));
    }

    /**
     * @Description: []
     * @Title: testStringStyle
     * @Author: WangYanjing
     * @Date: 2020/8/26
     * @Param:
     * @Return: void
     * @Throws:
     */
    @Test
    public void testStringStyle() {
        String msg = "{\"resKey\":\"one-gateway-333\",\"dimensions\":\"max,fail,avg,total\",\"topMenu\":\"one-gateway\",\"menuLevels\": [\n" +
                "        {\n" +
                "            \"key\": \"gw-root\",\n" +
                "            \"name\":\"网关\"\n" +
                "        },        {\n" +
                "            \"key\": \"one-gateway\",\n" +
                "            \"name\":\"网关管理后台\"\n" +
                "        },        {\n" +
                "            \"key\": \"gateway.application.list\",\n" +
                "            \"name\":\"获取项目列表1\"\n" +
                "        }\n" +
                "    ]}";
        System.out.println(msg);
    }

    @Test
    public void testStringSub() {
        String splitString = "gateway.application.list";
        String childMenuKey = "one-gateway-one-admin-gateway.application.list-avg";
        int index = childMenuKey.indexOf(splitString);
        System.out.println(index);
        String newabc = childMenuKey.substring(0, index) + splitString + "-fuse";
        System.out.println(newabc);

    }

    @Test
    public void testStringSub2() {
        String string = "ONE-gateway-abc-test-gateway.add.mehtod.test123";
        String[] abc = string.split("-");
        System.out.println(abc[abc.length - 1]);

    }


    @Test
    public void testLeftPad() {
        System.out.println(StringUtils.leftPad("8", 3, "0"));
    }

    @Test
    public void testStringEach() {
        String startTime = "2019-05-15";
        String endTime = "2020-01-12";
        int differDays = DateUtils.differentDays(DateUtils.parse(startTime, DateUtils.DATE_FORMAT_YYYY_MM_DD), DateUtils.parse(endTime, DateUtils.DATE_FORMAT_YYYY_MM_DD));
        System.out.println(differDays);
        for (int i = 0; i < differDays; i++) {
            Date newDate = DateUtils.daysPlusOfDate(DateUtils.parse(startTime, DateUtils.DATE_FORMAT_YYYY_MM_DD), i);
            System.out.println("DROP TABLE monitor_data_event_" + DateUtils.format(newDate, "yyyyMMdd") + ";");
        }
    }

    @Test
    public void testStringJoin() throws UnknownHostException {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            list.add(random.nextInt(10000000) + "");
        }
        String result = StringUtils.join(list.toArray(), "#");
        System.out.println(result);

        System.out.println(InetAddress.getLocalHost().getHostAddress());

    }

    @Test
    public void testStringUtilsSub() {
        String ipAndCode = "999002003001";
        String pcode = StringUtils.substring(ipAndCode, 0, 6);
        System.out.println(pcode);
        String worker = "10.100.31.123-999002003001";
        String ipcode = StringUtils.substringBeforeLast((String) worker, "-");
        System.out.println(ipcode);
    }

    @Test
    public void testTime() {
        Long now = System.currentTimeMillis();
        System.out.println(now);
        Long max = 1603185784000L;
        System.out.println((max + 6 * 1000 * 60) < now && (max + 24 * 3600 * 1000 > now));
    }

    @Test
    public void testRandomDataSource() {
        Random random = new Random();
        List<Long> useridList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            useridList.add(SnowflakeIdUtils.uniqueLong());
        }
        for (int j = 0; j < 10000; j++) {
            Double itemoffset = 200 + random.nextGaussian() * 100;
            Double goodoffset = 2000 + random.nextGaussian() * 1000;
            Long timeoffset = (long) (100000 + random.nextGaussian() * 100000);
            Long item = useridList.get(random.nextInt(1000));
            System.out.println(item + "," + (long) (1000 + itemoffset) + "," + (long) (10000 + goodoffset) + "," + "pv," + (System.currentTimeMillis() - timeoffset));
        }

    }

    @Test
    public void testStringsub() {
        String businessType = "111,222,333";
        List<String> businessTypeList = Arrays.asList(businessType.split(","));
        for (int i = 0; i < businessTypeList.size(); i++) {
            System.out.println(businessTypeList.get(i));
        }

        String param = "123";
        System.out.println(param.substring(0, 1));
        System.out.println(param.substring(1, 2));
        System.out.println(param.substring(2, 3));
    }

    @Test
    public void testHashCode() {
        long s1 = System.currentTimeMillis();
        Map<String, Integer> integerMap = new HashMap<>(1000000);
        for (int i = 0; i < 1000000; i++) {
            String value = UUID.randomUUID().toString();
            //int tableNumber = Math.abs(value.hashCode()) % 128;
            Long tableNumber = Math.abs(new MurmurHash().hash(String.valueOf(value))) % 64;
            String result = String.format("%02d", tableNumber);
            if (!integerMap.containsKey(result)) {
                integerMap.put(result, 1);
            } else {
                integerMap.put(result, integerMap.get(result) + 1);
            }
        }
        integerMap.forEach((key, value) -> {
            System.out.println("key: " + key + " value: " + value);
        });
        long s2 = System.currentTimeMillis();
        System.out.println(s2 - s1);
    }

    @Test
    public void testStringLength() {
        System.out.println("20200121".length());
        String str = " 134 1233 1312";
        String str2 = str.replaceAll(" ", "");
        System.out.println(str2);
    }

    @Test
    public void testStringSort() {
        String abc = " select count(phonekey) from valid_phone_dh where validtime = 1615391999";
        for (int i = 0; i < 1000; i++) {
            String param = String.format("%03d", i);
            System.out.println(abc + param + ";");
        }
        String baseTime = String.valueOf(DateUtils.getTimeStamp(DateUtils.weeHours(DateUtils.daysLose(DateUtils.now(), 1), 1)));
        System.out.println(baseTime);
    }

    @Test
    public void testCompare() {
        String str1 = "abc";
        String str2 = new String("abc");
        String str3 = str2.intern();
        System.out.println(str1 == str2);
        System.out.println(str2 == str3);
        System.out.println(str3 == str1);
        String key = "android_10_2048bits_private.pem";
        String prefix = key.substring(0, key.indexOf("_", key.indexOf("_") + 1)).toLowerCase();
        System.out.println(prefix);
    }

    @Test
    public void testListStreamJoin() {
        List<String> phoneKeys = Arrays.asList("0000001e8cab50f23ed547af90ced294", "0000001eca64db9132824ee6631542ae", "00000024a57bbf752e3dcbcba5095672");
        String QUERY_SQL_LIST_INFO = "SELECT phoneKey,ic,phone,validtime,uploadcount,activecount FROM valid_phone_dh WHERE phoneKey in (%s) ";
        String param = phoneKeys.stream().collect(Collectors.joining(",", "'", "'"));
        String sql = String.format(QUERY_SQL_LIST_INFO, param);
        System.out.println(sql);
        StringBuilder in = new StringBuilder();
        for (int i = 0; i < phoneKeys.size(); i++) {
            if (i > 0) {
                in.append(",");
            }
            in.append("'").append(phoneKeys.get(i)).append("'");
        }
        String sql2 = String.format(QUERY_SQL_LIST_INFO, in.toString());
        System.out.println(sql2);
    }

    @Test
    public void testMd5() {
        System.out.println(MD5Utils.stringToMD5("86" + "13000000007"));
    }


    @Test
    public void testStringSplit() {
        String resourceValue = "one-gateway-miniapp-platform-miniapp.getMiniappInfo.v1-ratelimit";

        String[] msgKeyArr = resourceValue.split("-");
        String oneName = resourceValue.substring(0, 12);
        String keyName = msgKeyArr[msgKeyArr.length - 1];
        String methodName = msgKeyArr[msgKeyArr.length - 2].replaceAll("\\.", "_");
        String fileName = (oneName + keyName).replaceAll("-", "_");
        String appName = resourceValue.substring(12, resourceValue.length() - keyName.length() - methodName.length() - 2).replaceAll("-", "_");
        System.out.println(fileName);
        System.out.println(appName);
        System.out.println(methodName);

    }


    @Test
    public void testBase64() {
 /*       String data = "7/2x51SUdA50jTvkonYDGA==\n";
        String data2 = "7/2x51SUdA50jTvkonYDGA==";
        String fileData = org.apache.commons.codec.binary.Base64.encodeBase64String(data.getBytes());
        String fileData2 = org.apache.commons.codec.binary.Base64.encodeBase64String(data2.getBytes());
        System.out.println(fileData);
        System.out.println(fileData2);
        String str = new String(Base64.getDecoder().decode(fileData));
        String str2 = new String(Base64.getDecoder().decode(fileData2));
        System.out.println(str);
        System.out.println(str2);*/

        String data2 = "7/2x51SUdA50jTvkonYDGA==";
        String fileData = org.apache.commons.codec.binary.Base64.encodeBase64String(data2.getBytes());
        System.out.println(fileData);
        String decodeStr = "Ny8yeDUxU1VkQTUwalR2a29uWURHQT09Cg==";
        String result = new String(Base64.getMimeDecoder().decode(decodeStr));
        System.out.println(result);
    }

    @Test
    public void readFileBase64() throws IOException {
        File f = new File("D:\\uic_1");
        byte[] data = org.apache.commons.io.FileUtils.readFileToByteArray(f);
        String fileData = org.apache.commons.codec.binary.Base64.encodeBase64String(data);
        System.out.println(fileData);
    }

    @Test
    public void stringLength() {
        String ip = "100.100.100.100";
        String host = "dc-flume-prod-0.dc-flume-prod.ibs-prod.svc.cluster.local";
        System.out.println(ip.length());
        System.out.println(host.length());
        if (host.length() > 30) {
            String newIP = host.substring(0,30);
            System.out.println(newIP);
            System.out.println(newIP.length());
        }


    }


}
