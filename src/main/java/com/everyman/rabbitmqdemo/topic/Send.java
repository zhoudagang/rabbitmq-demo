package com.everyman.rabbitmqdemo.topic;

import com.everyman.rabbitmqdemo.untils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * @author zhougang
 */
public class Send
{
    private static final String EXCHANGE_NAME = "topic_exchange_test";


    /**
     * •	通配符规则：
     * •	`#`：匹配0或多个词​（含零个）
     * •	`*`：匹配不多不少恰好1个词（不含零个）
     * •	举例：
     * •	`audit.#`：能够匹配`audit.irs.corporate` 或者 `audit.irs`​
     * •	`audit.*`：只能匹配`audit.irs`
     */
    public static void main(String[] argv) throws Exception
    {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 声明exchange，指定类型为topic b: 持久化
        channel.exchangeDeclare(EXCHANGE_NAME, "topic",true);
        // 消息内容
        String message = "新增商品 : id = 1001";
        // 发送消息，并且指定routing key 为：insert ,代表新增商品
        // MessageProperties.PERSISTENT_TEXT_PLAIN 持久化
        channel.basicPublish(EXCHANGE_NAME, "item.insert", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println(" [商品服务：] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
