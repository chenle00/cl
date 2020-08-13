package com.xiaoshu.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "tb_major_day")
public class Major implements Serializable {
    @Id
    private Integer mdid;

    private String mdname;

    private static final long serialVersionUID = 1L;

    /**
     * @return mdid
     */
    public Integer getMdid() {
        return mdid;
    }

    /**
     * @param mdid
     */
    public void setMdid(Integer mdid) {
        this.mdid = mdid;
    }

    /**
     * @return mdname
     */
    public String getMdname() {
        return mdname;
    }

    /**
     * @param mdname
     */
    public void setMdname(String mdname) {
        this.mdname = mdname == null ? null : mdname.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", mdid=").append(mdid);
        sb.append(", mdname=").append(mdname);
        sb.append("]");
        return sb.toString();
    }
}