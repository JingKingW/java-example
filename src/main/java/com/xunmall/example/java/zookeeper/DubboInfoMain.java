package com.xunmall.example.java.zookeeper;

import com.xunmall.example.java.excel.ExcelPoiUtil;
import com.xunmall.example.java.excel.ExcelSheetPO;
import com.xunmall.example.java.excel.ExcelVersion;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: wangyj03
 * @Description:
 * @Date: 2022/1/19 10:48
 */
public class DubboInfoMain {

    private static ZooKeeper zooKeeper;

    private static String connection = "10.135.24.41:2181";

    private static int sessionTimeout = 30000;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        initClient();

        testListNode();

        destroy();
    }


    public static void initClient() throws IOException {
        zooKeeper = new ZooKeeper(connection, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    public static void destroy() throws InterruptedException {
        if (zooKeeper != null) {
            zooKeeper.close();
        }
    }

    public static void testListNode() throws KeeperException, InterruptedException, IOException {
        String nodeDubbo = "/dubbo";
        List<String> children = zooKeeper.getChildren(nodeDubbo, false);
        List<String> providerList = new ArrayList<>();
        List<String> consumerList = new ArrayList<>();
        if (children.size() > 0) {
            children.forEach(item -> {
                String providerString = "/dubbo/" + item + "/providers";
                String consumerString = "/dubbo/" + item + "/consumers";
                providerList.add(providerString);
                consumerList.add(consumerString);
            });
        }
        Map<String, List<String>> providerListMap = println(providerList);
        Map<String, List<String>> consumerListMap = println(consumerList);
        outExcel(providerListMap, consumerListMap);

    }

    public static Map<String, List<String>> println(List<String> pathList) throws KeeperException, InterruptedException {
        Map<String, List<String>> providerMap = new HashMap<>();
        if (pathList.size() > 0) {
            for (String path : pathList) {
                List<String> providerNode = zooKeeper.getChildren(path, false);
                if (providerNode.size() > 0) {
                    List<String> collect = providerNode.stream().map(item -> URLDecoder.decode(item)).collect(Collectors.toList());
                    if (collect.size() > 0) {
                        for (String url : collect) {
                            String[] key_value = url.split("\\?");
                            String targetString = key_value[1];
                            if (targetString.contains("application") && targetString.contains("dubbo") && targetString.contains("interface")) {
                                System.out.println(targetString);
                                String[] split = targetString.split("&");
                                String appName = "";
                                String dubboName = "";
                                String interFaceName = "";
                                for (String value : split) {
                                    if (value.contains("application=")) {
                                        appName = value.substring(12, value.length());
                                    }
                                    if (value.contains("dubbo=")) {
                                        dubboName = value.substring(6, value.length());
                                    }
                                    if (value.contains("interface=")) {
                                        interFaceName = value.substring(10, value.length());
                                    }
                                    if (value.contains("release=")) {
                                        dubboName = value.substring(8, value.length());
                                    }

                                }
                                if (providerMap.containsKey(interFaceName)) {
                                    List<String> stringList = providerMap.get(interFaceName);
                                    if (stringList.size() > 0) {
                                        if (!stringList.contains(appName + " " + dubboName)) {
                                            stringList.add(appName + " " + dubboName);
                                            providerMap.put(interFaceName, stringList);
                                        }
                                    }
                                } else {
                                    List<String> stringList2 = new ArrayList<>();
                                    stringList2.add(appName + " " + dubboName);
                                    providerMap.put(interFaceName, stringList2);
                                }
                            }
                        }
                    }


                }
            }
        }
        if (!providerMap.isEmpty()) {
            providerMap.forEach((key, value) -> {
                String outStyring = "对应接口：" + key + " 版本信息： " + value;
                System.out.println(outStyring);
            });
        }
        return providerMap;
    }

    public static void outExcel(Map<String, List<String>> providerMap, Map<String, List<String>> consumerListMap) throws IOException {
        String outName = "C:\\Users\\wangyj03\\Desktop\\dubbo版本信息.xlsx";
        FileOutputStream fileOutputStream = new FileOutputStream(new File(outName));
        List<ExcelSheetPO> list = new ArrayList<>();
        ExcelSheetPO po1 = new ExcelSheetPO();
        po1.setTitle("dubbo接口对应服务与版本信息");
        po1.setSheetName("服务提供者");
        String[] heads1 = {"接口名称", "应用名称", "dubbo版本信息"};
        po1.setHeaders(heads1);
        List<List<Object>> dataList1 = new ArrayList<>();
        if (!providerMap.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : providerMap.entrySet()) {
                String serviceName = entry.getKey();
                List<String> info = entry.getValue();
                if (info.size() > 0) {
                    for (String value : info) {
                        String[] coll = value.split(" ");
                        List<Object> col = new ArrayList<>();
                        col.add(serviceName);
                        col.add(coll[0]);
                        col.add(coll[1]);
                        dataList1.add(col);
                    }
                }
            }
        }
        po1.setDataList(dataList1);

        ExcelSheetPO po2 = new ExcelSheetPO();
        po2.setTitle("dubbo接口对应服务与版本信息");
        po2.setSheetName("服务消费者");
        String[] heads2 = {"接口名称", "应用名称", "dubbo版本信息"};
        po2.setHeaders(heads2);
        List<List<Object>> dataList2 = new ArrayList<>();
        if (!providerMap.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : consumerListMap.entrySet()) {
                String serviceName = entry.getKey();
                List<String> info = entry.getValue();
                if (info.size() > 0) {
                    for (String value : info) {
                        String[] coll = value.split(" ");
                        List<Object> col = new ArrayList<>();
                        col.add(serviceName);
                        col.add(coll[0]);
                        col.add(coll[1]);
                        dataList2.add(col);
                    }
                }
            }
        }
        po2.setDataList(dataList2);

        list.add(po1);
        list.add(po2);
        ExcelPoiUtil.createWorkbookAtOutStream(ExcelVersion.V2007, list, fileOutputStream, true);
    }


}
