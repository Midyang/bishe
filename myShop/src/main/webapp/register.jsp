<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="${pageContext.request.contextPath }/">
		<title>注册页面</title>
		<link rel="stylesheet" href="css/registANDlogin.css" type="text/css"/>
		<link rel="stylesheet" href="css/web_foot.css" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript">
			function register(){
				var flag=true;
				var value = $("#userRegistForm").serialize();
				$.post(
					"userLR/userRegist",
					value,
					function(data){
						//验证码格式不正确
						if(data.code=="A1"){
							document.getElementById("content_main_72").innerText=data.msg;
							flag=false;
						}else{
							document.getElementById("content_main_72").innerText="";
						}
						//验证码不正确
						if(data.code=="A12"){
							document.getElementById("content_main_72").innerText=data.msg;
							flag=false;
						}else if( data.code!="A1" ){
							document.getElementById("content_main_72").innerText="";
						}
						//账号格式不正确
						if(data.code=="A2"){
							document.getElementById("content_main_12").innerText=data.msg;
							flag=false;
						}else{
							document.getElementById("content_main_12").innerText="";
						}
						//账号已经被占用
						if(data.code=="A9"){
							document.getElementById("content_main_12").innerText=data.msg;
							flag=false;
						}else if( data.code!="A2" ){
							document.getElementById("content_main_12").innerText="";
						}
						//密码格式不正确
						if(data.code=="A3"){
							document.getElementById("content_main_22").innerText=data.msg;
							flag=false;
						}else{
							document.getElementById("content_main_22").innerText="";
						}
						//确认密码格式不正确
						if(data.code=="A4"){
							document.getElementById("content_main_32").innerText=data.msg;
							flag=false;
						}else{
							document.getElementById("content_main_32").innerText="";
						}
						//两次密码不一致
						if(data.code=="A10"){
							document.getElementById("content_main_22").innerText=data.msg;
							flag=false;
						}else if( data.code!="A3"){
							document.getElementById("content_main_22").innerText="";
						}
						//姓名不能为空
						if(data.code=="A5"){
							document.getElementById("content_main_42").innerText=data.msg;
							flag=false;
						}else{
							document.getElementById("content_main_42").innerText="";
						}
						//手机号码格式正确
						if(data.code=="A6"){
							document.getElementById("content_main_52").innerText=data.msg;
							flag=false;
						}else{
							document.getElementById("content_main_52").innerText="";
						}
						//地址不能为空
						if(data.code=="A7"){
							document.getElementById("content_main_62").innerText=data.msg;
							flag=false;
						}else{
							document.getElementById("content_main_62").innerText="";
						}
						//邮箱格式不正确
						if(data.code=="A8"){
							document.getElementById("content_main_92").innerText=data.msg;
							flag=false;
						}else{
							document.getElementById("content_main_92").innerText="";
						}
						//注册失败,原因保留
						if(data.code=="A11"){
							alert(data.msg);
							flag=false;
						}
						if(flag){
							alert("注册成功 ! 为您跳转到登录页面 ...");
							location.href="login.jsp";
						}
						
					},
					"json"
				)
			}
			function userNameVerify(){
				var username=document.getElementById("usernameID").value;
				$.post(
					"userLR/isExistUser",
					{"username":username},
					function(data){
						//账号格式不正确
						if(data.code == "B1"){
							document.getElementById("content_main_12").innerText=data.msg;
							document.getElementById("subButtn").disabled="disabled";
							return;
						}
						//账号已经被注册
						if(data.code == "B2"){
							document.getElementById("content_main_12").innerText=data.msg;
							document.getElementById("subButtn").disabled="disabled";
							return;
						}
						document.getElementById("subButtn").disabled="";
						document.getElementById("content_main_12").innerText="";
					},
					"json"
				);
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
						<div id="heads_content_logo_title">欢迎注册</div>
					</div><!-- 作者：midy 时间：2019-01-17 描述：左侧logo -->
					<div id="heads_content_login">
						<span id="heads_content_login_1">已有账号？</span>
						<span>
							<a id="heads_content_login_2" href="login.jsp">请登录 ></a>
						</span>
						
					</div><!-- 作者：midy 时间：2019-01-17 描述：右侧登录-->
				</div>
			</div>
			<hr id="content_hr">
			<div id="content"><!--作者：midy 时间：2019-01-17 描述：主体内容div-->

				<div id="content_main">
					<form id="userRegistForm">
						<div id="content_main_1" class="content_main_allDiv"><!--描述：账号输入框-->
							<div class="content_main_1_title">账号</div>
							<div class="content_main_1_input">
								<input id="usernameID" type="text" name="username" class="content_main_1_inputs" onblur="userNameVerify()"/>
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
						
						<div id="content_main_3" class="content_main_allDiv"><!--描述：密码确认输入框-->
							<div class="content_main_1_title">确认密码</div>
							<div class="content_main_1_input">
								<input type="password" name="passwordTwo" class="content_main_1_inputs"/>
							</div>
						</div>
						<div id="content_main_32" class="content_main_allDiv2"></div><!--描述：密码确认信息提示框-->
						
						<div id="content_main_4" class="content_main_allDiv"><!--描述：姓名输入框-->
							<div class="content_main_1_title">姓名</div>
							<div class="content_main_1_input">
								<input type="text" name="name" class="content_main_1_inputs"/>
							</div>
						</div>
						<div id="content_main_42" class="content_main_allDiv2"></div><!--描述：姓名信息提示框-->
						
						<div id="content_main_5" class="content_main_allDiv"><!--描述：手机号输入框-->
							<div class="content_main_1_title">手机号</div>
							<div class="content_main_1_input">
								<input type="text" name="phone" class="content_main_1_inputs"/>
							</div>
						</div>
						<div id="content_main_52" class="content_main_allDiv2"></div><!--描述：手机号信息提示框-->
						
						<div id="content_main_6" class="content_main_allDiv"><!--描述：地址输入框-->
							<div class="content_main_1_title">地址</div>
							<div class="content_main_1_input">
								<input type="text" name="addr" class="content_main_1_inputs"/>
							</div>
						</div>
						<div id="content_main_62" class="content_main_allDiv2"></div><!--描述：地址信息提示框-->
						
						<div id="content_main_9" class="content_main_allDiv"><!--描述：邮箱输入框-->
							<div class="content_main_1_title">邮箱</div>
							<div class="content_main_1_input">
								<input type="text" name="email" class="content_main_1_inputs"/>
							</div>
						</div>
						<div id="content_main_92" class="content_main_allDiv2"></div><!--描述：邮箱信息提示框-->
						
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
								<input id="subButtn" type="button" class="content_main_1_button" onclick="register()" value="提交" />
							</div>
						</div>
					</form>
				</div>
			</div>
			<!--描述：尾部DIV开始-->
			<jsp:include page="frontEnd/web_foot.jsp"></jsp:include>
			<!--描述：尾部DIV结束-->
		</div>
	</body>
</html>
