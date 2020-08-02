package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.DeviceMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.DeviceExample;
import com.xiaoshu.entity.DeviceExample.Criteria;


@Service
public class DeviceService {

	@Autowired
	DeviceMapper deviceMapper;


	// 新增
	public void addUser(Device t) throws Exception {
		deviceMapper.insert(t);
	};

	// 修改
	public void updateUser(Device t) throws Exception {
		deviceMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteUser(Integer id) throws Exception {
		deviceMapper.deleteByPrimaryKey(id);
	};



	// 通过用户名判断是否存在，（新增时不能重名）
	public Device  existUserWithUserName(String userName) throws Exception {
		DeviceExample example = new DeviceExample();
		Criteria criteria = example.createCriteria();
		criteria.andDevicenameEqualTo(userName);
		List<Device> userList = deviceMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	

	public PageInfo<Device> findUserPage(Device device, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Device> userList = deviceMapper.findPage(device);
		PageInfo<Device> pageInfo = new PageInfo<Device>(userList);
		return pageInfo;
	}

	public List<Device> findPage(Device person) {
		// TODO Auto-generated method stub
		return deviceMapper.findPage(person);
	}


}
