<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
			<div id="heads">
				<ul id="list_ul">
					<li id="ul_li_1"></li>
					<li><a href="#">北京</a> <!-- 作者：midy 时间：2019-01-18  描述： 地址位置 下拉框 -->
						<div id="list_ul_li_weizhi">
						</div>
					</li>
					<li id="ul_li_2" >
						<c:if test="${user == null }">
							<a href="login.jsp">你好，请登录</a><!-- 作者：midy 时间：2019-01-18  描述： 登录-->
						</c:if>
						<c:if test="${user != null }">
								<a href="userLR/exitLogin">${user.name}</a>
							<!-- 作者：midy 时间：2019-01-18  描述： 登录信息-->
						</c:if>
					</li>
					<li><a href="register.jsp"><span id="ul_li_3">免费注册</span></a><!-- 作者：midy 时间：2019-01-18  描述： 注册--></li>
					<li class="tu1"></li>
					<li ><a href="userShop/orderInfo">我的订单</a><!-- 作者：midy 时间：2019-01-18  描述： 订单--></li>
					<li class="tu1"></li>
					<li id="list_ul_li_shangchengLi"><a href="#">我的商城 ∨</a><!-- 作者：midy 时间：2019-01-18  描述：我的商城-->
						<div id="list_ul_li_shangcheng">
							<ul class="list_ul_li_shangcheng_ul"><!--设计为分类导航栏-->
								<li class="list_ul_li_shangcheng_li1"><!-- 一级分类 内嵌耳机分类	到时候循环循环生成-->
									<ul class="list_ul_li_shangcheng_ul2">
										<li class="list_ul_li_shangcheng_li2">待处理订单</li>
										<li class="list_ul_li_shangcheng_li2">消息</li>
										<li class="list_ul_li_shangcheng_li2">返修退换货</li>
										<li class="list_ul_li_shangcheng_li2">我的问答</li>
										<li class="list_ul_li_shangcheng_li2">降价商品</li>
										<li class="list_ul_li_shangcheng_li2">我的关注</li>
									</ul>
								</li>
								<li class="list_ul_li_shangcheng_li1"><hr/></li>
								<li class="list_ul_li_shangcheng_li1"><!-- 一级分类 内嵌耳机分类	到时候循环循环生成-->
									<ul class="list_ul_li_shangcheng_ul2">
										<li class="list_ul_li_shangcheng_li2">我的优惠券</li>
										<li class="list_ul_li_shangcheng_li2">我的理财</li>
									</ul>
								</li>
							</ul>
						</div>
					</li>
					<li class="tu1"></li>
					<li><a href="#">商城会员</a><!-- 作者：midy 时间：2019-01-18  描述：我的会员--></li>
					<li class="tu1"></li>
					<li><a href="#">企业采购</a><!-- 作者：midy 时间：2019-01-18  描述：采购--></li>
					<li class="tu1"></li>
					<li id="list_ul_li_khfwLi"><a href="#">客户服务 ∨</a><!-- 作者：midy 时间：2019-01-18  描述：服务-->
						<div id="list_ul_li_khfw">
							<ul class="list_ul_li_khfw_ul"><!--设计为分类导航栏-->
								<li class="list_ul_li_khfw_li1"><!-- 一级分类 内嵌耳机分类	到时候循环循环生成-->
									<b>客户</b>
									<ul class="list_ul_li_khfw_ul2">
										<li class="list_ul_li_khfw_li2">帮助中心</li>
										<li class="list_ul_li_khfw_li2">售后服务</li>
										<li class="list_ul_li_khfw_li2">在线客服</li>
										<li class="list_ul_li_khfw_li2">意见建议</li>
										<li class="list_ul_li_khfw_li2">电话客服</li>
										<li class="list_ul_li_khfw_li2">客服邮箱</li>
										<li class="list_ul_li_khfw_li2">金融咨询</li>
										<li class="list_ul_li_khfw_li2">全球售客服</li>
									</ul>
								</li>
								<li class="list_ul_li_khfw_li1"><!-- 一级分类 内嵌耳机分类	到时候循环循环生成-->
									<b>商户</b>
									<ul class="list_ul_li_khfw_ul2">
										<li class="list_ul_li_khfw_li2">合作招商</li>
										<li class="list_ul_li_khfw_li2">学习中心</li>
										<li class="list_ul_li_khfw_li2">商家后台</li>
										<li class="list_ul_li_khfw_li2">商城工作台</li>
										<li class="list_ul_li_khfw_li2">商家帮助</li>
										<li class="list_ul_li_khfw_li2">规则平台</li>
									</ul>
								</li>
							</ul>
						</div>
					</li>
					<li class="tu1"></li>
					<li id="list_ul_li_daohangLi"><a href="#">网站导航 ∨</a><!-- 作者：midy 时间：2019-01-18  描述：网站导航-->
						<div id="list_ul_li_daohang">
							<ul class="list_ul_li_daohang_ul"><!--设计为分类导航栏-->
								<c:forEach items="${data }" var="blockData">
										<li class="list_ul_li_daohang_li1"><!-- 一级分类 内嵌二级分类 循环生成-->
											<b>${blockData.oneClass.name }</b>
											<ul class="list_ul_li_daohang_ul2">
												<c:forEach items="${blockData.twoClass }" var="two">
													<li class="list_ul_li_daohang_li2">${two.name}</li>
												</c:forEach>
											</ul>
										</li>
								</c:forEach>
							</ul>
						</div>
					</li>
					<li class="tu1"></li>
					<li id="list_ul_li_sjscLi"><a href="#">手机商城 ∨</a><!-- 作者：midy 时间：2019-01-18  描述：手机商城-->
						<div id="list_ul_li_sjsc">
							<div id="list_ul_li_sjsc_1"></div>
							<div id="list_ul_li_sjsc_2">
								<br/><span>手机商城</span><br/>
								<span>拿出手机扫一扫</span>
							</div>
						</div>
					</li>
				</ul>
			</div>