package com.yyp.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.yyp.dao.CategoryDao;
import com.yyp.domain.Category;
import com.yyp.utils.DataSourceUtils;

public class CategoryDaoImpl implements CategoryDao {

	QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
	QueryRunner _qr = new QueryRunner();//为开启事务准备
	
	@Override
	public List<Category> findAll() throws SQLException {
		String sql="select * from category";
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
	}

	@Override
	public void add(Category category) throws SQLException {
		String sql="insert into category values(?,?)";
		qr.update(sql, category.getCid(),category.getCname());
	}


	@Override
	public Category getById(String cid) throws SQLException {
		String sql="select * from category where cid=?";
		return qr.query(sql, new BeanHandler<Category>(Category.class), cid);
	}

	@Override
	public void update(Category category) throws SQLException {
		String sql="update category set cname=? where cid=?";
		qr.update(sql, category.getCname(),category.getCid());
	}
	
	@Override
	public void delete(String cid) throws SQLException {
		String sql="delete from category where cid=?";
		_qr.update(DataSourceUtils.getConnection(),sql, cid);
	}

	@Override
	public Category getByPid(String pid) throws SQLException {
		String sql="select * from category where cid=(select cid from product where pid=?)";
		return qr.query(sql, new BeanHandler<Category>(Category.class), pid);
	}
}
