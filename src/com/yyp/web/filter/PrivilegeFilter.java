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

import com.yyp.domain.User;

public class PrivilegeFilter implements Filter {
	
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		//1.强转
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		//2.处理业务逻辑
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			request.setAttribute("msg", "亲，您没有权限查看，请登录~");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
			return;
		}
		//3.放行
		arg2.doFilter(request, response);
	}

	

}
