package com.xiaoshu.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.alibaba.fastjson.JSONObject;
import com.xiaoshu.entity.Major;

import redis.clients.jedis.Jedis;

public class MyMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		TextMessage textMessage=(TextMessage) message;
		try {
			String text = textMessage.getText();
			Major major = JSONObject.parseObject(text, Major.class);
			System.err.println("接受的消息是：====="+major);
			/*Jedis jedis = new Jedis("localhost", 6379);
			jedis.hset("专业信息", major.getMdname(),major.getMdid()+"");*/
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}

}
