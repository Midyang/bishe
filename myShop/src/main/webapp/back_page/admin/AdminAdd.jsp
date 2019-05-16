<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<base href="${pageContext.request.contextPath }/">
	<link rel="stylesheet" href="css/Admin_Log_update_add.css" />
	<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript">
		function verification(){/* 输入信息验证 */
			var vals=true;
			var username=$("#username").val().trim();
			if(!/^[a-zA-Z][a-zA-Z0-9]{4,12}$/.test(username)){
				$("#err_username").html("*必选字段 请输入5到12位字母或数字!");
				$("#err_username").css("color","red");
				vals=false;
			}else{
				$("#err_username").html("*有效");
				$("#err_username").css("color","green");
			}
			
			var codeValid=$("#codeValid").val().trim();
			if(!/^[a-zA-Z0-9]{4}$/.test(codeValid)){
				$("#err_code").html("*必选字段 (4位有效字母或数字!)");
				$("#err_code").css("color","red");
				vals=false;
			}else{
				$("#err_code").html("*有效");
				$("#err_code").css("color","green");
			}

			var password=$("#pwd").val();
			var comfirmWord=$("#comfirmWord").val();
			if(!/^[a-zA-Z0-9]{4,12}$/.test(password)){
				$("#err_password").html("*必选字段 请输入4到12位数字或字母!");
				$("#err_password").css("color","red");
				vals=false;
				return;
			}else{
				$("#err_password").html("*有效");
				$("#err_password").css("color","green");
				if(password != comfirmWord){//两次密码不相等
					$("#err_comfirmWord").html("*两次密码不一致!");
					$("#err_password").html("*两次密码不一致!");
					$("#err_comfirmWord").css("color","red");
					$("#err_password").css("color","red");
					vals=false;
					return;
				}else{
					$("#err_comfirmWord").html("*有效");
					$("#err_comfirmWord").css("color","green");
					$("#err_password").html("*有效");
					$("#err_password").css("color","green");
				}
			}
			if(vals){
				$.post(
						"/admin/add",
						{
							"username":username,
							"password":password,
							"comfirmWord":comfirmWord,
							"codeValid":codeValid
						},
						function(data){
							if(data.id==0){/* 0 验证码 问题  */
								$("#err_code").html(data.msg);
								$("#err_code").css("color","red");
							}else if(data.id==1){/* 1 注册失败  账号问题*/
								$("#err_username").html(data.msg);
								$("#err_username").css("color","red");
							}else if(data.id==2){/* 2 注册失败  密码问题*/
								$("#err_password").html(data.msg);
								$("#err_password").css("color","red");
							}else if(data.id==3){/* 3 确认密码 问题*/
								$("#err_password").html(data.msg);
								$("#err_password").css("color","red");
							}else if(data.id==4){/* 4 注册失败! 原因不明*/
								alert(data.msg);
							}else{/* 0 注册成功 */
								alert(data.msg);
								location.href="/admin/list";
							}
						},
						"json"
					);
			}
		};
	
	</script>
	<title>注册管理员账号</title>
</head>
<body>
	<!-- 标题  -->
		<div id="head">
				<!-- 标题**导航部分  -->
				<div id="head_text">
				注册  &lt; <a href="AdminServlet?m=getList"> 管理员列表 </a> &lt; <a href="back_page/hello.jsp"> 首页</a>
				</div>
		</div>
		<div id="main_add">
			<div id="updateDIV">
				<form id="loginForm">
					<table border="0" cellspacing="0" cellpadding="0" id="tal_add">
						<tr class="input_info">
							<th>管理员账号:</th>
							<td>
								<input type="text" name="username" id="username"/>
							</td>
						</tr>
						<tr class="err_row">
							<td colspan="2">
								<font id="err_username" class="err"></font>
							</td>
						</tr>
						<tr class="input_info">
							<th>管理员密码:</th>
							<td>
								<input type="password" name="password" id="pwd" />
							</td>
						</tr>
						<tr class="err_row">
							<td colspan="2">
								<font id="err_password" class="err"></font>
							</td>
						</tr>
						<tr class="input_info">
							<th>确认密码:</th>
							<td>
								<input type="password" name="comfirmWord" id="comfirmWord" value="" />
							</td>
						</tr>
						<tr class="err_row">
							<td colspan="2">
								<font id="err_comfirmWord" class="err"></font>
							</td>
						</tr>
						<tr id="row_code">
							<th>验证码:</th>
							<td>
								<div id="codeValid_div">
									<input type="text" name="codeValid" id="codeValid" />
								</div>
								<div id="image_div">
									<img src="validateCode/codeValid" title="点击更新图片" onclick="this.src='validateCode/codeValid?time='+new Date().getTime()" width="120px" height="25px" />
								</div>
							</td>
						</tr>
						<tr class="err_row">
							<td colspan="2">
								<font id="err_code" class="err">
								</font>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div id="fenye_div">
									<input type="reset"  value="重置" class="fenye_button"/>
								<input type="button" value="注册" class="fenye_button" onclick="verification()"/>
								</div>
							</td>
						</tr>
					</table>
				</form>
		</div>
	</div>
</body>
</html>