<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
				<div id="query_div">
					<form action="userShop/searchShop" method="post">
						<div id="query_div_logo">
							<div id="query_div_logo_photo" onclick="shopCarIndexSubmit()"></div>
						</div>
						<div id="query_div_selectBox">
							<ul id="main_right_top_div_ul">
								<li><input id="main_right_top_1_ssk" type="text" name="keyword" value="${parameter.name}" placeholder="请输入要搜索的商品名称"/><br /></li>
								<li><input type="submit" id="main_right_top_1_file2" value=""></input></li>
								<li id="main_right_top_1_li2">
									<div id="main_right_top_1_GWC" onclick="shopCarFormSubmit()">
										<span>我的购物车</span><img src="img/shuzhi0.png" />
									</div>
								</li>
							</ul>
						</div>
					</form>
					<form action="userShop/addToShopCar" method="post" id="shopCarSearchBoxForm">
					</form>
					<form action="userShop/index" method="post" id="shopCarIndexForm">
					</form>
				</div>
				<script type="text/javascript">
					function shopCarFormSubmit(){
						document.getElementById("shopCarSearchBoxForm").submit();
					}
					function shopCarIndexSubmit(){
						document.getElementById("shopCarIndexForm").submit();
					}
				</script>
				