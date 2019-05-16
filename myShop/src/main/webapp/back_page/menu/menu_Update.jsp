<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.contextPath }/"/>
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<link rel="stylesheet" href="css/menu.css" />
<script type="text/javascript">
	function verify(){
		var menuId=document.getElementById("menuId_input").value.trim();
		var menuName=document.getElementById("menuName_input").value.trim();
		var menuURL=document.getElementById("menuURL_input").value.trim();
		var parentMenuId=document.getElementById("parentMenuId_input").value.trim();
		var flag=true;
		if(!/^.{1,20}$/.test(menuId)){
			$("#err_menuId").html("*必选字段! 请输入不能超过20个有意义字符");
			$("#err_menuId").css("color","red");
			flag = false;
		}
		
		if(!/^.{1,20}$/.test(menuName)){
			$("#err_menuName").html("*必选字段! 请输入不能超过20个有意义字符");
			$("#err_menuName").css("color","red");
			flag = false;
		}
		
		if(!/^.{1,20}$/.test(menuURL)){
			$("#err_menuURL").html("*必选字段! 请输入不能超过100个有意义字符");
			$("#err_menuURL").css("color","red");
			flag = false;
		}
		
		if(parentMenuId==null || parentMenuId=="" || parentMenuId=="undefined"){
			$("#err_parentMenuId").html("*必选");
			$("#err_parentMenuId").css("color","red");
			flag = false;
		}
		if(flag){
		}
		return true;
	};
	function returnPage(){
		location.href="menu/list";
	};
</script>
<title>编辑列表</title>
</head>
<body>
		<!-- 开始 -->
		<div id="start">
			<!-- 标题  -->
			<div id="head">
				<!-- 标题**导航部分  -->
				<div id="head_text">
				编辑目录项  &lt; <a href="menu/list">&nbsp;目录列表</a> &lt; <a href="back_page/hello.jsp">&nbsp;首页</a>
				</div>
				<!-- 退出登陆部分  -->
			</div>
			<!-- 表格-->
			<div id="main">
					<!-- 要提交的表单-->
					<form action="menu/update" method="post" onsubmit="return verify()">
						<!-- 表格-->
						<table id="main_table" border="0" cellpadding="0" cellspacing="0">
							<!--第一行-->
							<tr class="table_tr">
								<td  class="title_tr_td">目录ID:</td>
								<td >
									<input type="hidden" name="id"  value="${resMenu.entity.id}" />
									<input type="text" name="menuId"  value="${resMenu.entity.menuId}" id="menuId_input" class="text_input"/>
								</td>
							</tr>
							<tr class="err_row">
								<td colspan="2">
									<font color="red" id="err_menuId">
										<c:if test="${resMenu.code=='menuId'}">
											${resMenu.msg}
										</c:if>
									</font>
								</td>
							</tr>
							<tr class="table_tr">
								<td  class="title_tr_td">目录名称:</td>
								<td >
									<input type="text" name="menuName"  value="${resMenu.entity.menuName}" id="menuName_input" class="text_input"/>
								</td>
							</tr>
							<tr class="err_row">
								<td colspan="2">
									<font color="red" id="err_menuName">
										<c:if test="${resMenu.code=='menuName'}">
											${resMenu.msg}
										</c:if>
									</font>
								</td>
							</tr>
							<tr class="table_tr">
								<td  class="title_tr_td">目录URL值:</td>
								<td >
									<input type="text" name="menuURL"  value="${resMenu.entity.menuURL }" id="menuURL_input" class="text_input"/>
								</td>
							</tr>
							<tr class="err_row">
								<td colspan="2">
									<font color="red" id="err_menuURL">
										<c:if test="${resMenu.code=='menuURL'}">
											${resMenu.msg}
										</c:if>
									</font>
								</td>
							</tr>
							<tr class="table_tr">
								<td  class="title_tr_td">父目录:</td>
								<td >
									<select name="parentMenuId" id="parentMenuId_input" >
										<c:forEach var="perMenu" items="${parentMenu}">
											<option value="${perMenu.menuId}" ${perMenu.menuId==resMenu.entity.parentMenuId?"selected='selected'":"" }>${perMenu.menuName }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr class="err_row">
								<td colspan="2">
									<font color="red" id="err_parentMenuId">
										<c:if test="${resMenu.code=='parentMenuId'}">
											${resMenu.msg}
										</c:if>
									</font>
								</td>
							</tr>
							<!--第二行 功能按钮-->
							<tr>
								<td colspan="2" >
									<div id="func_button_div">
										<input type="reset" value="重置" class="func_button" />
										<input type="submit" value="提交" class="func_button" />
										<input type="button" onclick="returnPage();" value="返回" class="func_button"/>
									</div>
								</td>
							</tr>
						</table>
					</form>
			<!-- 表格div-->
			</div>
		<!--主体div -->
		</div>
		<c:if test="${resMenu.code=='errUpdateMenu'}"><!--主要用于弹窗的提示信息 -->
			${resMenu.msg}
		</c:if>
		${exit}<!--退出的登录 -->
	</body>
</html>