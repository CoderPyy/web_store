package com.yyp.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用的servlet
 * @author yyp
 *
 */
public class BaseServlet extends HttpServlet {
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 1.获取子类
			Class clazz = this.getClass();
			
			System.out.println(this);
			
			// 2.获取请求的方法
			String m = req.getParameter("method");
			if(m==null){
				m="index";
			}

			// 3.获取方法对象

			Method method=clazz.getMethod(m, HttpServletRequest.class, HttpServletResponse.class);
		
			//4.让方法执行 返回值为 请求转发的路径
			String s = (String) method.invoke(this, req,resp);
			
			//5.判断s是否为空
			if(s!=null){
				req.getRequestDispatcher(s).forward(req, resp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();	
		}
	}
	
	
	public String index(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
}
