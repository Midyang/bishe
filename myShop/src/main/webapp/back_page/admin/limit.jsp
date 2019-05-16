<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.contextPath }/"/>
<link rel="stylesheet" href="css/limit.css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	function returnPage(){
		location.href="product/list";
	};
	function changeChoiceBox(strId){
		if(strId != null){
			var box= document.getElementById("checkbox"+strId);
			if(box.checked){
				box.checked=false;
			} else{
				box.checked=true;
			}
		}
	};
</script>
<title>权限管理页面</title>
</head>
<body>
<!-- 开始 -->
		<div id="start">
			<!-- 标题  -->
			<div id="head">
				<!-- 标题**导航部分  -->
				<div id="head_text">
				权限管理  &lt; <a href="product/list"> 管理员列表 </a>  &lt; <a href="back_page/hello.jsp">&nbsp;首页</a>
				</div>
				<!-- 退出登陆部分  -->
			</div>
			<!-- 表格-->
			<div id="main">
				<!-- 要提交的表单-->
				<form action="admin/saveLimit" method="post" enctype="multipart/form-data" onsubmit="return verify()">
					<div id="dataDiv">
						<div id="accountInfo">
							<span>当前账号：</span><span>${targetAdmin.username}</span>
							<input type="hidden" value="${targetAdmin.id}" name="targetAdminId">
						</div>
						<div id="partDataDiv">
							<div id="partDataDivHead">
								<div class="partDataDivHeadChoice"><!--选择-->
									选择
								</div>
								<div class="partDataDivHeadSerial"><!--序号-->
									序号
								</div>
								<div class="partDataDivHeadLimitName"><!--权限名称-->
									权限名称
								</div>
							</div>
							<!-- 权限-->
							<div id="DataDivBody">
								<!-- 权限项-->
								<div class="partDataDivBody" onclick="changeChoiceBox(2)">
									<div class="partDataDivHeadChoice"><!--选择-->
										<input type="checkbox" value="2" id="checkbox2" name="menu" ${fn:contains(menus,"101")?"checked='checked'":""} />
									</div>
									<div class="partDataDivHeadSerial"><!--序号-->
										1
									</div>
									<div class="partDataDivHeadLimitName"><!--权限名称-->
										管理员
									</div>
								</div>
								<div class="partDataDivBody" onclick="changeChoiceBox(3)">
									<div class="partDataDivHeadChoice"><!--选择-->
										<input type="checkbox" value="3" id="checkbox3" name="menu" ${fn:contains(menus,"102")?"checked='checked'":""} />
									</div>
									<div class="partDataDivHeadSerial"><!--序号-->
										2
									</div>
									<div class="partDataDivHeadLimitName"><!--权限名称-->
										一级分类管理
									</div>
								</div>
								<div class="partDataDivBody" onclick="changeChoiceBox(4)">
									<div class="partDataDivHeadChoice"><!--选择-->
										<input type="checkbox" value="4" id="checkbox4" name="menu" ${fn:contains(menus,"103")?"checked='checked'":""} />
									</div>
									<div class="partDataDivHeadSerial"><!--序号-->
										3
									</div>
									<div class="partDataDivHeadLimitName"><!--权限名称-->
										二级分来管理
									</div>
								</div>
								<div class="partDataDivBody" onclick="changeChoiceBox(5)">
									<div class="partDataDivHeadChoice"><!--选择-->
										<input type="checkbox" value="5" id="checkbox5" name="menu" ${fn:contains(menus,"104")?"checked='checked'":""} />
									</div>
									<div class="partDataDivHeadSerial"><!--序号-->
										4
									</div>
									<div class="partDataDivHeadLimitName"><!--权限名称-->
										商品信息管理
									</div>
								</div>
								<div class="partDataDivBody" onclick="changeChoiceBox(6)">
									<div class="partDataDivHeadChoice"><!--选择-->
										<input type="checkbox" value="6" id="checkbox6" name="menu" ${fn:contains(menus,"105")?"checked='checked'":""} />
									</div>
									<div class="partDataDivHeadSerial"><!--序号-->
										5
									</div>
									<div class="partDataDivHeadLimitName"><!--权限名称-->
										订单管理
									</div>
								</div>
								<div class="partDataDivBody" onclick="changeChoiceBox(7)">
									<div class="partDataDivHeadChoice"><!--选择-->
										<input type="checkbox" value="7" id="checkbox7" name="menu" ${fn:contains(menus,"106")?"checked='checked'":""} />
									</div>
									<div class="partDataDivHeadSerial"><!--序号-->
										6
									</div>
									<div class="partDataDivHeadLimitName"><!--权限名称-->
										用户管理
									</div>
								</div>
								<div class="partDataDivBody" onclick="changeChoiceBox(8)">
									<div class="partDataDivHeadChoice"><!--选择-->
										<input type="checkbox" value="8" id="checkbox8" name="menu" ${fn:contains(menus,"107")?"checked='checked'":""} />
									</div>
									<div class="partDataDivHeadSerial"><!--序号-->
										7
									</div>
									<div class="partDataDivHeadLimitName"><!--权限名称-->
										权限管理
									</div>
								</div>
								<!-- 权限项-->
							</div>
						</div>
					</div>
					<!--第六行 功能按钮-->
					<div>
						<div id="func_button_div">
							<input type="button" onclick="returnPage()" value="返回" class="func_button"/>
							<input type="reset" value="重置" class="func_button"/>
							<input type="submit" value="保存" class="func_button"/>
							
						</div>
					</div>
				</form>
			<!-- 表格div-->
			</div>
		<!--主体div -->
		</div>
		${exit}<!-- 用于退出操作 -->
</body>
</html>