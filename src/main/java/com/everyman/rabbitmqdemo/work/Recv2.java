package com.everyman.rabbitmqdemo.work;

import com.everyman.rabbitmqdemo.untils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author zhougang
 */
//消费者2
public class Recv2
{
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] argv) throws Exception
    {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 能者多劳
        channel.basicQos(1);
        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel)
        {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException
            {
                // body 即消息体
                String msg = new String(body);
                System.out.println(" [消费者2] received : " + msg + "!");
                try
                {
                    // 模拟完成任务的耗时：200ms
                    Thread.sleep(200);
                } catch (InterruptedException ignored)
                {
                }
                // 手动ACK
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 监听队列。
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
