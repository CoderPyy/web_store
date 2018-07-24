<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
		<script type="text/javascript">
			function delOne(pid){
				if(confirm("您确认删掉这个商品吗？")){
					window.location.href="${ pageContext.request.contextPath }/adminProduct?method=delete&pid="+pid;
				}
			}
			
			function upOne(pid){
				if(confirm("您确认上架这个商品吗？")){
					window.location.href="${ pageContext.request.contextPath }/adminProduct?method=updateBy_Pflag&pid="+pid;
				}
													
			}
			
		</script>
	</HEAD>
	<body>
		<br>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>商品列表</strong>
						</TD>
					</tr>
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr
									style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="6%">
										序号
									</td>
									<td align="center" width="13%">
										商品图片
									</td>
									<td align="center" width="13%">
										商品名称
									</td>
									<td align="center" width="11%">
										商品价格
									</td>
									<td align="center" width="11%">
										是否热门
									</td>
									<!-- <td align="center" width="17%">
										是否下架
									</td> -->
									<!-- <td align="center">
										所属分类
									</td> -->
									<td width="7%" align="center">
										编辑
									</td>
									<td width="7%" align="center">
										删除
									</td>
									<td width="7%" align="center">
										上架
									</td>
								</tr>
								<c:forEach items="${pb.list }" var="p" varStatus="vs">
										<tr onmouseover="this.style.backgroundColor = 'white'"
											onmouseout="this.style.backgroundColor = '#F5FAFE';">
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="6%">
												${vs.count }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="13%">
												<img width="40" height="45" src="${ pageContext.request.contextPath }/${p.pimage}">
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="13%">
												${p.pname }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="11%">
												${p.shop_price }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="11%">
												<c:if test="${p.is_hot==1 }">是</c:if>
												<c:if test="${p.is_hot!=1 }">否</c:if>
											</td>
											<%-- <td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="10%">
												<c:if test="${p.pflag==1 }">是</c:if>
												<c:if test="${p.pflag!=1 }">否</c:if>
											</td> --%>
											<%-- <td style="CURSOR: hand; HEIGHT: 22px" align="center"
												>
												${p.category.cid }
											</td> --%>
											<td align="center" style="HEIGHT: 22px">
												<a href="${pageContext.request.contextPath }/adminProduct?method=updateUI&pid=${p.pid}">
													<img src="${pageContext.request.contextPath}/images/i_edit.gif" border="0" style="CURSOR: hand">
												</a>
											</td>
									
											<td align="center" style="HEIGHT: 22px">
												<a href="javascript:void(0)" onclick="delOne('${p.pid}')">
													<img src="${pageContext.request.contextPath}/images/i_del.gif" width="16" height="16" border="0" style="CURSOR: hand">
												</a>
											</td>
											<td align="center" style="HEIGHT: 22px">
												<a href="javascript:void(0)" onclick="upOne('${p.pid}')">
													<img src="${pageContext.request.contextPath}/images/i_del.gif" width="16" height="16" border="0" style="CURSOR: hand">
												</a>
											</td>
										</tr>
									</c:forEach>
							</table>
						</td>
					</tr>
				</TBODY>
			</table>
		</form>
		
		<!--分页 -->
		<div style="width:380px;margin:0 auto;margin-top:50px;">
			<ul class="pagination" style="text-align:center; margin-top:10px;">
				<!-- 判断当前页是否是首页 -->
				<c:if test="${pb.currentPage==1 }">
					<li class="disabled">
						<a href="javascript:void(0)" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
					</li>
				</c:if>
				<c:if test="${pb.currentPage!=1 }">
					<li>
						<a href="${pageContext.request.contextPath}/adminProduct?method=findAll&currentPage=${pb.currentPage-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
					</li>
				</c:if>
				<!-- 展示所有的页码 -->
				<c:forEach begin="${pb.currentPage-5>0?pb.currentPage-5:1 }" end="${pb.currentPage+4>pb.totalPage?pb.totalPage:pb.currentPage+4 }" var="n">
					<!-- 是否是当前页 -->
					<c:if test="${pb.currentPage==n }">
						<li class="active"><a href="javascript:void(0)">${n }</a></li>
					</c:if>
					<c:if test="${pb.currentPage!=n }">
						<li><a href="${pageContext.request.contextPath}/adminProduct?method=findAll&currentPage=${n}">${n }</a></li>
					</c:if>
				</c:forEach>
				<!-- 判断是否是最后一页 -->
				<c:if test="${pb.currentPage==pb.totalPage}">
					<li class="disabled">
						<a href="javascript:void(0)" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
				<c:if test="${pb.currentPage!=pb.totalPage}">
					<li>
						<a href="${pageContext.request.contextPath}/adminProduct?method=findAll&currentPage=${pb.currentPage+1}" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
			</ul>
		</div>
		<!-- 分页结束=======================        -->
		
		
	</body>
</HTML>

