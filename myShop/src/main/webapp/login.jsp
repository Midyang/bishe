<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="${pageContext.request.contextPath }/">
		<title>登录页面</title>
		<link rel="stylesheet" href="css/registANDlogin.css" type="text/css"/>
		<link rel="stylesheet" href="css/web_foot.css" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript">
			function login(){
				var value = $("#userLoginForm").serialize();
				$.post(
					"userLR/userLogin",
					value,
					function(data){
						var flag = true;
						//验证码格式不正确
						if(data.code =="A1"){
							document.getElementById("content_main_72").innerText=data.msg;
							flag=false;
						}else{
							document.getElementById("content_main_72").innerText="";
						}
						//验证码不正确
						if(data.code =="A12"){
							document.getElementById("content_main_72").innerText=data.msg;
							flag=false;
						}else if(data.code !="A1"){
							document.getElementById("content_main_72").innerText="";
						}
						//账号格式不正确
						if(data.code =="A2"){
							document.getElementById("content_main_12").innerText=data.msg;
							flag=false;
						}else{
							document.getElementById("content_main_12").innerText="";
						}
						//账号不存在
						if(data.code =="A4"){
							document.getElementById("content_main_12").innerText=data.msg;
							flag=false;
						}else if(data.code !="A2"){
							document.getElementById("content_main_12").innerText="";
						}
						//密码格式不正确
						if(data.code =="A3"){
							document.getElementById("content_main_22").innerText=data.msg;
							flag=false;
						}else{
							document.getElementById("content_main_22").innerText="";
						}
						//密码或账号错误
						if(data.code =="A5"){
							document.getElementById("content_main_22").innerText=data.msg;
							flag=false;
						}else if(data.code !="A3"){
							document.getElementById("content_main_22").innerText="";
						}
						if(flag){
							alert("登录成功...即将为您跳转...");
							var targetURL=document.getElementById("targetURL").value;
							if( targetURL != null && targetURL != "" && targetURL != "undefined"){
								location.href=targetURL;
							}else{
								location.href="userShop/index";
							}
						}
					},
					"json"
				)
			}
		</script>
	</head>
		<body>
		<div id="main_div"> <!--作者：midy 时间：2019-01-17 描述：主体div-->
			<div id="heads"><!-- 作者：midy 时间：2019-01-17 描述：注册页面的头部 -->
				<div id="heads_content">
					<div id="heads_content_logo">
						<div id="heads_content_logo_photo" onclick="shopCarIndexSubmit()"></div>
						<script type="text/javascript">
							function shopCarIndexSubmit(){
								document.getElementById("shopCarIndexForm").submit();
							}
						</script>
						<form action="userShop/index" method="post" id="shopCarIndexForm">
						</form>
						<div id="heads_content_logo_title">欢迎登录</div>
					</div><!-- 作者：midy 时间：2019-01-17 描述：左侧logo -->
					<div id="heads_content_login">
						<span id="heads_content_login_1">没有账号？</span>
						<span>
							<a id="heads_content_login_2" href="register.jsp">请注册 ></a>
						</span>
						
					</div><!-- 作者：midy 时间：2019-01-17 描述：右侧登录-->
				</div>
			</div>
			<hr id="content_hr">
			<div id="content"><!--作者：midy 时间：2019-01-17 描述：主体内容div-->

				<div id="content_main2">
					<form id="userLoginForm" >
						<div id="content_main_1" class="content_main_allDiv"><!--描述：账号输入框-->
							<div class="content_main_1_title">账号</div>
							<div class="content_main_1_input">
								<input type="text" name="username" class="content_main_1_inputs"/>
							</div>
						</div>
						<div id="content_main_12" class="content_main_allDiv2"></div><!--描述：账号信息提示框-->
						
						<div id="content_main_2" class="content_main_allDiv"><!--描述：密码输入框-->
							<div class="content_main_1_title">密码</div>
							<div class="content_main_1_input">
								<input type="password" name="password" class="content_main_1_inputs"/>
							</div>
						</div>
						<div id="content_main_22" class="content_main_allDiv2"></div><!--描述：账号信息提示框-->
						
						<div id="content_main_7" class="content_main_allDiv"><!--描述：验证码输入框-->
							<div class="content_main_1_title">验证码</div>
							<div id="content_main_1_input_yzm">
								<input type="text" name="yzm" id="content_main_1_inputs_yzmBox"/>
							</div>
							<div id="yzm_box">
								<img src="validateCode/codeValid" title="点击更新图片" onclick="this.src='validateCode/codeValid?time='+new Date().getTime()" width="120px" height="25px" />
							</div>
						</div>
						<div id="content_main_72" class="content_main_allDiv2"></div><!--描述：验证码信息提示框-->
						
						<div id="content_main_8" class="content_main_allDiv"><!--描述：登录重置按钮-->
							<div class="content_main_1_buttonDiv">
								<input type="reset"  class="content_main_1_button"/>
							</div>
							<div class="content_main_1_buttonDiv">
								<input type="button" value="登录" class="content_main_1_button" onclick="login()" />
							</div>
						</div>
					</form>
				</div>
			</div>
			<!--描述：尾部DIV开始-->
			<jsp:include page="frontEnd/web_foot.jsp"></jsp:include>
			<!--描述：尾部DIV结束-->
			<!--描述：参数用来跳转到目标页面-->
			<input type="hidden" id="targetURL" value="${target}" >
		</div>
	</body>
</html>
