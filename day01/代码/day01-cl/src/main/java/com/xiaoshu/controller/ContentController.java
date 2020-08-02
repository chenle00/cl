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
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.Contentcategory;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.ContentService;
import com.xiaoshu.service.ContentcategoryService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("content")
public class ContentController extends LogController{
	static Logger logger = Logger.getLogger(ContentController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private ContentcategoryService contentcategoryService ;
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("contentIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Role> roleList = roleService.findRole(new Role());
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		List<Contentcategory> clist=contentcategoryService.findContentcategory();
		request.setAttribute("clist", clist);
		request.setAttribute("operationList", operationList);
		request.setAttribute("roleList", roleList);
		return "content";
	}
	
	
	@RequestMapping(value="userList",method=RequestMethod.POST)
	public void userList(Content content,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<Content> userList= contentService.findUserPage(content,pageNum,pageSize);
			
			
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
	public void reserveUser(HttpServletRequest request,Content content,HttpServletResponse response){
		Integer cid =content.getContentid();
		JSONObject result=new JSONObject();
		try {
			if (cid != null) {   // userId不为空 说明是修改
				
					content.setContentid(cid);
					contentService.updateUser(content);
					result.put("success", true);
				
				
			}else {   // 添加
				if(contentService.existUserWithUserName(content.getContenttitle())==null){  // 没有重复可以添加
					contentService.addUser(content);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("deleteUser")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				contentService.deleteUser(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	//导入
		@RequestMapping("importContent")
		public void importContent(MultipartFile excelFile,HttpServletRequest request,HttpServletResponse response){
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
					String contenttitle = row.getCell(0).toString();
					String picpath = row.getCell(1).toString();
				
					String price = row.getCell(2).toString();
					String staus = row.getCell(3).toString();
					String contenturl = row.getCell(4).toString();
					Date createtime = row.getCell(5).getDateCellValue();
					String categoryname = row.getCell(6).toString();
					
					int contentcategoryid = contentService.findCidByCname(categoryname);
					//封装实体类
					Content person = new Content();
					person.setContenttitle(contenttitle);;
					person.setPicpath(picpath);
					person.setPrice(price);
					person.setStaus(staus);
					person.setContentcategoryid(contentcategoryid);
					person.setContenturl(contenturl);
					person.setCreatetime(createtime);
					//保存
					contentService.addUser(person);
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
