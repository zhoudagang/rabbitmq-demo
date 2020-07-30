package com.everyman.rabbitmqdemo.direct;

import com.everyman.rabbitmqdemo.untils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author zhougang
 */
public class Send
{

    private static final String EXCHANGE_NAME = "direct_exchange_test";


    public static void main(String[] argv) throws Exception
    {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 声明exchange，指定类型为direct
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        // 消息内容
        String message = "商品新增了， id = 1001";
        // 发布消息到Exchange
        channel.basicPublish(EXCHANGE_NAME, "insert", null, message.getBytes());
        System.out.println(" [生产者] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
