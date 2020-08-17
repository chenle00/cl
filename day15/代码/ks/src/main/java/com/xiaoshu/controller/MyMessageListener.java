package com.xiaoshu.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.alibaba.fastjson.JSONObject;
import com.xiaoshu.entity.Bank;

public class MyMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		TextMessage  textMessage=(TextMessage) message;
		try {
			String text = textMessage.getText();
			Bank bank = JSONObject.parseObject(text, Bank.class);
			System.out.println("银行信息======"+bank);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
