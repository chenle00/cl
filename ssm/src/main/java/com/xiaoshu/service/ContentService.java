package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.ContentMapper;
import com.xiaoshu.dao.ContentcategoryMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentExample;
import com.xiaoshu.entity.ContentExample.Criteria;
import com.xiaoshu.entity.Contentcategory;


@Service
public class ContentService {

	@Autowired
	ContentMapper contentMapper;
	@Autowired
	ContentcategoryMapper contentcategoryMapper;
	// 查询所有
	
	// 新增
	public void addUser(Content t) throws Exception {
		contentMapper.insert(t);
	};

	// 修改
	public void updateUser(Content t) throws Exception {
		contentMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteUser(Integer id) throws Exception {
		contentMapper.deleteByPrimaryKey(id);
	};

	
	// 通过用户名判断是否存在，（新增时不能重名）
	public Content existUserWithUserName(String userName) throws Exception {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andContenttitleEqualTo(userName);
		List<Content> userList = contentMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	

	public PageInfo<Content> findUserPage(Content content, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<Content> userList = contentMapper.findPage(content);
		PageInfo<Content> pageInfo = new PageInfo<Content>(userList);
		return pageInfo;
	}

	public int findCidByCname(String categoryname) {
		
		Contentcategory contentcategory = new Contentcategory();
		contentcategory.setCategoryname(categoryname);
		Contentcategory one = contentcategoryMapper.selectOne(contentcategory);
		return one.getContentcategoryid();
	}

	

}
