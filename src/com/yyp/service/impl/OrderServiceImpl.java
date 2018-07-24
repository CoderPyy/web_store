package com.yyp.service.impl;

import java.util.List;

import com.yyp.dao.OrderDao;
import com.yyp.domain.Order;
import com.yyp.domain.OrderItem;
import com.yyp.domain.PageBean;
import com.yyp.domain.User;
import com.yyp.service.OrderService;
import com.yyp.utils.BeanFactory;
import com.yyp.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {

	OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
	/**
	 * 添加一条订单和多条订单项
	 */
	@Override
	public void add(Order order) throws Exception{
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			
			//2.向orders表中添加一条数据
			od.add(order);
			
			//int i=1/0;//除零异常，用于验证事务是否回滚////注意：事务回滚的前提是 必须是同一个连接
			
			//3.向orderitem表中中添加n条数据
			List<OrderItem> items = order.getItems();
			for (OrderItem orderItem : items) {
				od.addItem(orderItem);
			}
			//4.事务处理
			DataSourceUtils.commitAndClose();
		}catch(Exception e){
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}
	/**
	 * 分页查询我的订单
	 */
	@Override
	public PageBean<Order> findAllByPage(Integer currentPage, int pageSize, User user) throws Exception {
		//查询当前页数据
		List<Order> list=od.findAllByPage(currentPage,pageSize,user.getUid());
		//查询总数量
		int totalCount=od.getTotalCount(user.getUid());
		
		return new PageBean<Order>(list, currentPage, pageSize, totalCount);
	}
	/**
	 * 查询订单详情
	 */
	@Override
	public Order getById(String oid) throws Exception {
		
		return od.getById(oid);
	}
	/**
	 * 
	 */
	@Override
	public void update(Order order) throws Exception {
		od.update(order);
	}
	@Override
	public List<Order> findAllByState(String state) throws Exception {
		return od.findAllByState(state);
	}

}
