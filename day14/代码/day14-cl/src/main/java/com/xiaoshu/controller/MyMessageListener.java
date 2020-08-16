package com.xiaoshu.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.Teacher;

public class MyMessageListener implements MessageListener{

	@Autowired
	private RedisTemplate redisTemplate;
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		TextMessage textMessage=(TextMessage) message;
		try {
			String text = textMessage.getText();
			redisTemplate.boundHashOps("teacherlist").put("teacher1", text);
			Teacher teacher = JSONObject.parseObject(text,Teacher.class);
			System.out.println("teacher======"+teacher);
			redisTemplate.boundHashOps("teacherlist").put("teacher2", teacher);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
