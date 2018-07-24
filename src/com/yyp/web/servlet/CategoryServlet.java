package com.yyp.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yyp.domain.Category;
import com.yyp.service.CategoryService;
import com.yyp.utils.BeanFactory;
import com.yyp.utils.JsonUtil;

public class CategoryServlet extends BaseServlet {

	CategoryService category = (CategoryService) BeanFactory.getBean("CategoryService");

	/**
	 * 查询所有分类的servlet
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		// 1.调用categoryservice 查询所有的分类 返回值为list
		List<Category> clist = category.findAll();

		// 2.將返回值转成json格式 返回到页面上
		if (clist != null) {
			//将返回值放入request域中 
//			req.setAttribute("clist", clist);
			String json=JsonUtil.list2json(clist);
			//写回给浏览器页面上
			resp.setContentType("text/html;charset=utf-8");
			resp.getWriter().print(json);
		}

		return null;
	}
}
