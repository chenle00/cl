package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.GoodsMapper;
import com.xiaoshu.dao.GoodstypeMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.GoodsExample;
import com.xiaoshu.entity.GoodsExample.Criteria;
import com.xiaoshu.entity.GoodsVo;
import com.xiaoshu.entity.Goodstype;


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

	// 通过用户名判断是否存在，（新增时不能重名）
		public Goods existUserWithUserName(String userName) throws Exception {
			GoodsExample example = new GoodsExample();
			Criteria criteria = example.createCriteria();
			criteria.andCodeEqualTo(userName);
			List<Goods> userList = goodsMapper.selectByExample(example);
			return userList.isEmpty()?null:userList.get(0);
		};


	

	public PageInfo<GoodsVo> findUserPage(GoodsVo goodsVo, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<GoodsVo> userList = goodsMapper.findPage(goodsVo);
		PageInfo<GoodsVo> pageInfo = new PageInfo<GoodsVo>(userList);
		return pageInfo;
	
	}
	

}
