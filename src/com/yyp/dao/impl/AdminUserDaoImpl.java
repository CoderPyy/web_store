package com.yyp.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.yyp.dao.AdminUserDao;
import com.yyp.domain.AdminUser;
import com.yyp.utils.DataSourceUtils;

public class AdminUserDaoImpl implements AdminUserDao{
	
	QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
	
	@Override
	public AdminUser login(AdminUser adminUser) throws Exception {
		String sql="select * from adminuser where username=? and password=?";
		return qr.query(sql, new BeanHandler<AdminUser>(AdminUser.class), adminUser.getUsername(),adminUser.getPassword());
	}

}
