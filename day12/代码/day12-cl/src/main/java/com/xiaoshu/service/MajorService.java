package com.xiaoshu.service;

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
import com.xiaoshu.dao.MajorMapper;
import com.xiaoshu.entity.Major;

import redis.clients.jedis.Jedis;


@Service
public class MajorService {

	@Autowired
	MajorMapper majorMapper;

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination queueTextDestination;
	public List<Major> findMajor() {
		// TODO Auto-generated method stub
		return majorMapper.selectAll();
	}


	public void addMajor(final Major major) {
		// TODO Auto-generated method stub
		majorMapper.insert(major);
		/*Jedis jedis = new Jedis("127.0.0.1", 6379);*/
		Jedis jedis = new Jedis("localhost", 6379);
		Major major2 = findzzzz(major.getMdname());
		jedis.set("专业信息", major2.getMdname(),major2.getMdid()+"");
		
		jmsTemplate.send(queueTextDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				String json = JSONObject.toJSONString(major);
				return session.createTextMessage(json);
			}
		});
	}

	public Major findzzzz(String mdname){
		Major major=new Major();
		major.setMdname(mdname);
		return  majorMapper.selectOne(major);
		
	}
	

}
