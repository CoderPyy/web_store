package com.yyp.service;

import java.util.List;

import com.yyp.domain.PageBean;
import com.yyp.domain.Product;

public interface ProductService {

	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;

	Product getById(String pid) throws Exception;

	PageBean<Product> findByPage(String cid, Integer currentPage, int pageSize) throws Exception;

	PageBean<Product> findAll(Integer currentPage, int pageSize) throws Exception;

	PageBean<Product> find_All(Integer currentPage, int pageSize) throws Exception;

	void add(Product product) throws Exception;

	void delete(String pid) throws Exception;

	void update(Product product) throws Exception;

	void updateByPflag(String pid) throws Exception;

	void updateBy_Pflag(String pid) throws Exception;


}
