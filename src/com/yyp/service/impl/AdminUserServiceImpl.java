package com.yyp.service.impl;

import com.yyp.dao.AdminUserDao;
import com.yyp.domain.AdminUser;
import com.yyp.service.AdminUserService;
import com.yyp.utils.BeanFactory;

public class AdminUserServiceImpl implements AdminUserService {
	
	AdminUserDao aud=(AdminUserDao) BeanFactory.getBean("AdminUserDao");
	
	@Override
	public AdminUser login(AdminUser adminUser) throws Exception {
		
		return aud.login(adminUser);
	}

}
