package com.yyp.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.yyp.dao.CategoryDao;
import com.yyp.dao.ProductDao;
import com.yyp.domain.Category;
import com.yyp.domain.Product;
import com.yyp.service.CategoryService;
import com.yyp.utils.BeanFactory;
import com.yyp.utils.DataSourceUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CategoryServiceImpl implements CategoryService {
	
	CategoryDao categoryDao=(CategoryDao) BeanFactory.getBean("CategoryDao"); 
	/**
	 * 查询所有商品分类信息
	 */
	@Override
	public List<Category> findAll() throws SQLException {
		
		//1.创建缓存管理器
		CacheManager cm=CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		
		
		//2.获取指定的缓存 将cache看成一个map即可
		Cache cache = cm.getCache("categoryCache");
		
		//3.通过缓存获取数据
		Element element = cache.get("clist");
		
		
		List<Category> list=null;
		
		//4.判断数据
		if(element==null){
			//从数据库中获取
			list=categoryDao.findAll();
			
			//将list放入缓存
			cache.put(new Element("clist", list));
			
			System.out.println("缓存中没有数据，已去数据库中获取");
			
		}else{
			//直接返回
			list = (List<Category>) element.getObjectValue();
			
			System.out.println("缓存中有数据，直接返回");
			
		}
		
		return list;
	}
	
	/**
	 * 清空缓存
	 */
	private void remCache() {
		//更新缓存
		//1.创建缓存管理器
		CacheManager cm=CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
				
				
		//2.获取指定的缓存 将cache看成一个map即可
		Cache cache = cm.getCache("categoryCache");
				
		//3.清空缓存
		cache.remove("clist");
	}
	
	/*public static void main(String[] args) {
		InputStream is = CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
		System.out.println(is);
	}*/
	/**
	 * 添加商品分类信息
	 */
	@Override
	public void add(Category category) throws SQLException {
		categoryDao.add(category);
		
		remCache();
	}

	
	/**
	 * 根据cid删除商品分类
	 * @throws Exception 
	 */
	@Override
	public void delete(String cid) throws Exception{
		
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			
			//2.更新商品信息 根据 cid 置空
			Product product = new Product();
			ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
			pd.updateByCid(cid);
			//3.删除商品分类信息
			categoryDao.delete(cid);
			
			//4.事务控制
			DataSourceUtils.commitAndClose();
			//5.清空缓存
			remCache();
			
		} catch (SQLException e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
		
		
		
		
	}
	/**
	 * 根据 cid获取商品分类信息
	 */
	@Override
	public Category getById(String cid) throws SQLException {
		return categoryDao.getById(cid);
	}
	/**
	 * 更新商品分类信息
	 */
	@Override
	public void udpate(Category category) throws SQLException {
		categoryDao.update(category);
		
		remCache();
		
	}
	/**
	 * 通过pid依赖查询出cid
	 */
	@Override
	public Category getByPid(String pid) throws SQLException {
		
		return categoryDao.getByPid(pid);
	}

}
