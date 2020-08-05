package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "tb_stu_day")
public class Stu implements Serializable {
    @Id
    private Integer sdid;

    private String sdname;

    private String sdsex;

    
    private String sdhobby;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date sdbirth;

    private Integer mid;

    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date start;
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date end;
    @Transient
    private String mdname;
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

	public String getMdname() {
		return mdname;
	}

	public void setMdname(String mdname) {
		this.mdname = mdname;
	}

	private static final long serialVersionUID = 1L;

    /**
     * @return sdid
     */
    public Integer getSdid() {
        return sdid;
    }

    /**
     * @param sdid
     */
    public void setSdid(Integer sdid) {
        this.sdid = sdid;
    }

    /**
     * @return sdname
     */
    public String getSdname() {
        return sdname;
    }

    /**
     * @param sdname
     */
    public void setSdname(String sdname) {
        this.sdname = sdname == null ? null : sdname.trim();
    }

    /**
     * @return sdsex
     */
    public String getSdsex() {
        return sdsex;
    }

    /**
     * @param sdsex
     */
    public void setSdsex(String sdsex) {
        this.sdsex = sdsex == null ? null : sdsex.trim();
    }

    /**
     * @return sdhobby
     */
    public String getSdhobby() {
        return sdhobby;
    }

    /**
     * @param sdhobby
     */
    public void setSdhobby(String sdhobby) {
        this.sdhobby = sdhobby == null ? null : sdhobby.trim();
    }

    /**
     * @return sdbirth
     */
    public Date getSdbirth() {
        return sdbirth;
    }

    /**
     * @param sdbirth
     */
    public void setSdbirth(Date sdbirth) {
        this.sdbirth = sdbirth;
    }

    /**
     * @return mid
     */
    public Integer getMid() {
        return mid;
    }

    /**
     * @param mid
     */
    public void setMid(Integer mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sdid=").append(sdid);
        sb.append(", sdname=").append(sdname);
        sb.append(", sdsex=").append(sdsex);
        sb.append(", sdhobby=").append(sdhobby);
        sb.append(", sdbirth=").append(sdbirth);
        sb.append(", mid=").append(mid);
        sb.append("]");
        return sb.toString();
    }
}