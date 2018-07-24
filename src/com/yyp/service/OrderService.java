package com.yyp.service;

import java.util.List;

import com.yyp.domain.Order;
import com.yyp.domain.PageBean;
import com.yyp.domain.User;

public interface OrderService {

	void add(Order order) throws Exception;

	PageBean<Order> findAllByPage(Integer currentPage, int pageSize, User user) throws Exception;

	Order getById(String oid) throws Exception;

	void update(Order order) throws Exception;

	List<Order> findAllByState(String state) throws Exception;

}
