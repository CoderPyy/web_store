package com.yyp.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yyp.domain.Product;
import com.yyp.service.ProductService;
import com.yyp.service.impl.ProductServiceImpl;
/**
 * 和首页相关的servlet
 * @author yyp
 *
 */
public class IndexServlet extends BaseServlet {
	
	ProductService ps=new ProductServiceImpl();
	
	public String index(HttpServletRequest request,HttpServletResponse response) throws Exception{
		System.out.println("IndexServlet index 执行了。");
		
		/*//1.调用categoryservice 查询所有的分类  返回值为list
		CategoryService category = new CategoryServiceImpl();
		List<Category> clist=category.findAll();
		
		//2.将返回值放入request域中
		if(clist!=null){
			request.setAttribute("clist", clist);
		}*/
		
		//去数据库中查询最新商品和热门商品，将他们放入request域中 请求转发
		
		//查询最新商品
		List<Product> newList=ps.findNew();
		//查询热门商品
		List<Product> hotList=ps.findHot();
		
		request.setAttribute("NList", newList);
		request.setAttribute("HList", hotList);
		
		//请求转发
		return "/jsp/index.jsp";
		
//		return null;
		
	}
}
