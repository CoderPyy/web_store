package com.yyp.dao.impl;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.taglibs.standard.tag.common.sql.DataSourceUtil;

import com.yyp.dao.UserDao;
import com.yyp.domain.User;
import com.yyp.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao{
	/**
	 * 添加用户
	 */
	@Override
	public void add(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into user values(?,?,?,?,?,?,?,?,?,?)";
		qr.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),
				user.getCode());
	}
	/**
	 * 通过激活码激活用户
	 */
	@Override
	public User getByCode(String code) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from user where code=? limit 1";
		return qr.query(sql, new BeanHandler<User>(User.class), code);
	}
	/**
	 * 通过uid修改用户
	 * public String uid;
		public String username;
		public String password;
		
		public String name;
		public String email;
		public String telephone;
		
		public Date birthday;
		public String sex;
		public Integer state;//激活状态 1：激活 0：未激活
		public String code;
	 */
	@Override
	public void update(User user) throws SQLException {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update user set username=?,password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
		qr.update(sql, user.getUsername(),user.getPassword(),user.getName(),
				user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),
				user.getState(),null,user.getUid());
	}
	/**
	 * 通过查询用户名和密码 返回一个User
	 */
	@Override
	public User selectByUsernameAndPwd(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from user where username=? and password=?";
		return qr.query(sql, new BeanHandler<User>(User.class), username,password);
	}
	
}
