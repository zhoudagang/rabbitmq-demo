package com.everyman.rabbitmqdemo.untils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author zhougang
 */
public class ConnectionUtil
{
    /**
     * 建立与RabbitMQ的连接
     */
    public static Connection getConnection() throws Exception
    {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("127.0.0.1");
        //端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("/zhougang");
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 通过工程获取连接
        return factory.newConnection();
    }

    public static void main(String[] args) throws Exception
    {
        Connection con = ConnectionUtil.getConnection();
        System.out.println(con);
        con.close();
    }

}
