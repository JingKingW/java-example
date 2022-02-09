package com.xunmall.example.java.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author WangYanjing
 * @date 2020/8/19.
 */
@Slf4j
public class ConsumerFastTest {

    public static final String brokerList = "10.100.31.31:9091";
    public static final String topic = "test-perf";
    public static final String groupId = "gp-localTest";

    public static Properties initConfig() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer.client.id" + System.currentTimeMillis());
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        return properties;
    }

    @Test
    public void testConsumerReblanceEx() {
        Properties properties = initConfig();

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);

        kafkaConsumer.subscribe(Arrays.asList(topic));

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(500);
                        handler(consumerRecords);
                    }
                } catch (Exception ex) {

                }
            }
        };
        new Thread(runnable).start();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handler(ConsumerRecords<String, String> consumerRecords) throws InterruptedException {
        Thread.sleep(31 * 1000);
        for (ConsumerRecord<String, String> record : consumerRecords) {
            System.out.println("topic = " + record.topic() + ", partition = " + record.partition() + " , offset = " + record.offset());
            System.out.println("key = " + record.key() + " , value = " + record.value());
        }

    }


}
