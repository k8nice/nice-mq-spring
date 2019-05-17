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
	//����ConnectionFactory��������	
	ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
			ActiveMQConnectionFactory.DEFAULT_PASSWORD,
			"tcp://192.168.230.130:61616");
	//ͨ��ConnectionFactory�����������Ǵ���һ��Connection���ӣ����ҵ���Connection��start�����������ӣ�ConnectionĬ���ǹرյġ�	
	Connection connection =  null;
	try {
	 connection = 	connectionFactory.createConnection();
	connection.start();
	//ͨ��Connection���󴴽�Session�Ự(�����Ļ�������),���ڽ�����Ϣ����������1Ϊ�Ƿ��������񣬲�������2Ϊǩ��ģʽ��һ�����������Զ�ǩ�ա�
	Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
	
	Destination destination = session.createQueue("first");
	MessageProducer producer = session.createProducer(null);
	for (int i = 0; i < 100; i++) {
		TextMessage msg = session.createTextMessage("������Ϣ����" + i);
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
