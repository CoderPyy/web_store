package com.yyp.dao;

import java.sql.SQLException;

import com.yyp.domain.User;

public interface UserDao {

	void add(User user) throws SQLException;

	User getByCode(String code) throws SQLException;

	void update(User user) throws SQLException;

	User selectByUsernameAndPwd(String username, String password) throws SQLException;

}
