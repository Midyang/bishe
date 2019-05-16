<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.contextPath }/"/>
<link rel="stylesheet" href="css/classOne_Add_Update.css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	function returnPage(){
		location.href="category/list";
	};
	function verify(){
		var name=document.getElementById("text_input").value.trim();
		if(!/^.{1,20}$/.test(name)){
			$("#err_name").html("*必选字段! 请输入不能超过20个有意义字符");
			$("#err_name").css("color","red");
			return false;
		}else{
			$("#err_name").html("有效");
			$("#err_username").css("color","green");
			return true;
		}
	};
</script>
<title>添加一级分类</title>
</head>
<body>
		<!-- 开始 -->
		<div id="start">
			<!-- 标题  -->
			<div id="head">
				<!-- 标题**导航部分  -->
				<div id="head_text">
				添加一级分类  &lt; <a href="category/list">&nbsp;一级分类列表</a> &lt; <a href="back_page/hello.jsp">&nbsp;首页</a>
				</div>
				<!-- 退出登陆部分  -->
			</div>
			<!-- 表格-->
			<div id="main">
					<!-- 要提交的表单-->
					<form action="category/add" method="post" onsubmit="return verify()">
						<!-- 表格-->
						<table id="main_table" border="0" cellpadding="0" cellspacing="0">
							<!--第一行-->
							<tr id="table_tr">
								<!--添加一级分类-->
								<td  id="title_tr_td">一级分类名称:</td>
								<td >
									<input type="text" name="name"  value="${message.name }" id="text_input"/>
								</td>
							</tr>
							<tr id="err_row">
								<td colspan="2">
									<font color="red" id="err_name">
										${message.msg }
									</font>
								</td>
							</tr>
							<!--第二行 功能按钮-->
							<tr>
								<td colspan="2" >
									<div id="func_button_div">
										<input type="reset" value="重置" class="func_button"/>
										<input type="submit" value="提交" class="func_button"/>
										<input type="button" onclick="returnPage()" value="返回" class="func_button"/>
									</div>
								</td>
							</tr>
						</table>
					</form>
			<!-- 表格div-->
			</div>
		<!--主体div -->
		</div>
	</body>
</html>