package com.yyp.service.impl;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.yyp.dao.UserDao;
import com.yyp.domain.User;
import com.yyp.service.UserService;
import com.yyp.utils.BeanFactory;
import com.yyp.utils.MailUtils;

public class UserServiceImpl implements UserService {

	UserDao dao=(UserDao) BeanFactory.getBean("UserDao");
	
	/**
	 * 用户注册
	 * @throws SQLException 
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	@Override
	public void regist(User user) throws Exception {
		dao.add(user);
		
		//发送邮件
		//email:收件人地址
		//emailMsg:邮件的内容
		String emailMsg="欢迎你注册成我们的一员,<a href='http://47.106.108.40/store/user?method=active&code="+user.getCode()+"'>点击激活</a>";
		MailUtils.sendMail(user.getEmail(), emailMsg);
		
	}
	/**
	 * 用户激活
	 * @throws SQLException 
	 */
	@Override
	public User active(String code) throws SQLException {
		//1.通过code获取一个用户
		User user=dao.getByCode(code);
		
		//2.判断用户为空为空
		if(user==null){
			return null;
		}
		
		//3.修改用户状态
		//设置用户状态码，修改为1
		user.setState(1);
		dao.update(user);
		
		return user;
	}
	/**
	 * 用户登录
	 * @throws SQLException 
	 */
	@Override
	public User login(String username, String password) throws SQLException {
		
		return dao.selectByUsernameAndPwd(username,password);
	}
	
}
