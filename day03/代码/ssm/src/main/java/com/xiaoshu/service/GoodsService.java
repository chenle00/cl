package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.GoodsMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class GoodsService {

	@Autowired
	GoodsMapper goodsMapper;



	// 新增
	public void addUser(Goods t) throws Exception {
		goodsMapper.insert(t);
	};

	// 修改
	public void updateUser(Goods t) throws Exception {
		goodsMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteUser(Integer id) throws Exception {
		goodsMapper.deleteByPrimaryKey(id);
	};

	

	public PageInfo<Goods> findUserPage(Goods goods, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<Goods> userList = goodsMapper.findPage(goods);
		PageInfo<Goods> pageInfo = new PageInfo<Goods>(userList);
		return pageInfo;
	}


}
