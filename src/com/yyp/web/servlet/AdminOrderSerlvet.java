package com.yyp.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yyp.domain.Order;
import com.yyp.domain.OrderItem;
import com.yyp.service.OrderService;
import com.yyp.utils.BeanFactory;
import com.yyp.utils.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 后台订单模块
 * @author yyp
 *
 */
public class AdminOrderSerlvet extends BaseServlet {
	
	OrderService os=(OrderService) BeanFactory.getBean("OrderService");
	
	/**
	 * 查询订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public  String findAllByState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.接受state
		String state = request.getParameter("state");
		//2.调用service
		List<Order> list=os.findAllByState(state);
		
		//3.将list放入域中，请求转发
		request.setAttribute("list", list);
		
		return "/admin/order/list.jsp";
	}
	
	/**
	 * 查询订单详情(订单项)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public  String getDetailByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//写回去以防出现中文乱码
		response.setCharacterEncoding("utf-8");
		
		//接受oid
		String oid=request.getParameter("oid");
		
		//调用service完成订单详情 返回值 List<OrderItem>
		List<OrderItem> items = os.getById(oid).getItems();
		
		//将其转成json 并返回
		//排除不用写回去的数据
		JsonConfig config = JsonUtil.configJson(new String[]{"class","itemid","order"});
		JSONArray json = JSONArray.fromObject(items, config);
		response.getWriter().println(json);
		System.out.println(json);
		
		return null;
	}
	
	/**
	 *修改订单状态
	 */
	public String updateState(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//1.接受 oid state
		String oid = req.getParameter("oid");
		String state = req.getParameter("state");
		
		//2.调用service
		OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		Order order = os.getById(oid);
		order.setState(Integer.parseInt(state));
		
		os.update(order);
		
		//3.重定向到
		resp.sendRedirect(req.getContextPath()+"/adminOrder?method=findAllByState&state=1");
		
		return null;
	}
}
