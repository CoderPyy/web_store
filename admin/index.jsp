<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>网上商城管理中心</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath }/css/general.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath }/css/main.css" rel="stylesheet" type="text/css" />

<style type="text/css">
body {
  color: white;
}
</style>
<script type="text/javascript">
	function checkCode(obj){
		obj.src="${pageContext.request.contextPath}/code?i="+Math.random();
	}
</script>
</head>
<body style="background: #278296;color:white">
<form action="${pageContext.request.contextPath }/adminUser?method=login" method="post">
    <table style="margin-top:100px" align="center">
        <tr>
            <td style="padding-left: 50px">
					
						<table>
							<tr>
								<td>管理员姓名：</td>
								<td colspan="2"><input type="text" name="username" /></td>
							</tr>
							<tr>
								<td>管理员密码：</td>
								<td colspan="2"><input type="password" name="password" />
								</td>
							</tr>
							<tr>
								<td>验证码：</td>
								<td><input type="text" name="checkcode" class="capital" /></td>
								<td colspan="2" align="right"><img
									src="${pageContext.request.contextPath }/code"
									onclick="checkCode(this)" title="看不清楚，点击换一张" alt="此处是图片" /></td>
							</tr>
							<tr>
								<td><input type="submit" value="进入管理中心" class="button" />
								</td>
								<td colspan="2"><span><font color="red">${msg }</font></span></td>
							</tr>
						</table>

				</td>
        </tr>
    </table>
</form>
</body>