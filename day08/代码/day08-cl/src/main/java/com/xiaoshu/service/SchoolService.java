package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.SchoolMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.School;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class SchoolService {

	@Autowired
	SchoolMapper schoolMapper;


	public List<School> findSchool() {
		// TODO Auto-generated method stub
		return schoolMapper.selectAll();
	}


}
