package com.yyp.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.yyp.dao.OrderDao;
import com.yyp.domain.Order;
import com.yyp.domain.OrderItem;
import com.yyp.domain.Product;
import com.yyp.utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {
	
	QueryRunner qr=new QueryRunner();
	QueryRunner _qr=new QueryRunner(DataSourceUtils.getDataSource());
	
	
	/**
	 * 添加一条订单
	 * 表orders
	 * `oid` varchar(32) NOT NULL,
	  `ordertime` datetime DEFAULT NULL,
	  `total` double DEFAULT NULL,
	  
	  `state` int(11) DEFAULT NULL,
	  `address` varchar(30) DEFAULT NULL,
	  `name` varchar(20) DEFAULT NULL,
	  
	  `telephone` varchar(20) DEFAULT NULL,
	  `uid` varchar(32) DEFAULT NULL,
	 * @throws SQLException 
	 * 
	 */
	@Override
	public void add(Order order) throws SQLException {
		String sql="insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(),sql, order.getOid(),order.getOrdertime(),order.getTotal(),
				order.getState(),order.getAddress(),order.getName(),
				order.getTelephone(),order.getUser().getUid());
	}
	/**
	 * 添加一条订单项
	 * 表orderitem
	 * `itemid` varchar(32) NOT NULL,
	  `count` int(11) DEFAULT NULL,
	  `subtotal` double DEFAULT NULL,
	  `pid` varchar(32) DEFAULT NULL,
	  `oid` varchar(32) DEFAULT NULL,
	 * @throws SQLException 
		 */
	@Override
	public void addItem(OrderItem orderItem) throws SQLException {
		String sql="insert into orderitem values(?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(),sql, orderItem.getItemid(),orderItem.getCount(),orderItem.getSubtotal(),
				orderItem.getProduct().getPid(),orderItem.getOrder().getOid());
	}
	/**
	 * 分页查询我的订单的数据
	 */
	@Override
	public List<Order> findAllByPage(Integer currentPage, int pageSize, String uid) throws Exception {
		String sql="select * from orders where uid=? order by ordertime desc limit ?,?";
		List<Order> list=_qr.query(sql, new BeanListHandler<Order>(Order.class), uid,(currentPage-1)*pageSize,pageSize);
		
		//遍历订单集合 封装每个订单的订单项列表
		 sql="select * from orderitem oi,product p where oi.pid=p.pid and oi.oid=?";
		for (Order order : list) {
			//当前订单的所有内容
			List<Map<String, Object>> mList = _qr.query(sql, new MapListHandler(), order.getOid());
			for (Map<String, Object> map : mList) {
				//封装product
				Product p = new Product();
				BeanUtils.populate(p, map);
				
				//封装orderItem
				OrderItem oi = new OrderItem();
				BeanUtils.populate(oi, map);
				
				oi.setProduct(p);
				
				//将orderItem对象添加到对应的order对象的list集合中
				order.getItems().add(oi);
			}
		
		}
		
		return list;
	}
	/**
	 * 查询我的订单的数量
	 */
	@Override
	public int getTotalCount(String uid) throws Exception {
		String sql="select count(*) from orders where uid=?";
		return ((Long) _qr.query(sql, new ScalarHandler(), uid)).intValue();
	}
	/**
	 * 通过 oid 查询我的订单详情
	 */
	@Override
	public Order getById(String oid) throws Exception {
		String sql="select * from orders where oid=?";
		 Order order = _qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		 
		 //封装orderitems
		 sql="select * from orderitem oi,product p where oi.pid=p.pid and oi.oid=?";
		 List<Map<String, Object>> mList = _qr.query(sql, new MapListHandler(), oid);
		 for (Map<String, Object> map : mList) {
			//封装product
			 Product product = new Product();
			 BeanUtils.populate(product, map);
			 
			 //封装orderitem
			 OrderItem orderItem = new OrderItem();
			 BeanUtils.populate(orderItem, map);
			 orderItem.setProduct(product);
			
			 //将orderItem对象添加到对应的order对象的list集合中
			 order.getItems().add(orderItem);
		 
		 }
		 return order;
	}
	/**
	 * 修改订单
	 * 表orders
	  `state` int(11) DEFAULT NULL,
	  `address` varchar(30) DEFAULT NULL,
	  `name` varchar(20) DEFAULT NULL,
	  
	  `telephone` varchar(20) DEFAULT NULL,
	  `uid` varchar(32) DEFAULT NULL,
	 */
	@Override
	public void update(Order order) throws Exception {
		String sql="update orders set state=?,address=?,name=?,telephone=? where oid=?";
		_qr.update(sql, order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
	}
	/**
	 * 根据订单状态 查询订单
	 */
	@Override
	public List<Order> findAllByState(String state) throws Exception {
		String sql="select * from orders where 1=1";
		if(state!=null&&state.trim().length()>0){
			sql+=" and state=? order by ordertime desc";
			return _qr.query(sql, new BeanListHandler<Order>(Order.class), state);
		}
		sql+=" order by ordertime desc";
		return _qr.query(sql, new BeanListHandler<Order>(Order.class));
	}

}
