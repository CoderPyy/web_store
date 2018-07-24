package com.yyp.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yyp.domain.Cart;
import com.yyp.domain.CartItem;
import com.yyp.domain.Product;
import com.yyp.service.ProductService;
import com.yyp.utils.BeanFactory;
/**
 * 购物车servlet
 * @author yyp
 *
 */
public class CartServlet extends BaseServlet {
	
	public String cartUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		//重定向
		resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		
		return null;
	}
	
	/**
	 * 获取一个购物车(存放在session中)
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public Cart getCart(HttpServletRequest req) throws Exception{
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		//如果session中没有购物车
		if(cart==null){
			cart = new Cart();
			req.getSession().setAttribute("cart", cart);
		}
		return cart;
	}
	/**
	 * 添加到购物车
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//1.获取pid count
		String pid = req.getParameter("pid");
		Integer count = Integer.parseInt(req.getParameter("count"));
		//2.通过Pid调用service获取一个商品
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
		Product product = ps.getById(pid);
		//3.组装成CartItem
		CartItem item = new CartItem(product, count);
		
		//4.添加到购物车
		Cart cart = getCart(req);
		cart.add2Cart(item);
		
		//5.重定向
		resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	/**
	 * 通过pid删除这个购物车项
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String remove(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//1.获取pid
		String pid = req.getParameter("pid");
		//2.获取购物车
		Cart cart = getCart(req);
		//3.删除购物车项
		cart.removeFromCart(pid);
		
		//4.重定向
		resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		
		return null;
	}
	/**
	 * 清空购物车里的购物车项
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String clear(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//1.获取购物车
		Cart cart = getCart(req);
		
		//2.删除购物车项
		cart.clearCart();
		
		//3.重定向
		resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		
		return null;
	}
	
	
	
	
	
	
	
}
