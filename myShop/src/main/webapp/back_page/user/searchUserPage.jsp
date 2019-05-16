<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="${pageContext.request.contextPath }/">
		<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		<link rel="stylesheet" href="css/searchUserPage.css" />
		<script type="text/javascript">
		</script>
		<title>详情搜索用户信息</title>
	</head>
	<body>
		<!-- 开始 -->
		<div id="start">
			<!-- 标题  -->
			<div id="head">
				<!-- 标题**导航部分  -->
				<div id="head_text">
				详细搜索  &lt; <a href="product/list"> 管理员列表 </a>  &lt; <a href="back_page/hello.jsp">&nbsp;首页</a>
				</div>
				<!-- 退出登陆部分  -->
			</div>
			<!-- 表格-->
			<div id="main">
				<!-- 要提交的表单-->
				<form action="user/searchUser" method="post" enctype="multipart/form-data" onsubmit="return verify()">

					<div id="dataDiv">
						<div id="searchCondiTionDiv">
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								账号：
								</div>
								<div class="partSearchDivValue">
									<input type="text" name="username" value="${parameter.username}" class="partSearchDivValueInpt"/>
								</div>
							</div>
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								姓名：
								</div>
								<div class="partSearchDivValue">
									<input type="text" name="name" value="${parameter.name}" class="partSearchDivValueInpt"/>
								</div>
							</div>
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								手机号：
								</div>
								<div class="partSearchDivValue">
									<input type="text" name="phone" value="${parameter.phone}" class="partSearchDivValueInpt"/>
								</div>
							</div>
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								email：
								</div>
								<div class="partSearchDivValue">
									<input type="text" name="email" value="${parameter.email}" class="partSearchDivValueInpt"/>
								</div>
							</div>
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								地址：
								</div>
								<div class="partSearchDivValue">
									<input type="text" name="addr" value="${parameter.addr}" class="partSearchDivValueInpt"/>
								</div>
							</div>
						</div>
						<div id="func_button_div">
							<input type="reset" value="重置" class="func_button"/>
							<input type="submit" value="查询" class="func_button"/>
						</div>
					</div>
					<input value="1" type="hidden" name="fromLocation" />
					<!--第六行 功能按钮-->
				</form>
			<!-- 表格div-->
			</div>
		<!--主体div -->
		</div>
	</body>
</html>