package com.yyp.service.impl;

import java.util.List;

import com.yyp.dao.ProductDao;
import com.yyp.domain.PageBean;
import com.yyp.domain.Product;
import com.yyp.service.ProductService;
import com.yyp.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {
	
	ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
	
	@Override
	public List<Product> findNew() throws Exception {
		return pd.findNew();
	}

	@Override
	public List<Product> findHot() throws Exception {
		return pd.findHot();
	}

	@Override
	public Product getById(String pid) throws Exception {
		return pd.getById(pid);
	}
	/**
	 * 前台 商品展示
	 */
	@Override
	public PageBean<Product> findByPage(String cid, Integer currentPage, int pageSize) throws Exception {
		
		//当前页数据
		List<Product> list=pd.findByPage(currentPage,pageSize,cid);
		
		//总条数
		int totalCount=pd.getTotalCount(cid);
		
		return new PageBean<Product>(list, currentPage, pageSize, totalCount);
	}
	/**
	 * 后台 商品展示(展示上架商品)
	 */
	@Override
	public PageBean<Product> findAll(Integer currentPage, int pageSize) throws Exception {
		//当前页数据
		List<Product> list=pd.findAll(currentPage,pageSize);
		
		//总条数
		int totalCount=pd.getToCount();
		
		return new PageBean<Product>(list, currentPage, pageSize, totalCount);
	}
	/**
	 * 后台 商品展示(展示下架商品)
	 */
	@Override
	public PageBean<Product> find_All(Integer currentPage, int pageSize) throws Exception {
		// 当前页数据
		List<Product> list = pd.find_All(currentPage, pageSize);

		// 总条数
		int totalCount = pd.getTo_Count();

		return new PageBean<Product>(list, currentPage, pageSize, totalCount);

	}
	
	/**
	 * 添加商品
	 */
	@Override
	public void add(Product product) throws Exception {
		pd.add(product);
	}
	/**
	 * 删除商品
	 */
	@Override
	public void delete(String pid) throws Exception {
		pd.delete(pid);
	}
	/**
	 *更新商品 
	 */
	@Override
	public void update(Product product) throws Exception {
		pd.update(product);
	}
	/**
	 * 通过 pid 更新商品的pflag上下架状态(下架)
	 */
	@Override
	public void updateByPflag(String pid) throws Exception {
		pd.updateByPflag(pid);
	}
	/**
	 * 通过 pid 更新商品的pflag上下架状态(上架)
	 */
	@Override
	public void updateBy_Pflag(String pid) throws Exception {
		pd.updateBy_Pflag(pid);
	}

	

}
