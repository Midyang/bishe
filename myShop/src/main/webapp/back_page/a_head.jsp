<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="${pageContext.request.contextPath }/"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
	<style type="text/css">
			body{
					background-color: #FFFEEF;
			}
			#allDIV{/* 整体DIV */
					width: 100%;
					height: 68px;
					clear: both;
					padding: 0px;
					margin: 0px;
					position: relative;
			}
			#head{/* 商城管理系统整体DIV */
				width: 100%;
				height: 60px;
			}
			#head_1{/* 商城管理系统 文字DIV*/
				width: 240px;
				margin: 0px auto;
				padding-top: 6px;
				font-size: 40px;
				color: #A9A9A9;
				font-family: STCaiyun ;
				text-shadow: 5px 5px 5px 5px black;
				
			}
			#head_2{/* 时间DIV */
				width: 100%;
				color: #999999;
				font-size: 14px;
				padding: 0px;
				margin: 0px;
				position: relative;
			}
			#head_time{
				margin: 0px;
				padding: 0px;
				width: 210px;
				height: 18px;
				position: absolute;
				right: 4px;
				top:-12px;
			}
			
			/* 退出登录框 */
			#userDiv{
				height: 28px;
				width: 160px;
				border: 2px solid #DDDDDD;
				border-radius: 6px;
				box-shadow: 1px 2px 10px 1px #E6E6E6;
				padding-top:8px;
				position: fixed;
				top: 8px;
				right: 16px;
			}
			/*退出登录 	内部字体的样式*/
			#userFont{
				line-height: 24px;
				margin-left: 14px;
				font-size: 16px;
				font-weight: bold;
				color: #888888;
				font-family: arial;
			}
			#usernameFont{
				font-size: 14px;
			}
			
			/*
			 * 隐藏 的DIV 块
			 * */
			#userFuncDiv{
				width: 160px;
				height: 24px;
				position: absolute;
				top:33px;
				visibility: hidden;
				background-color: #FFFEEF;
				border-radius: 4px;
				box-shadow: 1px 2px 10px 1px #E6E6E6;
				padding-top: 6px;
				
			}
			/* 显示div块*/
			#userDiv:hover #userFuncDiv{
				visibility: visible;
			}
			
			#exitDiv p{
				width: 160px;
				margin-top: 15px;
			}
			.textP{
				font-size: 12px;
				text-align: center;
				color: #A9A9A9;
			}
			/*退出功能	超链接*/
			#exitA{
				font-size: 12px;
				text-align: center;
				color: #A9A9A9;
			}
			#exitA:hover{
				font-size: 14px;
				color: red;
			}
		</style>
	<script type="text/javascript" >
			$(function(){
				$.post(
						"login/getAdminName",
						{},
						function(data){
							if(data.id==1){/* 登录成功 */
								$("#usernameFont").html(data.username);
							}else{
								alert(data.msg);
								window.parent.location.href="login/exitLogin";
							}
						},
						"json"
					);
			});
			function getTime(){
				var date = new Date();
				var mon = null;
				switch(date.getDay()){
					case 1:mon = "一";break;
					case 2:mon = "二";break;
					case 3:mon = "三";break;
					case 4:mon = "四";break;
					case 5:mon = "五";break;
					case 6:mon = "六";break;
					case 0:mon = "七";break;
					default:mon = date.getDay();
				}
				document.getElementById("head_time").innerHTML=date.getFullYear()+"年"+date.getMonth()+"月"+date.getDate()+"日 "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+" "+"星期"+mon;
			};
			
			function exitLogin(){
				window.parent.location.href="login/exitLogin";
			};
			var str = setInterval("getTime()",1000);
	</script>
	<title></title>
</head>
<body onload="getTime()">
	<div id="allDIV">
		<div id="head">
			<div id="head_1">
				商城管理系统
			</div>
		</div>
		<div id="head_2" >
				<p id="head_time"></p>
		</div>
		<div id="userDiv">
			<div>
				<font id="userFont">用户:</font>
				<font id="usernameFont"></font>
			</div>
			<div id="userFuncDiv">
					<div id="exitDiv">
						<div onclick="exitLogin()" class="textP" id="exitA">&lt;&lt; 退出登录
						</div>
					</div>
			</div>
		</div>
	</div>
</body>
</html>