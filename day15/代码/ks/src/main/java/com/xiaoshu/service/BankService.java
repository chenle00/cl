package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.BankMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Bank;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class BankService {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired 
	private Destination queueTextDestination;
	@Autowired
	BankMapper bankMapper;



	public List<Bank> findBank() {
		// TODO Auto-generated method stub
		return bankMapper.selectAll();
	}



	public void addBank(final Bank bank) {
		// TODO Auto-generated method stub
		bank.setCreatetime(new Date());
		bankMapper.insert(bank);
		jmsTemplate.send(queueTextDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				String jsonString = JSONObject.toJSONString(bank);
				return session.createTextMessage(jsonString);
			}
		});
	}


}
