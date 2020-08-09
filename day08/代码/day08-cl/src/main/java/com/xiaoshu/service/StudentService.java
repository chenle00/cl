package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentExample;
import com.xiaoshu.entity.StudentExample.Criteria;

@Service
public class StudentService {

	@Autowired
	StudentMapper studentMapper;

	// 新增
	public void addUser(Student t) throws Exception {
		studentMapper.insert(t);
	};

	// 修改
	public void updateUser(Student t) throws Exception {
		studentMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteUser(Integer id) throws Exception {
		studentMapper.deleteByPrimaryKey(id);
	};

	// 通过用户名判断是否存在，（新增时不能重名）
	public Student existUserWithUserName(String userName) throws Exception {
		StudentExample example = new 		StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andSnameEqualTo(userName);
		List<Student> userList = studentMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	

	

	public PageInfo<Student> findUserPage(Student student, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Student> userList = studentMapper.findPage(student);
		PageInfo<Student> pageInfo = new PageInfo<Student>(userList);
		return pageInfo;
	}

	public List<Student> findPage(Student student) {
		// TODO Auto-generated method stub
		return studentMapper.findPage(student);
	}

	


}
