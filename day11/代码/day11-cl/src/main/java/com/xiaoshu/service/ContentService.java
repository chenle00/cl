package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.ContentMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class ContentService {

	@Autowired
	ContentMapper contentMapper;

	
	

	// 修改
	public void updateUser(Content t) throws Exception {
		contentMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteUser(Integer id) throws Exception {
		contentMapper.deleteByPrimaryKey(id);
	};

	

	


	public PageInfo<ContentVo> findUserPage(ContentVo contentVo, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ContentVo> userList = contentMapper.findPage(contentVo);
		PageInfo<ContentVo> pageInfo = new PageInfo<ContentVo>(userList);
		return pageInfo;
	}

	public List<ContentVo> getEcharts() {
		// TODO Auto-generated method stub
		return contentMapper.getEcharts();
	}

	


}
