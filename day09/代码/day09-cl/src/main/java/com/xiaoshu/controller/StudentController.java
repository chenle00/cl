package com.xiaoshu.controller;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Course;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.CourseService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("student")
public class StudentController extends LogController{
	static Logger logger = Logger.getLogger(StudentController.class);

	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CourseService courseService ;
	
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("studentIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Role> roleList = roleService.findRole(new Role());
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		List<Course> clist= courseService.findCourse();
		request.setAttribute("clist", clist);
		request.setAttribute("operationList", operationList);
		request.setAttribute("roleList", roleList);
		return "student";
	}
	
	
	@RequestMapping(value="userList",method=RequestMethod.POST)
	public void userList(StudentVo studentVo,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
		
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<StudentVo> userList= studentService.findUserPage(studentVo,pageNum,pageSize);
			
			
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
	public void reserveUser(HttpServletRequest request,Student student,HttpServletResponse response){
		Integer userId = student.getId();
		JSONObject result=new JSONObject();
		try {
			if (userId != null) {   // userId不为空 说明是修改
				
					student.setId(userId);
					studentService.updateUser(student);
					result.put("success", true);
				
				
			}else {   // 添加
				
				studentService.addUser(student);
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
	
	
	@RequestMapping("deleteUser")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				studentService.deleteUser(Integer.parseInt(id));
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
	// 添加展业
		@RequestMapping("reserveCourse")
		public void reserveCourse(HttpServletRequest request,Course course,HttpServletResponse response){
			
			JSONObject result=new JSONObject();
			try {
					courseService.addCourse(course);
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
				List<StudentVo> elist = studentService.getEcharts();
				result.put("elist", elist);
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("删除用户信息错误",e);
				result.put("errorMsg", "对不起，删除失败");
			}
			WriterUtil.write(response, result.toString());
		}
		/**
		 * 备份
		 */
		@RequestMapping("exportStudent")
		public void backup(HttpServletRequest request,HttpServletResponse response){
			JSONObject result = new JSONObject();
			try {
				String time = TimeUtil.formatTime(new Date(), "yyyyMMddHHmmss");
			    String excelName = "手动备份"+time;
			    StudentVo studentVo = new StudentVo();
				List<StudentVo> list = studentService.findPage(studentVo);
				String[] handers = {"编号","学生编号","姓名","年龄","所属年级","入校时间","创建时间","所属课程"};
				// 1导入硬盘
				ExportExcelToDisk(request,response,handers,list, excelName);
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				result.put("", "对不起，备份失败");
			}
		}
		
		// 导出到硬盘
		@SuppressWarnings("resource")
		private void ExportExcelToDisk(HttpServletRequest request,HttpServletResponse response,
				String[] handers, List<StudentVo> list, String excleName) throws Exception {
			
			try {
				HSSFWorkbook wb = new HSSFWorkbook();//创建工作簿
				HSSFSheet sheet = wb.createSheet("操作记录备份");//第一个sheet
				HSSFRow rowFirst = sheet.createRow(0);//第一个sheet第一行为标题
				rowFirst.setHeight((short) 500);
				for (int i = 0; i < handers.length; i++) {
					sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
				}
				//写标题了
				for (int i = 0; i < handers.length; i++) {
				    //获取第一行的每一个单元格
				    HSSFCell cell = rowFirst.createCell(i);
				    //往单元格里面写入值
				    cell.setCellValue(handers[i]);
				}
				for (int i = 0;i < list.size(); i++) {
				    //获取list里面存在是数据集对象
				    StudentVo vo = list.get(i);
				    //创建数据行
				    HSSFRow row = sheet.createRow(i+1);
				    //设置对应单元格的值
				    row.setHeight((short)400);   // 设置每行的高度
//				    "学生编号","姓名","年龄","所属年级","入校时间","创建时间","所属课程"
				    row.createCell(0).setCellValue(i+1);
				    row.createCell(1).setCellValue(vo.getCode());
				    row.createCell(2).setCellValue(vo.getName());
				    row.createCell(3).setCellValue(vo.getAge());
				    row.createCell(4).setCellValue(vo.getGrade());
				    row.createCell(5).setCellValue(TimeUtil.formatTime(vo.getEntrytime(), "yyyy-MM-dd"));
				    row.createCell(6).setCellValue(TimeUtil.formatTime(vo.getCreatetime(), "yyyy-MM-dd"));
				    row.createCell(7).setCellValue(vo.getCname());
				}
				//写出文件（path为文件路径含文件名）
					response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("员工列表.xls", "UTF-8"));
					response.setHeader("Connection", "close");
					response.setHeader("Content-Type", "application/octet-stream");
			        wb.write(response.getOutputStream());
			        wb.close();
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
		}


	
}
