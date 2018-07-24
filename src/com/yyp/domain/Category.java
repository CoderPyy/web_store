package com.yyp.domain;

import java.io.Serializable;

/**
 * 分类实体
 * @author yyp
 *
 */
public class Category implements Serializable{
	
	/**
	 *  cid   cname
	 */
	private String cid;
	private String cname;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	
}
