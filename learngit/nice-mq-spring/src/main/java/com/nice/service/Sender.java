package com.nice.service;

import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class Sender {
	public static void main(String[] args) throws JMSException {
	//建立ConnectionFactory工厂对象，	
	ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
			ActiveMQConnectionFactory.DEFAULT_PASSWORD,
			"tcp://192.168.230.130:61616");
	//通过ConnectionFactory工厂对象我们创建一个Connection连接，并且调用Connection的start方法开启连接，Connection默认是关闭的。	
	Connection connection =  null;
	try {
	 connection = 	connectionFactory.createConnection();
	connection.start();
	//通过Connection对象创建Session会话(上下文环境对象),用于接收信息，参数配置1为是否启用事务，参数配置2为签收模式，一般我们设置自动签收。
	Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
	
	Destination destination = session.createQueue("first");
	MessageProducer producer = session.createProducer(null);
	for (int i = 0; i < 100; i++) {
		TextMessage msg = session.createTextMessage("我是消息内容" + i);
		producer.send(destination,msg);
		TimeUnit.SECONDS.sleep(1);
	}
	} catch (JMSException | InterruptedException  e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(connection != null) {
		connection.close();
	}
	}
}
