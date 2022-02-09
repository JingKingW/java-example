package com.xunmall.example.java.middleware;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author wangyj03@zenmen.com
 * @description
 * @date 2020/10/23 10:51
 */
public class ElasticsearchTest {

    private Logger logger = LoggerFactory.getLogger(ElasticsearchTest.class);

    public final static String HOST = "10.241.122.136";
    public final static int PORT = 9300;

    private TransportClient client = null;

    @Before
    public void getConnect() throws UnknownHostException {
        // 1)创建一个Settings对象，相当于配置信息，主要配置集群名称。
        Settings settings = Settings.builder()
                .put("cluster.name", "my-es")
                .build();
        // 2)创建客户端
        client = new PreBuiltTransportClient(settings)
                .addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(HOST), PORT));
        logger.info("Elasticsearch connect info:" + client.toString());
    }

    @After
    public void closeConnect() {
        if (null != client) {
            logger.info("执行关闭连接操作...");
            client.close();
        }
    }

    @Test
    public void addIndex() throws IOException
    {
        IndexResponse response = client.prepareIndex("msg", "tweet", "1").setSource(XContentFactory.jsonBuilder()
                .startObject().field("userName", "张三").field("sendDate", new Date()).field("msg", "你好李四").endObject())
                .get();

        logger.info("索引名称:" + response.getIndex() + "\n类型:" + response.getType() + "\n文档ID:" + response.getId() + "\n当前实例状态:" );
    }







}
