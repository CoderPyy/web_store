package com.yyp.dao;

import java.util.List;

import com.yyp.domain.Order;
import com.yyp.domain.OrderItem;

public interface OrderDao {

	void add(Order order) throws Exception;

	void addItem(OrderItem orderItem) throws Exception;

	List<Order> findAllByPage(Integer currentPage, int pageSize, String uid) throws Exception;

	int getTotalCount(String uid) throws Exception;

	Order getById(String oid) throws Exception;

	void update(Order order) throws Exception;

	List<Order> findAllByState(String state) throws Exception;
}
