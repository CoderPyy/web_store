package com.yyp.dao;

import com.yyp.domain.AdminUser;

public interface AdminUserDao {

	AdminUser login(AdminUser adminUser)throws Exception;

}
