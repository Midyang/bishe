<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="${pageContext.request.contextPath }/">
		<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		<link rel="stylesheet" href="css/searchProductPage.css" />
		<script type="text/javascript">
		</script>
		<title>详情搜索商品信息</title>
	</head>
	<body>
		<!-- 开始 -->
		<div id="start">
			<!-- 标题  -->
			<div id="head">
				<!-- 标题**导航部分  -->
				<div id="head_text">
				详细搜索  &lt; <a href="product/list"> 商品列表 </a>  &lt; <a href="back_page/hello.jsp">&nbsp;首页</a>
				</div>
				<!-- 退出登陆部分  -->
			</div>
			<!-- 表格-->
			<div id="main">
				<!-- 要提交的表单-->
				<form action="product/searchProduct" method="post">
					<div id="dataDiv">
						<div id="searchCondiTionDiv">
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								商品名称：
								</div>
								<div class="partSearchDivValue">
									<input value="${parameter.name}" type="text" name="name" class="partSearchDivValueInpt"/>
								</div>
							</div>
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								商品描述信息：
								</div>
								<div class="partSearchDivValue">
									<input value="${parameter.description}" type="text" name="description" class="partSearchDivValueInpt"/>
								</div>
							</div>
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								价格区间：
								</div>
								<div class="partSearchDivValue">
									<input type="number" name="minPrice" min="0" value="${parameter.minPrice}" class="partSearchDivValueInptPrice" placeholder="0.0">&nbsp;至&nbsp;
									<input type="number" name="maxPrice" min="0" value="${parameter.maxPrice}" class="partSearchDivValueInptPrice" placeholder="1000000.0">
								</div>
							</div>
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								是否热门：
								</div>
								<div class="partSearchDivValue">
									<input type="radio" value="2" name="isHot" ${parameter.isHot==null || parameter.isHot=="" || parameter.isHot=='2'?"checked='checked'":""}>不限制&nbsp;
									<input type="radio" value="1" name="isHot" ${parameter.isHot=='1'?"checked='checked'":""}>热门&nbsp;
									<input type="radio" value="0" name="isHot" ${parameter.isHot=='0'?"checked='checked'":""}>不热门
								</div>
							</div>
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								价格升序/降序：
								</div>
								<div class="partSearchDivValue">
									<input type="radio" value="2" name="priceSort" ${(parameter.priceSort==null || parameter.priceSort=='2' || parameter.priceSort=='')?"checked='checked'":""}>不限制&nbsp;
									<input type="radio" value="1" name="priceSort" ${parameter.priceSort=='1'?"checked='checked'":""}>升序&nbsp;
									<input type="radio" value="0" name="priceSort" ${parameter.priceSort=='0'?"checked='checked'":""}>降序
								</div>
							</div>
							<div class="partSearchDiv">
								<div class="partSearchDivName">
								所属分类：
								</div>
								<div class="partSearchDivValue">
									<select name="scid" class="partSearchDivValueSel" id="partSearchDivValueSelId">
										<option value="0" class="partSearchDivValueSelOp" ${(parameter.scid==null || parameter.scid=='0' || parameter.scid=="")?"selected='selected'":""}>不限制</option>
										<c:forEach items="${twoClass}" var="item">
											<option value="${item.id}" class="partSearchDivValueSelOp" ${parameter.scid==item.id?"selected='selected'":""}>${item.name}</option>
										</c:forEach>
									</select>
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