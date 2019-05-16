<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="${pageContext.request.contextPath }/">
		<link rel="stylesheet" href="css/searchShop.css" type="text/css"/>
		<link rel="stylesheet" href="css/web_foot.css" type="text/css" />
		<link rel="stylesheet" href="css/web_headNavigation.css" type="text/css" />
		<link rel="stylesheet" href="css/web_searchBox.css" type="text/css" />
		<script type="text/javascript" src="js/searchShop.js" ></script>
		<title>商品搜索页</title>
	</head>
	<body onload="load()">
		<form action="userShop/searchShop" method="post" id="searchForm">
			<div id="main_div">
				<!--描述：头部DIV开始-->
				<jsp:include page="web_headNavigation.jsp"></jsp:include>
				<!--描述：头部DIV结束-->
				<div id="query_div">
					<div id="query_div_logo">
						<div id="query_div_logo_photo" onclick="shopCarIndexSubmit()"></div>
					</div>
					<div id="query_div_selectBox">
						<ul id="main_right_top_div_ul">
							<li><input id="main_right_top_1_ssk" type="text" name="keyword" value="${parameter.name}" placeholder="请输入要搜索的商品名称"/><br /></li>
							<li><input type="button" id="main_right_top_1_file2" onclick="formSubmit()"/></li>
							<li id="main_right_top_1_li2">
								<div id="main_right_top_1_GWC">
									<span>我的购物车</span><img src="img/shuzhi0.png" />
								</div>
							</li>
						</ul>
					</div>
				</div>
				<!--
	            	作者：Midy
	            	时间：2019-01-25
	            	描述：更多选择
	            -->
				<div id="searchFilterDiv">
					<div id="searchFilterDiv_price">
						价格区间：<input type="number" name="minPrice" value="${parameter.minPrice}" min="0" value="0.0" class="searchFilterDiv_price_num" placeholder="0.0">&nbsp;至&nbsp;<input type="number" name="maxPrice" value="${parameter.maxPrice}" min="0" value="0.0" class="searchFilterDiv_price_num" placeholder="1000000">
					</div>
					<div id="searchFilterDiv_hot">
						是否热门：
						<select id="searchFilterDiv_hot_select" name="isHot">
							<option value="2" ${parameter.isHot==2?"selected='selected'":"" }>不限制</option>
							<option value="1" ${parameter.isHot==1?"selected='selected'":"" }>热门</option>
							<option value="0" ${parameter.isHot==0?"selected='selected'":"" }>不热门</option>
						</select>
					</div>
					<div id="searchFilterDiv_discription">
						更多信息：<input id="searchFilterDiv_discriptionMore" value="${parameter.description }"type="text" name="description" placeholder="商品描述 可省略"/>
					</div>
					<div id="searchFilterDiv_sort">
						价格排序：&nbsp;升序<input ${parameter.priceSort==1?"checked='checked='":"" } id="searchFilterDiv_sortup" type="radio" name="priceSort" value="1"/>
						&nbsp;降序<input ${parameter.priceSort==0?"checked='checked='":"" } id="searchFilterDiv_sortDown" type="radio" name="priceSort" value="0"/>
					</div>
				</div>
				<!--
	            	作者：Midy
	            	时间：2019-01-25
	            	描述：商品一二级分类
	            -->
	            <div id="shop_class">
	            	<div id="shop_class_daohang">
	            		<c:if test="${not empty data}">
		            		<ul class="shop_class_daohang_ul"><!--设计为分类导航栏-->
								<c:forEach items="${data }" var="blockData">
									<li class="shop_class_daohang_li1"><!-- 一级分类 内嵌耳机分类	到时候循环循环生成-->
										<b>${blockData.oneClass.name }</b>
										<ul class="shop_class_daohang_ul2">
											<c:forEach items="${blockData.twoClass }" var="two">
												<li class="shop_class_daohang_li2" onclick="changeTwoClassStatus(${two.id})"><span id="TwoClass${two.id }">${two.name}</span></li>
											</c:forEach>
										</ul>
									</li>
								</c:forEach>
							</ul>
	            		</c:if>
	            		<input type="hidden" id="scid" name="scid" value="${parameter.scid}">
	            		<input type="hidden" id="lastTwoClass" value="0">
					</div>
	            </div>
				<!--描述：商品详情部分-->
				<div id="shop_info">
					<div class="shop_info_one"><!--描述：商品信息DIV 开始-->
						<!--描述：商品种类信息头开始-->
						<div class="shop_info_one_head">
							<div class="shop_info_one_head_title">
							</div>
							<div class="shop_info_one_head_value">
							</div>
						</div>
						<!--描述：商品种类信息头结束-->
						<!--描述：商品信息体开始-->
						<div class="shop_info_one_body">
							<!--描述：单个商品信息体开始-->
							<c:forEach items="${productList }" var="product">
								<div class="shop_info_one_body_shopInfo" onclick="showShopInfo('${product.id}')">
									<div class="shop_info_one_body_shopInfo_photoDiv"><!--描述：单个商品图片放置地方-->
										<img src="${product.image }" class="shop_info_one_body_shopInfo_photoDiv_img"/>
									</div>
									<div class="shop_info_one_body_shopInfo_infoDiv"><!--描述：单个商品描述信息放置地方-->
										${product.description }
									</div>
									<div class="shop_info_one_body_shopInfo_priceDiv"><!--描述：单个商品价格信息放置地方-->
										￥${product.shopPrice }
									</div>
								</div>
							</c:forEach>
							<!--描述：单个商品信息体结束-->
	
						</div>
						<!--描述：商品信息体开始-->
						<div id="fenyeDiv">
							<div id="currentPage">
								${page.currPage }/${page.countPage }
							</div>
							<div id="baoliu"></div>
							<div id="firstPage" onclick="pageSearch(1)">首页</div>
							<div id="previousPage" onclick="pageSearch(${page.prePage})">上一页</div>
							<div id="fixPage">跳到<input type="number" id="fixPageInput" min="1" onblur="pageSearch(this.value)"/>页 </div>
							<div id="nextPage" onclick="pageSearch(${page.nextPage})">下一页</div>
							<div id="lastPage" onclick="pageSearch(${page.countPage})">尾页</div>
							<input type="hidden" id="currentPages" name="currentPage" value="1" />
						</div>
					</div><!--描述：商品信息DIV 结束-->
					<!--描述：商品信息DIV 结束-->
				</div>
				<!--描述：商品详情部分结束-->
				<!--描述：尾部DIV开始-->
				<jsp:include page="web_foot.jsp"></jsp:include>
				<!--描述：尾部DIV结束-->
			</div>
		</form>
		<form action="userShop/index" method="post" id="shopCarIndexForm">
		</form>
	</body>
</html>
