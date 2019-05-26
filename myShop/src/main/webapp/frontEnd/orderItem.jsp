<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="${pageContext.request.contextPath }/">
		<link rel="stylesheet" href="css/orderItem.css" type="text/css"/>
		<link rel="stylesheet" href="css/web_foot.css" type="text/css" />
		<link rel="stylesheet" href="css/web_headNavigation.css" type="text/css" />
		<link rel="stylesheet" href="css/web_searchBox.css" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="js/orderList.js"></script>
		<title>订单项页</title>
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
					<div id="shopInformation">商品信息</div>
					<div id="danjia">单价</div>
					<div id="shuliang">数量</div>
					<div id="jine">金额</div>
				</div>
				<!--
                	描述：商品信息部分
                -->
                <c:forEach var="item" items="${oilInfo}">
                	<div class="order_info_body">
						<!-- 描述：商品图片和描述 -->
						<div class="shopInformationbody">
							<div class="shopInformationbody1">
								<img class="shopInformationbodyImg" src="${item.product.image}">
							</div>
							<div class="shopInformationbody2">
								${item.product.description}
							</div>
						</div>
						<!-- 描述：商品价格 -->
						<div class="danjiabody"> <!--描述：单价-->
							￥<span id="perPrice35">${item.product.shopPrice}</span>
						</div>
						<!-- 描述：商品数量 -->
						<div class="shuliangbody"><!--描述：数量  onclick中放置的是商品id-->
							<input id="number${item.product.id}" class="shuliangbody_num" type="text" value="${item.count}" readonly="readonly" />
						</div>
						<!-- 描述：商品总价 -->
						<div class="jinebody"><!--描述：总价-->
							￥<span id="allPrice35">${item.subtotal}</span>
						</div>
					</div>
                </c:forEach>
				<!--
                	描述：商品总计部分
                -->
                 <!--
                	作者：midy
                	时间：2019-03-05
                	描述：订单信息部分
                -->

                <div id="orderInfo_div">
                	<div class="orderInfo_orderNO_div">
                		<div class="nameBlock">下单时间 :</div>
                		<div class="valueBlock"><fmt:formatDate value="${orderInfo.ordertime}" pattern="yyyy年MM月dd日HH点mm分" /><!--描述：日期--></div>
                	</div>
                	<div class="orderInfo_orderNO_div">
                		<div class="nameBlock">手机号 :</div>
                		<div class="valueBlock">${orderInfo.phone}</div>
                	</div>
                	<div class="orderInfo_orderNO_div">
                		<div class="nameBlock">收货人 :</div>
                		<div class="valueBlock">${orderInfo.name}</div>
                	</div>
                	<div class="orderInfo_orderNO_div">
                		<div class="nameBlock">订单号 :</div>
                		<div class="valueBlock">${orderInfo.id}</div>
                	</div>
                	<div class="orderInfo_orderNO_div">
                		<div class="nameBlock">地址 :</div>
                		<div class="valueBlock">${orderInfo.addr}</div>
                	</div>
                	<div class="orderInfo_orderNO_div">
                		<div class="nameBlock">合计:</div>
                		<div class="valueBlock"><span id="totalPrice">￥${orderInfo.total }</span></div>
                	</div>
                </div>
				<div id="order_total">
					<c:if test="${orderInfo.state !=null}">
						<c:if test="${orderInfo.state == '1'}">
							<div id="order_total_num" class="orderStateChangeDiv" onclick="changeOrdSta('${orderInfo.id }')">确认付款</div>
						</c:if>
						<c:if test="${ orderInfo.state == '3'}">
							<div id="order_total_num" class="orderStateChangeDiv" onclick="changeOrdSta('${orderInfo.id }')">确认收货</div>
						</c:if>
					</c:if>
				</div>
			</div>
			
			<!--描述：订单详情部分结束-->
			<!--描述：尾部DIV开始-->
			<jsp:include page="web_foot.jsp"></jsp:include>
			<!--描述：尾部DIV结束-->
		</div>
	</body>
</html>
