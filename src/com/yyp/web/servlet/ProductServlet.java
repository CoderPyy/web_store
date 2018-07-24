package com.yyp.web.servlet;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yyp.domain.PageBean;
import com.yyp.domain.Product;
import com.yyp.service.ProductService;
import com.yyp.utils.BeanFactory;
import com.yyp.utils.CookieUtils;
/**
 * 商品servlet
 * @author yyp
 *
 */
public class ProductServlet extends BaseServlet {
	
	ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
	
	/**
	 * 通过id查询单个商品详情
	 * @throws Exception sss
	 */
	public String getById(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//1.获取商品 id
		String pid=req.getParameter("pid");
		//2.调用service
		Product product=ps.getById(pid);
		
		//将product放入request域中
		req.setAttribute("bean", product);
		
		//将商品pid放入Cookie中(记录上次的访问记录)，写回给浏览器
		//规定约束：ids 3-2-1
		Cookie c = CookieUtils.getCookieByName("ids", req.getCookies());
		String ids="";
		if(c==null){
			//cookie为空
			ids=pid;
		}else{
			//cookie不为空
			ids=c.getValue();
			String[] arr=ids.split("-");
			//将数组转成List集合
			List<String> asList=Arrays.asList(arr);
			//将asList放入一个list中
			LinkedList<String> list=new LinkedList<>(asList);
			if(list.contains(pid)){
				//如果ids中包含 pid
				list.remove(pid);
				list.addFirst(pid);
			}else{
				//如果ids不包含pid
				//继续判断长度是否大于2
				if(list.size()>2){
					list.removeLast();
					list.addFirst(pid);
				}else{
					list.addFirst(pid);
				}
				
				ids="";
				//将list转成字符串
				for (String s : list) {
					ids+=(s+"-");
				}
				ids=ids.substring(0,ids.length()-1);
			}
		}
		
		//将ids写回去
		c=new Cookie("ids",ids);
		c.setPath(req.getContextPath()+"/");
		c.setMaxAge(3600);
		resp.addCookie(c);
		

		//请求转发
		return "/jsp/product_info.jsp";
	}
	/**
	 * 前台分页展示 商品信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findByPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//1.获取商品分类 cid 和 当前页 currentPage
		String cid=req.getParameter("cid");
		Integer currentPage=Integer.parseInt(req.getParameter("currentPage"));
		
		int pageSize=12;
		
		//2.调用service 返回 PageBean
		PageBean<Product> pagebean=ps.findByPage(cid,currentPage,pageSize);
		
		//将product放入request域中
		req.setAttribute("pb", pagebean);

		//请求转发
		return "/jsp/product_list.jsp";
	}
}
