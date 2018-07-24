package com.yyp.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.yyp.dao.ProductDao;
import com.yyp.domain.Product;
import com.yyp.utils.DataSourceUtils;

public class ProductDaoImpl implements ProductDao {
	
	QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
	QueryRunner _qr = new QueryRunner();//为开启事务准备
	
	@Override
	public List<Product> findNew() throws Exception {
		String sql="select * from product order by pdate limit 9";
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> findHot() throws Exception {
		
		String sql="select * from product where is_hot=1 order by pdate limit 9";
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public Product getById(String pid) throws Exception {
		String sql="select * from product where pid=?";
		return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
	}
	/**
	 * 前台 通过 当前页，页大小，商品分类的cid 查询 当前页的数据
	 */
	@Override
	public List<Product> findByPage(Integer currentPage, int pageSize, String cid) throws Exception {
		String sql="select * from product where cid=? limit ?,?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class), cid,(Integer)((currentPage-1)*pageSize),pageSize);
	}
	/**
	 * 前台 通过 商品分类的cid 查询该商品的总条数
	 */
	@Override
	public int getTotalCount(String cid) throws Exception {
		String sql="select count(*) from product where cid=?";
		return ((Long)qr.query(sql, new ScalarHandler(), cid)).intValue();
	}
	/**
	 * 通过商品分类cid 更新该商品  为删除分类的时候准备
	 */
	@Override
	public void updateByCid(String cid) throws Exception {
		String sql="update product set cid=null where cid=?";
		_qr.update(DataSourceUtils.getConnection(), sql, cid);
	}
	/**
	 * 后台 通过 当前页，页大小 查询 当前页的数据(上架商品)
	 */
	@Override
	public List<Product> findAll(Integer currentPage, int pageSize) throws Exception {
		String sql="select * from product where pflag=0 order by pdate desc limit ?,?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class), (currentPage-1)*pageSize,pageSize);
	}
	/**
	 *  后台 通过查询商品的总条数(上架商品)
	 */
	@Override
	public int getToCount() throws Exception {
		String sql="select count(*) from product where pflag=0";
		return ((Long)qr.query(sql, new ScalarHandler())).intValue();
	}
	/**
	 * 后台 通过 当前页，页大小 查询 当前页的数据(下架商品)
	 */
	@Override
	public List<Product> find_All(Integer currentPage, int pageSize) throws Exception {
		String sql="select * from product where pflag=1 order by pdate desc limit ?,?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class), (currentPage-1)*pageSize,pageSize);
	}
	/**
	 *  后台 通过查询商品的总条数(下架商品)
	 */
	@Override
	public int getTo_Count() throws Exception {
		String sql="select count(*) from product where pflag=1";
		return ((Long)qr.query(sql, new ScalarHandler())).intValue();
	}
	
	
	/**
	 *后台 添加商品 
	 *`pid` varchar(32) NOT NULL,
	  `pname` varchar(50) DEFAULT NULL,
	  `market_price` double DEFAULT NULL,
	  `shop_price` double DEFAULT NULL,
	  `pimage` varchar(200) DEFAULT NULL,
	  `pdate` date DEFAULT NULL,
	  `is_hot` int(11) DEFAULT NULL,
	  `pdesc` varchar(255) DEFAULT NULL,
	  `pflag` int(11) DEFAULT NULL,
	  `cid` varchar(32) DEFAULT NULL,
	  PRIMARY KEY (`pid`),
	  KEY `sfk_0001` (`cid`),
	 */
	@Override
	public void add(Product p) throws Exception {
		String sql="insert into product values(?,?,?,?,?,?,?,?,?,?)";
		qr.update(sql, p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(),p.getPimage(),
				p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getPflag(),p.getCategory().getCid());
	}
	/**
	 * 后台 删除商品
	 */
	@Override
	public void delete(String pid) throws Exception {
		String sql="delete from product where pid=?";
		qr.update(sql, pid);
	}
	/**
	 * 后台 更新商品
	 *`pid` varchar(32) NOT NULL,
	  `pname` varchar(50) DEFAULT NULL,
	  `market_price` double DEFAULT NULL,
	  `shop_price` double DEFAULT NULL,
	  `pimage` varchar(200) DEFAULT NULL,
	  `pdate` date DEFAULT NULL,
	  `is_hot` int(11) DEFAULT NULL,
	  `pdesc` varchar(255) DEFAULT NULL,
	  `pflag` int(11) DEFAULT NULL,
	  `cid` varchar(32) DEFAULT NULL,
	  PRIMARY KEY (`pid`),
	  KEY `sfk_0001` (`cid`),
	 */
	@Override
	public void update(Product p) throws Exception {
		String sql="update product set pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=?,pflag=?,cid=? where pid=?";
		qr.update(sql, p.getPname(),p.getMarket_price(),p.getShop_price(),
				p.getPimage(),p.getPdate(),p.getIs_hot(),
				p.getPdesc(),p.getPflag(),p.getCategory().getCid(),p.getPid());
	}
	/**
	 * 后台 通过 pid 更新 商品 pflag 上下架状态 (下架)			  1:下架  0：上架
	 */
	@Override
	public void updateByPflag(String pid) throws Exception {
		String sql="update product set pflag=1 where pid=?";
		qr.update(sql, pid);
	}
	/**
	 * 后台 通过 pid 更新 商品 pflag 上下架状态(上架) 				  1:下架  0：上架
	 */
	@Override
	public void updateBy_Pflag(String pid) throws Exception {
		String sql="update product set pflag=0 where pid=?";
		qr.update(sql, pid);
	}

	

}
