package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.DevicetypeMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Devicetype;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class DevicetypeService {

	@Autowired
	DevicetypeMapper devicetypeMapper;


	public List<Devicetype> findDevicetype() {
		// TODO Auto-generated method stub
		return devicetypeMapper.selectAll();
	}


}
