package com.yyp.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.yyp.domain.Category;
import com.yyp.domain.Product;
import com.yyp.service.ProductService;
import com.yyp.utils.BeanFactory;
import com.yyp.utils.UploadUtils;
/**
 * 后台 修改商品
 * @author yyp
 *
 */
public class UpdateProductServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//0.创建map 放入前台传递的数据
			HashMap<String,Object> map = new HashMap<>();
			
			//0.1创建磁盘文件项
			DiskFileItemFactory factory=new DiskFileItemFactory();
			
			//0.2创建核心上传对象
			ServletFileUpload upload=new ServletFileUpload(factory);
			
			//0.3解析request
			List<FileItem> list = upload.parseRequest(req);
			
			//0.4遍历集合
			for (FileItem fi : list) {
				//判断是否是普通上传的组件
				if(fi.isFormField()){
					//普通上传组件
					map.put(fi.getFieldName(), fi.getString("utf-8"));
				}else{
					//文件上传组件
					//获取文件名称
					String name=fi.getName();
					
					//获取文件的真实名称 xxxx.xx
					String realName = UploadUtils.getRealName(name);
					
					//获取文件的随机名称
					String uuidName = UploadUtils.getUUIDName(realName);
				
					//获取文件的存放路径
					String path = this.getServletContext().getRealPath("/products/1");
					
					//获取文件流对象
					InputStream is = fi.getInputStream();
					//保存图片
					FileOutputStream os = new FileOutputStream(new File(path, uuidName));
					
					IOUtils.copy(is, os);
					os.close();
					is.close();
					
					//删除临时文件
					fi.delete();
					
					//在map中设置文件上传的路径
					map.put(fi.getFieldName(), "products/1/"+uuidName);
				}
				
			}
			
			//1.封住参数
			Product product = new Product();
			BeanUtils.populate(product, map);
			
			//1.1商品时间
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			product.setPdate(Timestamp.valueOf(sdf.format(date)));
			
			//1.3封装category
			Category c = new Category();
			c.setCid((String)(map.get("cid")));
			
			product.setCategory(c);
			
			//2.调用service
			ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
			ps.update(product);
			
			//3.页面重定向
			resp.sendRedirect(req.getContextPath()+"/adminProduct?method=findAll&currentPage=1");
			
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "商品修改失败~");
			req.getRequestDispatcher("/jps/msg.jsp").forward(req, resp);
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
