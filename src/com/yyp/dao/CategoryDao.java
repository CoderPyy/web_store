package com.yyp.dao;

import java.sql.SQLException;
import java.util.List;

import com.yyp.domain.Category;

public interface CategoryDao {

	List<Category> findAll() throws SQLException;

	void add(Category category) throws SQLException;

	void delete(String cid) throws SQLException;

	Category getById(String cid) throws SQLException;

	void update(Category category) throws SQLException;

	Category getByPid(String pid) throws SQLException;



}
