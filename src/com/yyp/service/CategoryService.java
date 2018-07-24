package com.yyp.service;

import java.sql.SQLException;
import java.util.List;

import com.yyp.domain.Category;

public interface CategoryService {

	List<Category> findAll() throws SQLException;

	void add(Category category) throws SQLException;

	void delete(String cid) throws Exception;

	Category getById(String cid) throws SQLException;

	void udpate(Category category) throws SQLException;

	Category getByPid(String pid) throws SQLException;
	
	
}