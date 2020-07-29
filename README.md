## 官方介绍

> RabbitMQ是一个消息代理：它接受和转发消息。 你可以把它想象成一个邮局：当你把邮件放在邮箱里时，你可以确定邮差先生最终会把邮件发送给你的收件人。 
>
> 在这个比喻中，RabbitMQ是邮政信箱，邮局和邮递员。
>
> RabbitMQ与邮局的主要区别是它不处理纸张，而是接受，存储和转发数据消息的二进制数据块。

- P（producer/ publisher）：生产者，一个发送消息的用户应用程序。

- C（consumer）：消费者，消费和接收有类似的意思，消费者是一个主要用来等待接收消息的用户应用程序

- 队列（红色区域）：rabbitmq内部类似于邮箱的一个概念。

- 虽然消息流经rabbitmq和你的应用程序，但是它们只能存储在队列中。

- 队列只受主机的内存和磁盘限制，实质上是一个大的消息缓冲区。

- 许多生产者可以发送消息到一个队列，许多消费者可以尝试从一个队列接收数据。

- 总之：

- - 生产者将消息发送到队列，消费者从队列中获取消息，队列是存储消息的缓冲区。
  - 我们将用Java编写两个程序;发送单个消息的生产者，以及接收消息并将其打印出来的消费者。
  - 我们将详细介绍Java API中的一些细节，这是一个消息传递的“Hello World”。
  - 我们将调用我们的消息发布者（发送者）Send和我们的消息消费者（接收者）Recv。
  - 发布者将连接到RabbitMQ，发送一条消息，然后退出。



## 五种消息模型

### **simple模式（即最简单的收发模式）**

![image-20200729214123032](/Users/everyman/Documents/Knowledge points/JavaPorject/rabbitmq-demo/README.assets/image-20200729214123032.png)

1.消息产生消息，将消息放入队列

2.消息的消费者(consumer) 监听 消息队列,如果队列中有消息,就消费掉,消息被拿走后,自动从队列中删除(隐患 消息可能没有被消费者正确处理,已经从队列中消失了,造成消息的丢失，这里可以设置成手动的ack,但如果设置成手动ack，处理完后要及时发送ack消息给队列，否则会造成内存溢出)。

---

### **work工作模式(资源的竞争)**

![image-20200729214135888](/Users/everyman/Documents/Knowledge points/JavaPorject/rabbitmq-demo/README.assets/image-20200729214135888.png)

1.消息产生者将消息放入队列消费者可以有多个,消费者1,消费者2同时监听同一个队列,消息被消费。C1 C2共同争抢当前的消息队列内容,谁先拿到谁负责消费消息(隐患：高并发情况下,默认会产生某一个消息被多个消费者共同使用,可以设置一个开关(syncronize) 保证一条消息只能被一个消费者使用)。

---

### **publish/subscribe发布订阅(共享资源)**

![image-20200729214158974](/Users/everyman/Documents/Knowledge points/JavaPorject/rabbitmq-demo/README.assets/image-20200729214158974.png)

1、每个消费者监听自己的队列；

2、生产者将消息发给broker，由交换机将消息转发到绑定此交换机的每个队列，每个绑定交换机的队列都将接收到消息。

---

### **routing路由模式**

![image-20200729214426817](/Users/everyman/Documents/Knowledge points/JavaPorject/rabbitmq-demo/README.assets/image-20200729214426817.png)

![image-20200729214538098](/Users/everyman/Documents/Knowledge points/JavaPorject/rabbitmq-demo/README.assets/image-20200729214538098.png)

1.消息生产者将消息发送给交换机按照路由判断,路由是字符串(info) 当前产生的消息携带路由字符(对象的方法),交换机根据路由的key,只能匹配上路由key对应的消息队列,对应的消费者才能消费消息;

2.根据业务功能定义路由字符串

3.从系统的代码逻辑中获取对应的功能字符串,将消息任务扔到对应的队列中。

4.业务场景:error 通知;EXCEPTION;错误通知的功能;传统意义的错误通知;客户通知;利用key路由,可以将程序中的错误封装成消息传入到消息队列中,开发者可以自定义消费者,实时接收错误;

### **topic 主题模式(路由模式的一种)**

![image-20200729214628568](/Users/everyman/Documents/Knowledge points/JavaPorject/rabbitmq-demo/README.assets/image-20200729214628568.png)

1.星号井号代表通配符

2.星号代表多个单词,井号代表一个单词

3.路由功能添加模糊匹配

4.消息产生者产生消息,把消息交给交换机

5.交换机根据key的规则模糊匹配到对应的队列,由队列的监听消费者接收消息消费

（在我的理解看来就是routing查询的一种模糊匹配，就类似sql的模糊查询方式）