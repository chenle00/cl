package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.CourseMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Course;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

import redis.clients.jedis.Jedis;

@Service
public class CourseService {

	@Autowired
	CourseMapper courseMapper;


	public List<Course> findCourse() {
		// TODO Auto-generated method stub
		return courseMapper.selectAll();
	}


	@SuppressWarnings("resource")
	public void addCourse(Course course) {
		// TODO Auto-generated method stub
		course.setCreatetime(new Date());
		courseMapper.insert(course);
		Jedis jedis=new Jedis("localhost",6379);
		jedis.hset("课程信息", course.getId()+"",course.getName());
		}


}
