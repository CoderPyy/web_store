package com.yyp.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yyp.domain.Category;
import com.yyp.domain.Order;
import com.yyp.domain.PageBean;
import com.yyp.domain.Product;
import com.yyp.service.CategoryService;
import com.yyp.service.OrderService;
import com.yyp.service.ProductService;
import com.yyp.utils.BeanFactory;

public class AdminProductServlet extends BaseServlet {
	
	ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
	
	/**
	 * 后台分页展示 商品信息(展示上架商品)
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//1.获取当前页 currentPage
		Integer currentPage=Integer.parseInt(req.getParameter("currentPage"));
		
		int pageSize=8;
		
		//2.调用service 返回 PageBean
		PageBean<Product> pagebean=ps.findAll(currentPage,pageSize);
		
		//将product放入request域中
		req.setAttribute("pb", pagebean);

		//请求转发
		return "/admin/product/list.jsp";
	}
	
	/**
	 * 后台分页展示 商品信息(展示下架商品)
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String find_All(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//1.获取当前页 currentPage
		Integer currentPage=Integer.parseInt(req.getParameter("currentPage"));
		
		int pageSize=8;
		
		//2.调用service 返回 PageBean
		PageBean<Product> pagebean=ps.find_All(currentPage,pageSize);
		
		//将product放入request域中
		req.setAttribute("pb", pagebean);

		//请求转发
		return "/admin/product/list_.jsp";
	}
	
	/**
	 * 后台商品 添加页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		//查询所有商品的分类
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = cs.findAll();
		req.setAttribute("clist", clist);
		
		return "/admin/product/add.jsp";
	}
	
	/**
	 * 后台 通过 pid 删除商品
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取pid
		String pid=req.getParameter("pid");
		//调用service
		ps.delete(pid);
		//重定向到 findAll
		resp.sendRedirect(req.getContextPath()+"/adminProduct?method=findAll&currentPage=1");
		
		return null;
	}
	
	/**
	 * 后台 商品 修改 页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updateUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 查询所有商品的分类
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = cs.findAll();
		req.setAttribute("clist", clist);
		
		
		//通过pid 获取商品信息 然后写回 edit.jsp
		String pid=req.getParameter("pid");
		Product p=ps.getById(pid);
		req.getSession().setAttribute("bean", p);
		
		Category c=cs.getByPid(pid);//通过pid依赖查询查询出cid
		
		req.getServletContext().setAttribute("cat",c);
		
		return "/admin/product/edit.jsp";
		
	}
	
	/**
	 * 后台 更新商品的pflag状态(下架)    1: 下架   0:上架    
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updateByPflag(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		//获取商品pid
		String pid=req.getParameter("pid");
		
		//调用service
		ps.updateByPflag(pid);
		
		//重定向到 findAll
		resp.sendRedirect(req.getContextPath()+"/adminProduct?method=findAll&currentPage=1");
		
		return null;
	}
	/**
	 * 后台 更新商品的pflag状态(上架)    1: 下架   0:上架    
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updateBy_Pflag(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		//获取商品pid
		String pid=req.getParameter("pid");
		
		//调用service
		ps.updateBy_Pflag(pid);
		
		//重定向到 findAll
		resp.sendRedirect(req.getContextPath()+"/adminProduct?method=find_All&currentPage=1");
		
		return null;
	}
	
}
