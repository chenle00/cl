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
import com.xiaoshu.dao.TeacherMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Teacher;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class TeacherService {

	@Autowired
	TeacherMapper teacherMapper;

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination queueTextDestination;
	
	public List<Teacher> findTeacher() {
		// TODO Auto-generated method stub
		return teacherMapper.selectAll();
	}


	public void addTeacher(final Teacher teacher) {
		// TODO Auto-generated method stub
		teacher.setCreatetime(new Date());
		teacherMapper.insert(teacher);
		jmsTemplate.send(queueTextDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				String jsonString = JSONObject.toJSONString(teacher);
				
				return session.createTextMessage(jsonString);
			}
		});
	}


	public Integer findCidByTname(String tname) {
		Teacher teacher =new Teacher();
		teacher.setName(tname);
		Teacher one = teacherMapper.selectOne(teacher);
		return one.getId();
	}


}
