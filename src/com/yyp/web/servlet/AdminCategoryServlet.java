package com.yyp.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yyp.domain.Category;
import com.yyp.service.CategoryService;
import com.yyp.utils.BeanFactory;
import com.yyp.utils.UUIDUtils;
/**
 * 商城后台 商品分类servlet
 * @author yyp
 *
 */
public class AdminCategoryServlet extends BaseServlet {
	
	CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
	
	/**
	 * 查询所有商品分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		System.out.println("AdminCategoryServlet 的 findAll方法执行了。");
		
		//调用service 查询分类信息
		List<Category> list=cs.findAll();
		
		//放入request中，请求转发
		req.setAttribute("bean", list);
		
		return "/admin/category/list.jsp";
	}
	/**
	 * 添加的UI界面
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		return "/admin/category/add.jsp";
	}
	
	
	/**
	 * 添加商品分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取cname
		String cname=req.getParameter("cname");
		String cid=UUIDUtils.getId();
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		//调用service 添加
		cs.add(category);
		
		//重定向到list.jsp
		resp.sendRedirect(req.getContextPath()+"/adminCategory?method=findAll");
		
		return null;
	}
	
	/**
	 * 通过cid 获取商品分类信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String getById(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取 cid
		String cid=req.getParameter("cid");
		
		//调用service 完成获取
		Category bean=cs.getById(cid);
		
		//放入request域中，请求转发到 edit.jsp
		req.setAttribute("bean", bean);
		return "/admin/category/edit.jsp";
	}
	/**
	 * 更新修改商品分类信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String udpate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取cid 和 cname
		String cid=req.getParameter("cid");
		String cname=req.getParameter("cname");
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		//调用service 更新
		cs.udpate(category);
		
		//重定向到 list.jsp
		resp.sendRedirect(req.getContextPath()+"/adminCategory?method=findAll");
		return null;
	}
	/**
	 * 通过cid 删除商品分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取cid
		String cid=req.getParameter("cid");
		
		//调用service 删除
		cs.delete(cid);
		
		//重定向到list.jsp
		resp.sendRedirect(req.getContextPath()+"/adminCategory?method=findAll");
		
		return null;
	}
	
	
	
}
