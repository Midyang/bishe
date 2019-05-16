<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="${pageContext.request.contextPath }/">
		<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		<link rel="stylesheet" href="css/searchOrderPage.css" />
		<script type="text/javascript">
		</script>
		<title>详情搜索订单信息</title>
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
				<form action="orders/searchOrder" method="post" enctype="multipart/form-data" onsubmit="return verify()">

					<div id="dataDiv">
						<div id="searchCondiTionDiv">
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								订单号：
								</div>
								<div class="partSearchDivValue">
									<input type="text" name="id" value="${parameter.id }" class="partSearchDivValueInpt"/>
								</div>
							</div>
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								用户姓名：
								</div>
								<div class="partSearchDivValue">
									<input type="text" name="name" value="${parameter.name }" class="partSearchDivValueInpt"/>
								</div>
							</div>
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								手机号：
								</div>
								<div class="partSearchDivValue">
									<input type="text" name="phone" value="${parameter.phone }" class="partSearchDivValueInpt"/>
								</div>
							</div>
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								价格区间：
								</div>
								<div class="partSearchDivValue">
									<input type="number" name="minPrice" value="${parameter.minPrice }" min="0" value="${data.minPrice}" class="partSearchDivValueInptPrice" placeholder="0.0">&nbsp;至&nbsp;
									<input type="number" name="maxPrice" value="${parameter.maxPrice }" min="0" value="${data.maxPrice}" class="partSearchDivValueInptPrice" placeholder="1000000.0">
								</div>
							</div>
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								订单状态：
								</div>
								<div class="partSearchDivValue">
									<input type="radio" name="state" value="0" ${(parameter.state=='' or parameter.state==null or parameter.state=='0')?"checked='checked'":""}/>不限制
									<input type="radio" name="state" value="1" ${parameter.state=='1'?"checked='checked'":""}/>未支付
									<input type="radio" name="state" value="2" ${parameter.state=='2'?"checked='checked'":""}>未发货 
									<input type="radio" name="state" value="3" ${parameter.state=='3'?"checked='checked'":""}>已发货 
									<input type="radio" name="state" value="4" ${parameter.state=='4'?"checked='checked'":""}>已收货
									<input type="radio" name="state" value="5" ${parameter.state=='5'?"checked='checked'":""}>已完成
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