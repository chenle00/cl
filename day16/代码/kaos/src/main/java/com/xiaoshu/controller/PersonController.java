package com.xiaoshu.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Bank;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.BankService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.PersonService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("person")
public class PersonController extends LogController{
	static Logger logger = Logger.getLogger(PersonController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	@Autowired
	private PersonService personService;
	
	@Autowired
	private BankService  bankService ;
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("personIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Role> roleList = roleService.findRole(new Role());
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		List<Bank> blist=bankService.findBank();
		request.setAttribute("blist", blist);
		request.setAttribute("operationList", operationList);
		request.setAttribute("roleList", roleList);
		return "person";
	}
	
	
	@RequestMapping(value="userList",method=RequestMethod.POST)
	public void userList(PersonVo personVo,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<PersonVo> userList= personService.findUserPage(personVo,pageNum,pageSize);
			
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",userList.getTotal() );
			jsonObj.put("rows", userList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveUser")
	public void reserveUser(HttpServletRequest request,Person  person,HttpServletResponse response){
		Integer userId =person.getpId();
		JSONObject result=new JSONObject();
		try {
			if (userId != null) {   // userId不为空 说明是修改
				
					person.setpId(userId);
					personService.updateUser(person);
					result.put("success", true);
				
				
			}else {   // 添加
				
					personService.addUser(person);
					result.put("success", true);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	

	
	// 新增或修改
	@RequestMapping("reserveBank")
	public void reserveBank(HttpServletRequest request,Bank  bank,HttpServletResponse response){
		
		JSONObject result=new JSONObject();
		try {
			
				
					bankService.addBank(bank);
					result.put("success", true);
				
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	//报表
	@RequestMapping("getEcharts")
	public void getEcharts(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			//查询报表
			List<PersonVo> elist = personService.getEcharts();
			result.put("elist", elist);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}

	//导入
		@RequestMapping("importPerson")
		public void importPerson(MultipartFile excelFile,HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				//获取webbook
				HSSFWorkbook wb = new HSSFWorkbook(excelFile.getInputStream());
				//获取sheet页
				HSSFSheet sheet = wb.getSheetAt(0);
				//获取最后一行行数
				int lastRowNum = sheet.getLastRowNum();
				for (int i = 1; i <= lastRowNum; i++) {
					//获取每一行的对象
					HSSFRow row = sheet.getRow(i);
					String pName = row.getCell(0).toString();
					String pSex = row.getCell(1).toString();
					String pCount = row.getCell(2).toString();
					String pLike = row.getCell(3).toString();
					Double numericCellValue = row.getCell(4).getNumericCellValue();
					int pAge = numericCellValue.intValue();
					Date createtime = row.getCell(5).getDateCellValue();
					String bname = row.getCell(6).toString();
					
					int bId = bankService.findCidByBname(bname);
					//封装实体类
					PersonVo person = new PersonVo();
					person.setpName(pName);
					person.setCreatetime(createtime);
					person.setpCount(pCount);
					person.setpLike(pLike);
					person.setbId(bId);
					person.setpSex(pSex);
					person.setpAge(pAge);
					//保存
					personService.addUser(person);
				}
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("删除用户信息错误",e);
				result.put("errorMsg", "对不起，删除失败");
			}
			WriterUtil.write(response, result.toString());
		}


	
}
