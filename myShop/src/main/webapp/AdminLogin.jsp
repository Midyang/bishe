<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${pageContext.request.contextPath }/"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/Admin_Log_update_add.css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	function login(){/*用户登录*/
		var vals=true;/* 测试 */
		var username=$("#username").val().trim();
		if(!/^[a-zA-Z][a-zA-Z0-9]{3,12}$/.test(username)){
			$("#err_username").html("*必选字段 请输入4到12位字母或数字!");
			$("#err_username").css("color","red");
			vals=false;
		}else{
			$("#err_username").html("*有效");
			$("#err_username").css("color","green");
		}
		
		var password=$("#pwd").val();
		if(!/^[a-zA-Z0-9]{4,12}$/.test(password)){
			$("#err_password").html("*必选字段 请输入4到12位数字或字母!");
			$("#err_password").css("color","red");
			vals=false;
		}else{
			$("#err_password").html("*有效");
			$("#err_password").css("color","green");
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
		if(vals){
			var val=$("#loginForm").serialize();
			$.post(
				"login/adminLogin",
				val,
				function(data){
					var lable=true;
					if(data.id==0){/*验证码信息不正确*/
						$("#err_code").css("color","red");
						$("#err_code").html(data.msg);
						lable=false;
					}else{
						$("#err_code").html("");
					}
					
					if(data.id==1){/*该用户名不存在*/
						$("#err_username").css("color","red");
						$("#err_username").html(data.msg);
						lable=false;
					}else{
						$("#err_username").html("");
						
					}
					
					if(data.id==2){/*密码错误*/
						$("#err_password").css("color","red");
						$("#err_password").html(data.msg);
						lable=false;
					}else{/*验证码,账号,密码,正确*/
						$("#err_password").html("");
					}
					
					if(lable){
						location.href="admin/index";
					}else{
						return;
					}
					
				},//functon结束
				"json"
			);
		}
	};
</script>
<title>商城管理系统-管理员登录</title>
</head>
<body>
		<div id="main">
			<div id="loginDIV">
				<form id="loginForm" action="" method="post" >
					<table border="0" cellspacing="0" cellpadding="0" id="tal">
						<tr class="input_info">
							<th>管理员账号:</th>
							<td>
								<input type="text" name="username" id="username" />
							</td>
						</tr>
						<tr class="err_row">
							<td colspan="2">
								<font id="err_username" class="err">
								</font>
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
								<font id="err_password" class="err">
								</font>
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
									<input type="button" onclick="login()" value="登录" class="fenye_button"/>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>