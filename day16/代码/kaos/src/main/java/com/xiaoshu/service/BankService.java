package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.BankMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Bank;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

import redis.clients.jedis.Jedis;

@Service
public class BankService {

	@Autowired
	BankMapper bankMapper;

	
	@Autowired
	private RedisTemplate redisTemplate;
	
	public List<Bank> findBank() {
		// TODO Auto-generated method stub
		return bankMapper.selectAll();
	}


	public int findCidByBname(String bname) {
		// TODO Auto-generated method stub
		Bank bank=new Bank();
		bank.setbName(bname);
		Bank one = bankMapper.selectOne(bank);
		return one.getbId();
	}


	public void addBank(Bank bank) {
		// TODO Auto-generated method stub
		bank.setCreatetime(new Date());
		bankMapper.insert(bank);
		redisTemplate.boundHashOps("banklist").put("bank", bank.toString());
	
	}

 
	


}
