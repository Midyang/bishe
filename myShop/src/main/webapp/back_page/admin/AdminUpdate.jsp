<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.contextPath }/">
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<link rel="stylesheet" href="css/Admin_Log_update_add.css" />
<script type="text/javascript">
	function verification(){/* 数据验证  */
		var vals=true;

		var username=$("#username").val().trim();
		if(!/^[a-zA-Z][a-zA-Z0-9]{4,12}$/.test(username)){
			$("#err_username").html("*必选字段 请输入4到12位字母或数字!");
			$("#err_username").css("color","red");
			vals=false;
		}else{
			$("#err_username").html("*有效");
			$("#err_username").css("color","green");
		}
		

		var password=$("#pwd").val();
		var comfirmWord=$("#comfirmWord").val();
		if(!/^[a-zA-Z0-9]{4,12}$/.test(password)){
			$("#err_password").html("*必选字段 请输入4到12位数字或字母!");
			$("#err_password").css("color","red");
			vals=false;
			return false;
		}else{
			$("#err_password").html("*有效");
			$("#err_password").css("color","green");
			if(password != comfirmWord){//两次密码不相等
				$("#err_comfirmWord").html("*两次密码不一致!");
				$("#err_password").html("*两次密码不一致!");
				$("#err_comfirmWord").css("color","red");
				$("#err_password").css("color","red");
				vals=false;
			}else{
				$("#err_comfirmWord").html("*有效");
				$("#err_comfirmWord").css("color","green");
				$("#err_password").html("*有效");
				$("#err_password").css("color","green");
			}
		}
		return vals;
	};
	
</script>
<title>更改管理员信息</title>
</head>
<body>
		<!-- 标题  -->
	<div id="head">
				<!-- 标题**导航部分  -->
				<div id="head_text">
				编辑  &lt; <a href="AdminServlet?m=getList"> 管理员列表 </a> &lt; <a href="back_page/hello.jsp"> 首页</a>
				</div>
	</div>
	<div id="main_update">
			<div id="loginDIV">
				<form id="loginForm" action="/admin/update" method="post" onsubmit="return verification()">
					<table border="0" cellspacing="0" cellpadding="0" id="tal">
						<tr class="input_info">
							<th>管理员账号:</th>
							<td>
								<input type="hidden" name="id" value="${admin.id}"/>
								<input type="hidden" name="oldUsername" value="${admin.username}"/>
								<input type="hidden" name="oldPassword" value="${admin.password}"/>
								<input type="text" name="username" id="username" value="${admin.username }"/>
							</td>
						</tr>
						<tr class="err_row">
							<td colspan="2">
								<font id="err_username" class="err">
								${nameMsg }
								</font>
							</td>
						</tr>
						<tr class="input_info">
							<th>管理员密码:</th>
							<td>
								<input type="password" name="password" id="pwd" value="${admin.password }" />
							</td>
						</tr>
						<tr class="err_row">
							<td colspan="2">
								<font id="err_password" class="err">
								${pwdMsg }
								</font>
							</td>
						</tr>
						<tr class="input_info">
							<th>确认密码:</th>
							<td>
								<input type="password" name="comfirmWord" id="comfirmWord" value="" />
							</td>
						</tr>
						<tr class="err_row">
							<td colspan="2">
								<font id="err_comfirmWord" class="err">
									${comfirmMsg }
								</font>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div id="fenye_div">
									<input type="reset"  value="重置" class="fenye_button"/>
								<input type="submit" value="确定" class="fenye_button"/>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
	</div>
</body>
</html>