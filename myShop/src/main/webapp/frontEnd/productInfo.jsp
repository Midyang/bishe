<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="${pageContext.request.contextPath }/">
		<link rel="stylesheet" href="css/productInfo.css" type="text/css"/>
		<link rel="stylesheet" href="css/web_foot.css" type="text/css" />
		<link rel="stylesheet" href="css/web_headNavigation.css" type="text/css" />
		<link rel="stylesheet" href="css/web_searchBox.css" type="text/css" />
		<script type="text/javascript" >
			function fomSubmit(location){
				if("add"==location){
					document.getElementById("addProductForm").action="userShop/addToShopCar";
					document.getElementById("addProductForm").submit();
				}else if("order"==location){
					document.getElementById("addProductForm").action="userShop/formSubmitBefore";
					document.getElementById("shop_info_infoDiv_num2_value").value=document.getElementById("shop_info_infoDiv_num_value").value;
					document.getElementById("addProductForm").submit();
				}
				
			}
		</script>
		<title>商品详情页</title>
	</head>
	<body>
		<div id="main_div">
			<!--描述：头部DIV开始-->
			<jsp:include page="web_headNavigation.jsp"></jsp:include>
			<!--描述：头部DIV结束-->
			<!--描述：搜索框DIV开始-->
			<jsp:include page="web_searchBox.jsp"></jsp:include>
			<!--描述：搜索框DIV结束-->
			<form action="userShop/addToShopCar" method="post" id="addProductForm">
				<!--描述：商品详情部分-->
				<div id="shop_info">
					<div id="shop_info_photoDiv">
						<div id="shop_info_photoDiv_photo">
							<img src="${productInfo.image }" />
						</div>
						<div id="shop_info_photoDiv_photoDisc">
							${productInfo.description }
						</div>
					</div>
					<div id="shop_info_infoDiv">
						<div id="shop_info_infoDiv_name">
							${productInfo.name }
						</div>
						<div id="shop_info_infoDiv_price">
							<div id="shop_info_infoDiv_price1">
								市场价格：<s>${productInfo.marketPrice }</s>￥
							</div>
							<div id="shop_info_infoDiv_price2">
								商城价格：<span>${productInfo.shopPrice }</span>￥
							</div>
						</div>
						<div id="shop_info_infoDiv_num">
							<div id="shop_info_infoDiv_num_tip">
								数量&nbsp;
								<input type="number"  name="count" id="shop_info_infoDiv_num_value" min="1" value="1"/>
								<input type="hidden"  name="productId" value="${productInfo.id }"/>
								<input type="hidden"  name="number" value="1" id="shop_info_infoDiv_num2_value"/>
								<input type="hidden"  name="statu" value="1"/>
							</div>
						</div>
						<div id="shop_info_infoDiv_button">
							<div id="shop_info_infoDiv_baoliu">
							</div>
							<div id="shop_info_infoDiv_buttonAll">
								<div id="shop_info_infoDiv_button1" onclick="fomSubmit('order')">
								</div>
								<div id="shop_info_infoDiv_button2" onclick="fomSubmit('add')">
							
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--描述：商品详情部分结束-->
			</form>
			<!--描述：尾部DIV开始-->
			<jsp:include page="web_foot.jsp"></jsp:include>
			<!--描述：尾部DIV结束-->
		</div>
	</body>
</html>
