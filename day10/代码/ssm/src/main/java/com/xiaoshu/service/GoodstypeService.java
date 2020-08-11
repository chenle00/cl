package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoshu.dao.GoodstypeMapper;

import com.xiaoshu.entity.Goodstype;

import redis.clients.jedis.Jedis;


@Service
public class GoodstypeService {

	@Autowired
	GoodstypeMapper goodstypeMapper;


	public List<Goodstype> findGoodstype() {
		// TODO Auto-generated method stub
		return goodstypeMapper.selectAll();
	}


	//将新增的商品类型id作为key，新增商品类型对象作为value存入redis缓存，使用hash数据类型
	public void addGoodstype(Goodstype goodstype) {
		// TODO Auto-generated method stub
		goodstype.setCreatetime(new Date());
		goodstypeMapper.insert(goodstype);
		Jedis jedis=new Jedis("localhost",6379);
		Goodstype type=findggggg(goodstype.getTypename());
		jedis.set("商品分类",type.getId()+""+type.toString());
	}

	//随便创建一个方法
	public Goodstype findggggg(String typename){
		Goodstype goodstype=new Goodstype();
		goodstype.setTypename(typename);
		return goodstypeMapper.selectOne(goodstype);
		
	}

}
