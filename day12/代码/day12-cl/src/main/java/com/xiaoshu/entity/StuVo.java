package com.xiaoshu.entity;

import java.util.Date;

import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

public class StuVo extends Stu{

	 
	    private String mdname;
	   
	    private Integer num;
	    
	    public Integer getNum() {
			return num;
		}

		public void setNum(Integer num) {
			this.num = num;
		}

		@DateTimeFormat(pattern="yyyy-MM-dd")
	    private Date start;
	    
	    @DateTimeFormat(pattern="yyyy-MM-dd")
	    private Date end;

		public String getMdname() {
			return mdname;
		}

		public void setMdname(String mdname) {
			this.mdname = mdname;
		}

		public Date getStart() {
			return start;
		}

		public void setStart(Date start) {
			this.start = start;
		}

		public Date getEnd() {
			return end;
		}

		public void setEnd(Date end) {
			this.end = end;
		}
	    

}
