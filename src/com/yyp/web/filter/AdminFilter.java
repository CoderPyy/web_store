package com.yyp.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yyp.domain.AdminUser;

public class AdminFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		//强转
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		
		//处理管理员权限(只有登录了之后才能访问)
		AdminUser user=(AdminUser) request.getSession().getAttribute("adminuser");
		if(user==null){
			request.setAttribute("msg", "您没有权限，请先登录~");
			request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
		}
		
		//放行
		arg2.doFilter(request, response);
	}

	

}
