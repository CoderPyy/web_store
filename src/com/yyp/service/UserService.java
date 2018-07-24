package com.yyp.service;

import java.sql.SQLException;

import com.yyp.domain.User;

public interface UserService {

	void regist(User user) throws Exception;

	User active(String code) throws SQLException;

	User login(String username, String password) throws SQLException;
	
}
