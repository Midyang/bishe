<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="${pageContext.request.contextPath }/">
		<link rel="stylesheet" href="css/orderList.css" type="text/css"/>
		<link rel="stylesheet" href="css/web_foot.css" type="text/css" />
		<link rel="stylesheet" href="css/web_headNavigation.css" type="text/css" />
		<link rel="stylesheet" href="css/web_searchBox.css" type="text/css" />
		<script type="text/javascript" src="js/orderList.js"></script>
		<title>订单页</title>
	</head>
	<body onload="changeLocaltion('${changeLocaltion}')">
		<div id="main_div">
			<!--描述：头部DIV开始-->
			<jsp:include page="web_headNavigation.jsp"></jsp:include>
			<!--描述：头部DIV结束-->
			<!--描述：搜索框DIV开始-->
			<jsp:include page="web_searchBox.jsp"></jsp:include>
			<!--描述：搜索框DIV结束-->
			<!--描述：订单详情部分-->
			<div id="order_info">
				<div id="leftInfo">
					<div id="orderItems">选择</div>
					<div id="unpaidOrder" onclick="divVisibility('unpaidOrder_div')">未付款订单</div>
					<div id="receiveOfgoodsOrder" onclick="divVisibility('receiveOfgoodsOrder_div')">未收货订单</div>
					<div id="finishOrder" onclick="divVisibility('finishOrder_div')">已完成订单</div>
				</div>
				<div id="rightInfo">
					<div id="rightInfo_order_head">
						<div id="order_product">订单号</div><!--描述：商品描述-->
						<div id="order_date">日期</div><!--描述：日期-->
						<div id="order_phone">手机号</div><!--描述：手机号-->
						<div id="order_addr">收货地址</div><!--描述：收货地址-->
						<div id="order_total">总计</div><!--描述：收货地址-->
						<div id="order_state">订单状态</div><!--描述：收货地址-->
					</div>
					<div id="rightInfo_order_body">
						<div id="unpaidOrder_div"><!--描述：未支付订单-->
							<!--
	                                                    作者：Midy
	                                                    时间：2019-01-29
	                      	    描述：一个订单项
	                        -->
							<div class="unpaidOrder_divTop">
	                            <c:if test="${unpaid != null }">
	                            	<c:forEach  items="${unpaid }" var="order">
	                            		<div class="unpaidOrder_div_orderItem" onclick="orderItemInfo('${order.id }')">
											<div class="orderItem_product">${order.id }</div><!--描述：商品描述-->
											<div class="orderItem_date"><fmt:formatDate value="${order.ordertime }" pattern="yyyy年MM月dd日HH点mm分" /></div><!--描述：日期-->
											<div class="orderItem_phone">${order.phone }</div><!--描述：手机号-->
											<div class="orderItem_addr">${order.addr }</div><!--描述：收货地址-->
											<div class="orderItem_total">￥${order.total }</div><!--描述：收货地址-->
											<div class="orderItem_state">${order.state_name }</div><!--描述：收货地址-->
										</div>
	                            	</c:forEach>
	                            </c:if>
							</div>
							<!--
                            	作者：midy
                            	时间：2019-01-29
                            	描述：分页部分
                            -->
							<div class="fenyeDiv">
								<div class="currentPage">
									${pageOne.currPage }/${pageOne.countPage}
								</div>
								<div class="baoliu"></div>
								<div class="firstPage" onclick="refreshPage('currentPageOne',1,'unpaidOrder_div')">首页</div>
								<div class="previousPage" onclick="refreshPage('currentPageOne',${pageOne.prePage},'unpaidOrder_div')">上一页</div>
								<div class="fixPage">跳到<input type="number" class="fixPageInput" min="1" onblur="refreshPage2('currentPageOne',this,'unpaidOrder_div')"/>页 </div>
								<div class="nextPage" onclick="refreshPage('currentPageOne',${pageOne.nextPage},'unpaidOrder_div')">下一页</div>
								<div class="lastPage" onclick="refreshPage('currentPageOne',${pageOne.countPage},'unpaidOrder_div')">尾页</div>
							</div>
						</div>
						<div id="receiveOfgoodsOrder_div"><!--描述：未完成订单-->
							<div class="unpaidOrder_divTop">
	                             <c:if test="${openOrder != null }">
	                            	<c:forEach  items="${openOrder }" var="order">
	                            		<div class="unpaidOrder_div_orderItem" onclick="orderItemInfo('${order.id }')">
											<div class="orderItem_product">${order.id }</div><!--描述：商品描述-->
											<div class="orderItem_date"><fmt:formatDate value="${order.ordertime }" pattern="yyyy年MM月dd日HH点mm分" /></div><!--描述：日期-->
											<div class="orderItem_phone">${order.phone }</div><!--描述：手机号-->
											<div class="orderItem_addr">${order.addr }</div><!--描述：收货地址-->
											<div class="orderItem_total">￥${order.total }</div><!--描述：收货地址-->
											<div class="orderItem_state"><div id="${order.id }" class="orderStateChangeDiv" onclick="changeOrdSta('${order.id }')">${order.state_name }</div></div><!--描述：收货地址-->
										</div>
	                            	</c:forEach>
	                            </c:if>
							</div>
                            <div class="fenyeDiv">
								<div class="currentPage">
									${pageTwo.currPage }/${pageTwo.countPage}
								</div>
								<div class="baoliu"></div>
								<div class="firstPage" onclick="refreshPage('currentPageTwo',1,'receiveOfgoodsOrder_div')">首页</div>
								<div class="previousPage" onclick="refreshPage('currentPageTwo',${pageTwo.prePage},'receiveOfgoodsOrder_div')">上一页</div>
								<div class="fixPage">跳到<input type="number" class="fixPageInput" min="1" onblur="refreshPage2('currentPageTwo',this,'receiveOfgoodsOrder_div')" />页 </div>
								<div class="nextPage" onclick="refreshPage('currentPageTwo',${pageTwo.nextPage},'receiveOfgoodsOrder_div')">下一页</div>
								<div class="lastPage" onclick="refreshPage('currentPageTwo',${pageTwo.countPage},'receiveOfgoodsOrder_div')">尾页</div>
							</div>
						</div>
						<div id="finishOrder_div"><!--描述：已完成订单完成订单-->
							<div class="unpaidOrder_divTop">
								 <c:if test="${receivedOrder != null }">
	                            	<c:forEach  items="${receivedOrder }" var="order">
	                            		<div class="unpaidOrder_div_orderItem" onclick="orderItemInfo('${order.id }')">
											<div class="orderItem_product">${order.id }</div><!--描述：商品描述-->
											<div class="orderItem_date"><fmt:formatDate value="${order.ordertime }" pattern="yyyy年MM月dd日HH点mm分" /></div><!--描述：日期-->
											<div class="orderItem_phone">${order.phone }</div><!--描述：手机号-->
											<div class="orderItem_addr">${order.addr }</div><!--描述：收货地址-->
											<div class="orderItem_total">￥${order.total }</div><!--描述：收货地址-->
											<div class="orderItem_state">${order.state_name }</div><!--描述：收货地址-->
										</div>
	                            	</c:forEach>
	                            </c:if>
							</div>
                            <div class="fenyeDiv3">
								<div class="currentPage3">
									${pageThr.currPage }/${pageThr.countPage}
								</div>
								<div class="baoliu"></div>
								<div class="firstPage" onclick="refreshPage('currentPageThr',1,'finishOrder_div')">首页</div>
								<div class="previousPage" onclick="refreshPage('currentPageThr',${pageThr.prePage},'finishOrder_div')">上一页</div>
								<div class="fixPage">跳到<input type="number" class="fixPageInput" min="1" onblur="refreshPage2('currentPageThr',this,'finishOrder_div')" />页 </div>
								<div class="nextPage" onclick="refreshPage('currentPageThr',${pageThr.nextPage},'finishOrder_div')">下一页</div>
								<div class="lastPage" onclick="refreshPage('currentPageThr',${pageThr.countPage},'finishOrder_div')">尾页</div>
							</div>
						</div>
					</div>
				</div>
				<form method="post" action="userShop/orderInfo" id="refreshPageForm">
					<input id="currentPageOne" name="currentPageOne" value="${pageOne.currPage }" type="hidden"/>
					<input id="currentPageTwo" name="currentPageTwo" value="${pageTwo.currPage }" type="hidden"/>
					<input id="currentPageThr" name="currentPageThr" value="${pageThr.currPage }" type="hidden"/>
					<input id="changeLocaltion" name="localtion" value="" type="hidden"/>
				</form>
				<form method="post" action="userShop/orderItemInfo" id="orderItemForm">
					<input id="orderUUID" name="orderUUID" value="" type="hidden"/>
				</form>
			</div>
			<!--描述：订单详情部分结束-->
			<!--描述：尾部DIV开始-->
			<jsp:include page="web_foot.jsp"></jsp:include>
			<!--描述：尾部DIV结束-->
		</div>
	</body>
</html>
