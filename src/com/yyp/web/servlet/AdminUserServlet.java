package com.yyp.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.yyp.domain.AdminUser;
import com.yyp.service.AdminUserService;
import com.yyp.utils.BeanFactory;
import com.yyp.utils.MD5Utils;
/**
 * 后台 管理员用户
 * @author yyp
 *
 */
public class AdminUserServlet extends BaseServlet {
	
	AdminUserService aus=(AdminUserService) BeanFactory.getBean("AdminUserService");
	
	public String login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		System.out.println("AdminUserServlet login 方法执行了==");
		
		//验证 验证码是否一致
		String checkcode = req.getParameter("checkcode");
		String sessionCode = (String) req.getSession().getAttribute("sessionCode");
		if(checkcode==null||checkcode.trim().length()<=0||sessionCode==null||sessionCode.trim().length()<=0){
			req.setAttribute("msg", "验证码不能为空~");
			return "/admin/index.jsp";
		}
		if(!checkcode.equalsIgnoreCase(sessionCode)){
			req.setAttribute("msg", "验证码输入不正确~");
			return "/admin/index.jsp";
		}
		
		//接受参数并且封装adminUser
		AdminUser adminUser = new AdminUser();
		BeanUtils.populate(adminUser, req.getParameterMap());
		String md5 = MD5Utils.md5(adminUser.getPassword());
		adminUser.setPassword(md5);
		
		AdminUser user=aus.login(adminUser);
		if(user==null){
			req.setAttribute("msg", "用户名或者密码不存在~");
			return "/admin/index.jsp";
		}else{
			req.getSession().setAttribute("adminuser", user);
		}
		
		//重定向到home.jsp
		resp.sendRedirect(req.getContextPath()+"/admin/home.jsp");
		
		return null;
	}
}
