package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.StuMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Stu;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class StuService {

	@Autowired
	StuMapper stuMapper;

	// 新增
	public void addUser(Stu t) throws Exception {
		stuMapper.insert(t);
	};

	// 修改
	public void updateUser(Stu t) throws Exception {
		stuMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteUser(Integer id) throws Exception {
		stuMapper.deleteByPrimaryKey(id);
	};



	public PageInfo<Stu> findUserPage(Stu stu, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Stu> userList = stuMapper.findPage(stu);
		PageInfo<Stu> pageInfo = new PageInfo<Stu>(userList);
		return pageInfo;
	}


}
