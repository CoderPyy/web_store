package com.yyp.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.yyp.constant.Constant;
import com.yyp.domain.User;
import com.yyp.myconverter.MyConverter;
import com.yyp.service.UserService;
import com.yyp.utils.BeanFactory;
import com.yyp.utils.MD5Utils;
import com.yyp.utils.UUIDUtils;

/**
 * 和用户相关的servlet
 * @author yyp
 *
 */
public class UserServlet extends BaseServlet {
	
	UserService s =(UserService) BeanFactory.getBean("UserService");

	public String add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("userservlet 的 add方法执行了。");
		
		return "";
	}
	
	/**
	 * 用户注册界面
	 * 请求转发到 register.jsp页面
	 * @param req
	 * @param resp
	 * @return   
	 * @throws ServletException
	 * @throws IOException
	 */
	public String registUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("userservlet 的 registUI 方法执行了。");
		
		return "/jsp/register.jsp";
	}
	
	/**
	 * 用户注册操作
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception 
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		System.out.println("userservlet 的 regist 方法执行了。");
		
		//验证验证码是否输入正确
		String rcode=req.getParameter("checkcode");
		String scode=(String) req.getSession().getAttribute("sessionCode");
		if(rcode==null||rcode==""||scode==null||scode==""){
			return "/jsp/register.jsp";
		}
		if(!rcode.equalsIgnoreCase(scode)){
			return "/jsp/register.jsp";
		}
		
		//1.封装数据
		User user = new User();
		
		//注册自定义的转换器
		ConvertUtils.register(new MyConverter(), Date.class);
		
		BeanUtils.populate(user, req.getParameterMap());
		
		//设置用户id
		user.setUid(UUIDUtils.getId());
		//设置激活码
		user.setCode(UUIDUtils.getCode());
		//设置用户状态
		user.setState(0);
		//加密密码
		user.setPassword(MD5Utils.md5(user.getPassword()));
		
		//2.调用service完成注册
		s.regist(user);
		
		//3.页面请求转发
		req.setAttribute("msg", "亲，您已注册已成功，请去邮箱激活~");
		
		return "/jsp/msg.jsp";
	}
	/**
	 * 用户注册激活邮箱  /store/user?method=active&code=
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String active(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		System.out.println("userservlet 的 active方法执行了。");
		
		//1.获取激活码
		String code = req.getParameter("code");
		
		//2.调用service完成激活
		User user=s.active(code);
		
		//如果user为空，表示没有找到用户
		if(user==null){
			req.setAttribute("msg", "亲，请重新激活~");
		}else{
			//添加信息
			req.setAttribute("msg", "亲，您已激活成功，可以登录了~");
		}
		
		//3.请求转发到msg.jsp
		
		return "/jsp/msg.jsp";
	}
	
	
	/**
	 * 用户登录界面
	 * 请求转发到 login.jsp页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loginUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("userservlet 的 loginUI 方法执行了。");
		
		return "/jsp/login.jsp";
	}
	/**
	 * 用户登录操作
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		System.out.println("userservlet 的 login 方法执行了。");
		
		//验证验证码是否一致
		String ucode=req.getParameter("checkcode");
		String scode=(String) req.getSession().getAttribute("sessionCode");
		
		if(ucode==null||ucode.trim().length()<=0||scode==null||scode.trim().length()<=0){
			req.setAttribute("msg", "请输入验证码~");
			return "/jsp/login.jsp";
		}
		
		if(!ucode.equalsIgnoreCase(scode)){
			req.setAttribute("msg", "验证码输入不正确~");
			return "/jsp/login.jsp";
		}
		
		
		//获取用户名和密码
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		password=MD5Utils.md5(password);
		
		User user=s.login(username,password);
		if(user==null){
			req.setAttribute("msg", "用户名和密码不匹配");
			return "/jsp/login.jsp";
		}else{
			if(user.getState()!=Constant.USER_IS_ACTIVE){
				req.setAttribute("msg", "亲，您的账号还没有通过邮箱激活，请去激活~");
				return "/jsp/login.jsp";
			}
			//将user放入session中
			req.getSession().setAttribute("user", user);
			
			//判断是否勾选了自动登录 若勾选了需要将用户名和密码放入cookie中，写回浏览器
			if(Constant.IS_AUTO_LOGIN.equals(req.getParameter("autoLogin"))){
				Cookie cookie=new Cookie("autologin", username+"-"+password);
				cookie.setMaxAge(3600);
				cookie.setPath(req.getContextPath()+"/");
				resp.addCookie(cookie);
			}
			
			//判断是否勾选了 保存用户名 若勾选了需要将用户名和密码放入cookie中，写回浏览器
			if(Constant.IS_SAVE_NAME.equals(req.getParameter("saveName"))){
				Cookie cookie = new Cookie("saveName", URLEncoder.encode(username,"utf-8"));
				cookie.setMaxAge(3600);
				cookie.setPath(req.getContextPath()+"/");
				resp.addCookie(cookie);
			}
			
			//重定向到index
			resp.sendRedirect(req.getContextPath());
			
			return null;
		}
		
		
	}
	/**
	 * 退出登录
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("userservlet 的 logout方法执行了。");
		
		//清除session
		req.getSession().invalidate();
		
		
		
		//清除自动登录
		Cookie c=new Cookie("autologin", null);
		c.setMaxAge(0);
		c.setPath(req.getContextPath()+"/");
		resp.addCookie(c);
		
//		Cookie[] cookies=req.getCookies();
//			for(Cookie cookie: cookies){
//			cookie.setMaxAge(0);
//			cookie.setPath("/");
//			resp.addCookie(cookie);
//		}
			
		//页面重定向到首页
		resp.sendRedirect(req.getContextPath());
		
		return null;
	}
	
	
	
	
	
}
