<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="${pageContext.request.contextPath }/">
		<title>网站首页</title>
		<link rel="stylesheet" href="css/index.css" type="text/css" />
		<link rel="stylesheet" href="css/web_foot.css" type="text/css" />
		<link rel="stylesheet" href="css/web_headNavigation.css" type="text/css" />
		<script type="text/javascript" src="js/index.js"></script>
	</head>
	<body>
		<!--描述：主体DIV开始-->
		<div id="content">
			<!--描述：头部DIV开始-->
			<jsp:include page="web_headNavigation.jsp"></jsp:include>
			<!--描述：头部DIV结束-->
        	<!--描述：内容DIV开始-->
			<div id="main">
				<!--描述：内容左边DIV开始-->
				<div id="main_left">
					<div id="logo">
						<a href="#"><img src="img/LOGTEXT.jpg" id="logoIMG"/></a>
					</div>
					<div id="main_left_ul" onselectstart="return false">
						<ul id="main_left_ul_1"> <!-- 描述：鼠标移入的时候 xs(显示)    鼠标移出的时候 yc（隐藏） -->
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_1')" onmouseout="yc('main_left_ul_1_1')">
								<a href="#" class="main_left_ul_a1">家用电器</a>
							</li>
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_2')" onmouseout="yc('main_left_ul_1_2')">
								<a href="#" class="main_left_ul_a1">手机</a>/
								<a href="#" class="main_left_ul_a">运营商</a>/
								<a href="#" class="main_left_ul_a">数码</a>
							</li>
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_3')" onmouseout="yc('main_left_ul_1_3')">
								<a href="#" class="main_left_ul_a1">电脑</a>/
								<a href="#" class="main_left_ul_a">办公</a>
							</li>
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_4')" onmouseout="yc('main_left_ul_1_4')">
								<a href="#" class="main_left_ul_a1">家居</a>/
								<a href="#" class="main_left_ul_a">家具</a>/
								<a href="#" class="main_left_ul_a">家装</a>/
								<a href="#" class="main_left_ul_a">厨具</a>
							</li>
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_5')" onmouseout="yc('main_left_ul_1_5')">
								<a href="#" class="main_left_ul_a1">男装</a>/
								<a href="#" class="main_left_ul_a">女装</a>/
								<a href="#" class="main_left_ul_a">童装</a>/
								<a href="#" class="main_left_ul_a">内衣</a>
							</li>
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_6')" onmouseout="yc('main_left_ul_1_6')">
								<a href="#" class="main_left_ul_a1">美妆</a>/
								<a href="#" class="main_left_ul_a">个护清洁</a>/
								<a href="#" class="main_left_ul_a">宠物</a>
							</li>
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_7')" onmouseout="yc('main_left_ul_1_7')">
								<a href="#" class="main_left_ul_a1">女鞋</a>/
								<a href="#" class="main_left_ul_a">箱包</a>/
								<a href="#" class="main_left_ul_a">钟表</a>/
								<a href="#" class="main_left_ul_a">珠宝</a>
							</li>
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_8')" onmouseout="yc('main_left_ul_1_8')">
								<a href="#" class="main_left_ul_a1">男鞋</a>/
								<a href="#" class="main_left_ul_a">运动</a>/
								<a href="#" class="main_left_ul_a">户外</a>
							</li>
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_9')" onmouseout="yc('main_left_ul_1_9')">
								<a href="#" class="main_left_ul_a1">房产</a>/
								<a href="#" class="main_left_ul_a">汽车</a>/
								<a href="#" class="main_left_ul_a">汽车用品</a>
							</li>
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_10')" onmouseout="yc('main_left_ul_1_10')">
								<a href="#" class="main_left_ul_a1">母婴</a>/
								<a href="#" class="main_left_ul_a">玩具乐器</a>
							</li>
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_11')" onmouseout="yc('main_left_ul_1_11')">
								<a href="#" class="main_left_ul_a1">食品</a>/
								<a href="#" class="main_left_ul_a">酒类</a>/
								<a href="#" class="main_left_ul_a">生鲜</a>/
								<a href="#" class="main_left_ul_a">特产</a>
							</li>
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_12')" onmouseout="yc('main_left_ul_1_12')">
								<a href="#" class="main_left_ul_a1">艺术</a>/
								<a href="#" class="main_left_ul_a">礼品鲜花</a>/
								<a href="#" class="main_left_ul_a">农资绿植</a>
							</li>
							<li  class="main_left_ul_li" onmouseover="xs('main_left_ul_1_13')" onmouseout="yc('main_left_ul_1_13')">
								<a href="#" class="main_left_ul_a1">医药保健</a>/
								<a href="#" class="main_left_ul_a">计生情趣</a>
							</li>
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_14')" onmouseout="yc('main_left_ul_1_14')">
								<a href="#" class="main_left_ul_a1">图书</a>/
								<a href="#" class="main_left_ul_a">音像</a>/
								<a href="#" class="main_left_ul_a">电子书</a>
							</li>
							<li class="main_left_ul_li" onmouseover="xs('main_left_ul_1_15')" onmouseout="yc('main_left_ul_1_15')">
								<a href="#" class="main_left_ul_a1">机票</a>/
								<a href="#" class="main_left_ul_a">酒店</a>/
								<a href="#" class="main_left_ul_a">旅游</a>/
								<a href="#" class="main_left_ul_a">生活</a>
							</li>
							<li class="main_left_ul_li"  onmouseover="xs('main_left_ul_1_16')" onmouseout="yc('main_left_ul_1_16')">
								<a href="#" class="main_left_ul_a1">理财</a>/
								<a href="#" class="main_left_ul_a">众筹</a>/
								<a href="#" class="main_left_ul_a">白条</a>&nbsp;/
								<a href="#" class="main_left_ul_a">保险</a>
							</li>
							<li class="main_left_ul_li"  onmouseover="xs('main_left_ul_1_17')" onmouseout="yc('main_left_ul_1_17')">
								<a href="#" class="main_left_ul_a1">安装</a>/
								<a href="#" class="main_left_ul_a">装修</a>/
								<a href="#" class="main_left_ul_a">清洗保养</a>
							</li>
						</ul>
					</div>
				</div>
        		<!--描述：内容左边DIV结束-->
        		
        		<!--描述：内容右边DIV开始-->
				<div id="main_right">
					<div id="main_right_top">
						<div id="main_right_top_1">
							<div id="main_right_top_div">
								<form action="userShop/searchShop" method="post">
								<ul id="main_right_top_div_ul">
									<li><input id="main_right_top_1_ssk" type="text" name="keyword" value="${parameter.name}" placeholder="请输入要搜索的商品名称"/><br /></li>
									<li><input type="button" id="main_right_top_1_file1" /></li>
									<li><input type="submit" id="main_right_top_1_file2" value=""></input></li>
									<li id="main_right_top_1_li2">
										<div id="main_right_top_1_GWC" onclick="targetShopCarFormSubmit()">
											<span>我的购物车</span><img src="img/shuzhi0.png" />
										</div>
									</li>
									<li>
										<ul id="main_right_top_1_ul">
											<li id="main_right_top_1_lli" class="main_right_top_1_li">
												<a class="main_right_top_1_a" href="#">
													苹果新品
												</a>
											</li>
											<li class="main_right_top_1_li">
												<a class="main_right_top_1_a" href="#">
													旗舰电竞
												</a>
											</li>
											<li class="main_right_top_1_li">
												<a class="main_right_top_1_a" href="#">
													100元8折
												</a>
											</li>
											<li class="main_right_top_1_li">
												<a class="main_right_top_1_a" href="#">
													荣耀新品
												</a>
											</li>
											<li class="main_right_top_1_li">
												<a class="main_right_top_1_a" href="#">
													满99元减50
												</a>
											</li>
											<li class="main_right_top_1_li">
												<a class="main_right_top_1_a" href="#">
													空调狂欢
												</a>
											</li>
											<li class="main_right_top_1_li">
												<a class="main_right_top_1_a" href="#">
													家具满减
												</a>
											</li>
											<li class="main_right_top_1_li">
												<a class="main_right_top_1_a" href="#">
													净水狂欢
												</a>
											</li>
											<li class="main_right_top_1_li">
												<a class="main_right_top_1_a" href="#">
													装饰品
												</a>
											</li>
										</ul>
									</li>
								</ul>
								</form>
								<form action="userShop/addToShopCar" method="post" id="targetShopCarForms"></form>
							</div>
							
							<div id="main_right_top_1_yzm">
                            <!-- 作者：midy 时间：2018-07-27 描述：验证码部分 -->	
							</div>
							
						</div>
						<div id="main_right_top_2">
							<ul id="main_right_top_2_ul">
								<li>
									<a class="main_right_top_2_a" href="#">秒杀</a>
								</li>
								<li>
									<a class="main_right_top_2_a" href="#">优惠券</a>
								</li>
								<li>
									<a class="main_right_top_2_a" href="#">PLUS会员</a>
								</li>
								<li>
									<a class="main_right_top_2_a" href="#">闪购</a>
								</li>
								<li>
									|
								</li>
								<li>
									<a class="main_right_top_2_a" href="#">拍卖</a>
								</li>
								<li>
									<a class="main_right_top_2_a" href="#">京东服饰</a>
								</li>
								<li>
									<a class="main_right_top_2_a" href="#">京东超市</a>
								</li>
								<li>
									<a class="main_right_top_2_a" href="#">生鲜</a>
								</li>
								<li>
									|
								</li>
								<li>
									<a class="main_right_top_2_a" href="#">全球购</a>
								</li>
								<li>
									<a class="main_right_top_2_a" href="#">京东金融</a>
								</li>
							</ul>
						</div>
					</div>
					<!--
                    	作者：midy
                    	时间：2019-01-17
                    	描述：用于左侧导航栏的详细信息
                    -->
					<div id="main_left_ul_1_1" class="main_right_bottom2">1我是测试 页面</div>
					<div id="main_left_ul_1_2" class="main_right_bottom2">2哈哈</div>
					<div id="main_left_ul_1_3" class="main_right_bottom2">3你好</div>
					<div id="main_left_ul_1_4" class="main_right_bottom2">4欢迎来到商城</div>
					<div id="main_left_ul_1_5" class="main_right_bottom2">5是不是</div>
					<div id="main_left_ul_1_6" class="main_right_bottom2">6觉得我很调皮</div>
					<div id="main_left_ul_1_7" class="main_right_bottom2">7其实我是一个人工智能</div>
					<div id="main_left_ul_1_8" class="main_right_bottom2">8我的主人正在测试我功能</div>
					<div id="main_left_ul_1_9" class="main_right_bottom2">9你没猜错</div>
					<div id="main_left_ul_1_10" class="main_right_bottom2">10对！</div>
					<div id="main_left_ul_1_11" class="main_right_bottom2">11正如你所想</div>
					<div id="main_left_ul_1_12" class="main_right_bottom2">12我的主人就是..</div>
					<div id="main_left_ul_1_13" class="main_right_bottom2">13就是你呀</div>
					<div id="main_left_ul_1_14" class="main_right_bottom2">14亲爱的主人</div>
					<div id="main_left_ul_1_15" class="main_right_bottom2">15请不要抛弃我</div>
					<div id="main_left_ul_1_16" class="main_right_bottom2">16我给你讲个笑话吧</div>
					<div id="main_left_ul_1_17" class="main_right_bottom2">17你是猪...哈哈</div>
					<!--
                    	作者：midy
                    	时间：2019-01-17
                    	描述：用于分割
                    -->
					<div id="main_right_bottom">
						<!--
                        	作者：midy
                        	时间：2019-01-17
                        	描述：图片轮播
                        -->
						<div id="main_right_bottom_left">
							<div id="tplb_div_2" onmouseover="clearRun()" onmouseout="addRun()">
								<div id="tplb_div_button_left" class="tplb_div_buttonAll" onclick="leftMove()">&it;<</div>
								<div id="tplb_div_button_right" class="tplb_div_buttonAll" onclick="rightMove()">&gt;</div>
								<div id="tplb_div_sort1" class="tplb_div_sortAll" onclick="onclickMove('tplb_div_sort1')" onmouseover="changeColorOnOver('tplb_div_sort1')" onmouseout="changeColorOnOut('tplb_div_sort1')"></div>
								<div id="tplb_div_sort2" class="tplb_div_sortAll" onclick="onclickMove('tplb_div_sort2')" onmouseover="changeColorOnOver('tplb_div_sort2')" onmouseout="changeColorOnOut('tplb_div_sort2')"></div>
								<div id="tplb_div_sort3" class="tplb_div_sortAll" onclick="onclickMove('tplb_div_sort3')" onmouseover="changeColorOnOver('tplb_div_sort3')" onmouseout="changeColorOnOut('tplb_div_sort3')"></div>
								<div id="tplb_div_sort4" class="tplb_div_sortAll" onclick="onclickMove('tplb_div_sort4')" onmouseover="changeColorOnOver('tplb_div_sort4')" onmouseout="changeColorOnOut('tplb_div_sort4')"></div>
								<div id="tplb_div_sort5" class="tplb_div_sortAll" onclick="onclickMove('tplb_div_sort5')" onmouseover="changeColorOnOver('tplb_div_sort5')" onmouseout="changeColorOnOut('tplb_div_sort5')"></div>
								<img src="img/t1.jpg" id="imgObj"/><!--图片轮播图片位置-->
							</div>
						</div>
						<div id="main_right_bottom_center">
							<div class="main_right_bottom_center_tu" id="main_right_bottom_center_tu1">
								<img src="img/tu1.png" />
							</div>
							<div class="main_right_bottom_center_tu">
								<img src="img/tu2.png" />
							</div>
							<div class="main_right_bottom_center_tu">
								<img src="img/tu3.png"/>
							</div>
						</div>
						<div id="main_right_bottom_right">
							<div id="main_right_bottom_right_dl">
								<img src="img/touxiang.png" />
								<span>Hi~欢迎来到商城！</span>
								<a href="#" id="main_right_bottom_right_dl_a">登录</a> <a href="#">注册</a><br/>
								<div id="main_right_bottom_right_dl_div1">
									<a>新人福利</a>
								</div>
								<div id="main_right_bottom_right_dl_div2">
									<a>PLUS会员</a>
								</div>
							</div>
							<div id="main_right_bottom_right_d2">
							
                            	
                            <ul id="main_right_bottom_right_d2_ul">
									<li class="main_right_bottom_right_d2_li" id="main_right_bottom_right_d2_right1">
										&nbsp;促销
									</li>
									<li class="main_right_bottom_right_d2_li">
										|
									</li>
									<li class="main_right_bottom_right_d2_li">
										公告
									</li>
									<li id="main_right_bottom_right_d2_right">
										更多
									</li>
								</ul><br />
								<ul id="main_right_bottom_right_d2_ul2">
									<li>国际顶级奢侈品品牌Bale...</li>
									<li>商城PLUS会员权益更新及...</li>
									<li>商城启用全新客服电话“9...</li>
									<li>关于召回普利司通（天津）...</li>
								</ul>
							</div>
							<div id="main_right_bottom_right_d3">
							
							</div>
							
						</div>
						
					</div>
				</div>
        		<!--描述：内容右边DIV结束-->
			</div>
			<!--描述：内容DIV结束-->
			<!--描述：内容内容展示部分开始-->
			<div id="shop_info"><!--描述：商品详情整体DIV 开始-->
				<c:forEach items="${data }" var="blockData">
					<div class="shop_info_one"><!--描述：商品信息DIV 开始-->
						<!--描述：商品种类信息头开始-->
						<div class="shop_info_one_head">
							<div class="shop_info_one_head_title">
								${blockData.oneClass.name}
							</div>
							<div class="shop_info_one_head_value">
								<c:forEach items="${blockData.twoClass }" var="two">
									${two.name}&nbsp;&nbsp;
								</c:forEach>
							</div>
						</div>
						<!--描述：商品种类信息头结束-->
						<!--描述：商品信息体开始-->
						<div class="shop_info_one_body">
							<!--描述：单个商品信息体开始-->
							<c:forEach items="${blockData.product }" var="produ">
								<div class="shop_info_one_body_shopInfo" onclick="showShopInfo('${produ.id}')">
									<div class="shop_info_one_body_shopInfo_photoDiv"><!--描述：单个商品图片放置地方-->
										<img src="${ produ.image}" class="shop_info_one_body_shopInfo_photoDiv_img"/>
									</div>
									<div class="shop_info_one_body_shopInfo_infoDiv"><!--描述：单个商品描述信息放置地方-->
										${ produ.description}
									</div>
									<div class="shop_info_one_body_shopInfo_priceDiv"><!--描述：单个商品价格信息放置地方-->
										￥${ produ.shopPrice}
									</div>
								</div>
						 	</c:forEach>
						 	<!--描述：单个商品信息体结束-->
						</div>
						<!--描述：商品信息体开始-->
					</div><!--描述：商品信息DIV 结束-->
				</c:forEach>
			</div><!--描述：商品详情整体DIV 结束-->
				<!--描述：内容内容展示部分结束-->
	        	<!--描述：尾部DIV开始-->
				<jsp:include page="web_foot.jsp"></jsp:include>
				<!--描述：尾部DIV结束-->
			</div>
		<!--描述：主体DIV结束-->
	</body>
</html>
