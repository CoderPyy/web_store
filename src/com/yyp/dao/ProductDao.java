package com.yyp.dao;

import java.util.List;

import com.yyp.domain.Product;

public interface ProductDao {

	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;

	Product getById(String pid) throws Exception;

	List<Product> findByPage(Integer currentPage, int pageSize, String cid) throws Exception;

	int getTotalCount(String cid) throws Exception;

	void updateByCid(String cid) throws Exception;

	List<Product> findAll(Integer currentPage, int pageSize) throws Exception;

	List<Product> find_All(Integer currentPage, int pageSize) throws Exception;

	int getToCount() throws Exception;
	
	int getTo_Count() throws Exception;

	void add(Product product) throws Exception;

	void delete(String pid) throws Exception;

	void update(Product product) throws Exception;

	void updateByPflag(String pid) throws Exception;

	void updateBy_Pflag(String pid) throws Exception;


	



}
