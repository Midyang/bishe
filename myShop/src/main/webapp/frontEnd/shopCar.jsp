<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="${pageContext.request.contextPath }/">
		<link rel="stylesheet" href="css/shopCar.css" type="text/css"/>
		<link rel="stylesheet" href="css/web_foot.css" type="text/css" />
		<link rel="stylesheet" href="css/web_headNavigation.css" type="text/css" />
		<link rel="stylesheet" href="css/web_searchBox.css" type="text/css" />
		<script type="text/javascript" src="js/shopCar.js"></script>
		<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		<title>购物车详情页</title>
	</head>
	<body>
		<div id="main_div">
			<!--描述：头部DIV开始-->
			<jsp:include page="web_headNavigation.jsp"></jsp:include>
			<!--描述：头部DIV结束-->
			<!--描述：搜索框DIV开始-->
			<jsp:include page="web_searchBox.jsp"></jsp:include>
			<!--描述：搜索框DIV结束-->
			<!--描述：订单详情部分-->
			<div id="order_info">
				<div id="order_info_head">
					<div id="quanxuan"><input type="checkbox" onclick="quanxuan(this)" id="allCheckedOne"/>全选</div>
					<div id="shopInformation">商品信息</div>
					<div id="danjia">单价</div>
					<div id="shuliang">数量</div>
					<div id="jine">金额</div>
					<div id="caozuo">操作</div>
				</div>
				<!--<form action="" method="post">-->
				<!--
                	描述：商品信息部分
                -->
                	<c:forEach items="${orderItem }" var="item">
	                	<div class="order_info_body">
							<!-- 描述：选择框 -->
							<div class="quanxuanbody">
								<input type="checkbox" checked="checked" class="order_info_body_select" id="${item.productId}" onclick="totalPrice()"/>选择 <!--作者：Midy 时间：2019-01-28 描述：此处ID 设定为商品ID，方便其它操作 -->
							</div>
							<!-- 描述：商品图片和描述 -->
							<div class="shopInformationbody">
								<div class="shopInformationbody1">
									<img class="shopInformationbodyImg" src="${item.product.image }">
								</div>
								<div class="shopInformationbody2">
									${item.product.description}
								</div>
							</div>
							<!-- 描述：商品价格 -->
							<div class="danjiabody"> <!--描述：单价-->
								￥<span id="perPrice${item.productId}">${item.product.shopPrice}</span>
							</div>
							<!-- 描述：商品数量 -->
							<div class="shuliangbody" ><!--描述：数量  onclick中放置的是商品id-->
								<input id="number${item.productId}" value="${item.count}" class="shuliangbody_num" type="number" min="1" onmouseout="calculatePrice(${item.productId})"/>
								<input id="numberChange${item.productId}" type="hidden" value="${item.count}" />
							</div>
							<!-- 描述：商品小计 -->
							<div class="jinebody"><!--描述：小计-->
								￥<span id="allPrice${item.productId}">${item.subtotal}</span>
							</div>
							<!-- 描述：商品删除按钮 -->
							<div class="caozuobody">
								<button id="delectShopButton" onclick="deleteItemById(${item.productId})">删除</button>
							</div>
						</div>
                	</c:forEach>
                	<c:if test="${orderItem == null }">
                		<div id="notHaveShop">空空如也...</div>
                	</c:if>
				<!--</form>-->
				<!--
                	描述：商品总计部分
                -->
				<div id="order_total">
					<div id="order_total_quanxuan"><input type="checkbox" />全选</div>
					<div id="order_total_num">已选商品<span id="selectProductNum">0</span>件</div>
					<div id="order_total_money">合计：<span id="totalPrice">￥${order.total}</span></div>
					<div id="order_total_manyDelete" onclick="deleteItemManyByIdForm()">批量删除</div>
					<div id="order_total_tijiao" onclick="formSubmit()">提交</div>
				</div>
			</div>
			<!--描述：订单详情部分结束-->
			<!--描述：尾部DIV开始-->
			<jsp:include page="web_foot.jsp"></jsp:include>
			<!--描述：尾部DIV结束-->
			<!--描述：删除表单-->
			<form action="userShop/addToShopCar" method="post" id="deleteItemByIdForm">
				<input type="hidden" name="productId" value="0" id="productIdValue"/>
				<input type="hidden" name="statu" value="2" id="statuValue"/>
				<input type="hidden" name="count" value="0" id="countValue"/>
			</form>
			<form action="userShop/addToShopCar" method="post" id="deleteItemManyByIdForm">
				<input type="hidden" name="productId" value="0" id="productIdValueTwo"/>
				<input type="hidden" name="statu" value="3" id="statuValueTwo"/>
				<input type="hidden" name="count" value="0" id="countValueTwo"/>
			</form>
			<form action="userShop/formSubmitBefore" method="post" id="formSubmitBeforeForm">
				<input type="hidden" name="productId" value="0" id="productIdVArray"/>
			</form>
		</div>
	</body>
</html>
