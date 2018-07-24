package com.yyp.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yyp.domain.User;
import com.yyp.service.impl.UserServiceImpl;
import com.yyp.utils.CookieUtils;
/**
 * 完成自动登录的过滤操作
 * @author yyp
 *
 */
public class AutoLoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 强转
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// 完成自动登录操作
		// 判断session中是否为空，为空才自动登录，然后读取cookie,判断不是和注册&登录相关的界面才调用service的login操作，最后将user放入session中
		User user = (User) req.getSession().getAttribute("user");
		if (user == null) {
			String path = req.getRequestURL().toString();
			// regist registUI active login loginUI
			if (!path.contains("/user")){
				
					Cookie c = CookieUtils.getCookieByName("autologin", req.getCookies());
				
					if (c != null) {
						String username = c.getValue().split("-")[0];
						String password = c.getValue().split("-")[1];
						try {
							user = new UserServiceImpl().login(username, password);
	
						} catch (Exception e) {
							e.printStackTrace();
						}
	
						if (user != null) {
							req.getSession().setAttribute("user", user);
						}
	
					}

			}

		}

		// 放行
		chain.doFilter(req, resp);
	}
		
		
		
		

	@Override
	public void destroy() {

	}

}
