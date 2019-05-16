<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.contextPath }/">
<link rel="stylesheet" href="css/list.css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	function fenye(curPage){/* 分页查询  */
		var name=document.getElementById("mohu").value;
		location.href="admin/list?curPage="+curPage+"&mohuName="+name;
	};
	function mohu() {/* 模糊查询  */
		var name=document.getElementById("mohu").value;
		location.href="admin/list?curPage=1&mohuName="+name;
	};
	function selectById(id) {/* 修改前查询  */
		location.href="admin/selectById?id="+id;
	};
	function deleteById(id,currPage) {/* 删除  */
		if(confirm("确认删除吗?")){
			var name=document.getElementById("mohu").value;
			location.href="admin/del?id="+id+"&curPage="+currPage+"&mohuName="+name;
		}
	};
	function add(){/* 注册  */
		location.href="back_page/admin/AdminAdd.jsp";
	};
	function showColor(id){
		$("#admin"+id).css("background-color","#FFEEFF");
	};
	function closeColor(id){
		$("#admin"+id).css("background-color","#FFFFFF");
	};
	function selectLimitById(id){
		document.getElementById("limitFormAdminId").value=id;
		document.getElementById("limitForm").submit();
	};
</script>
<title>管理员信息列表</title>
</head>
<body>
		<!-- 整体DIV  -->
		<div id="start">
			<!-- 标题  -->
			<div id="head">
				<!-- 标题**导航部分  -->
				<div id="head_text">
				管理员列表  &lt; <a href="back_page/hello.jsp">&nbsp;首页</a>
				</div>
			</div>
			<!-- 根据名字模糊查询  部分-->
			<div id="func_box">		
				<div id="select_button">
					<span>请输入账号:</span>
					<input type="text" name="select" id="mohu" value="${mohu }" />
					<button onclick="mohu()">查询</button>
				</div>
			</div>
			<!-- 表格主体   部分-->
			<div id="main">
				<!-- 添加按钮   -->
				<div id="add_button">
					<div id="add_button_float">
							<button onclick="add()">添加</button>
					</div>	
				</div>
				<div id="table_div">
					<!-- 表格 -->
					<table id="main_table"  cellpadding="0" cellspacing="0">
						<!-- 表格头 -->
						<tr id="main_table_thead">
							<th class="table_thead_col">
								序号
							</th>
							<th class="table_thead_col">
								账号
							</th>
							<th class="table_thead_col">
								密码
							</th>
							<th class="table_thead_col_button">
								权限
							</th>
							<th class="table_thead_col_button">
								编辑
							</th>
							<th class="table_thead_col_button">
								删除
							</th>
						</tr>
						<!--
						描述：表格内容部分
						 -->
	                    <c:forEach items="${list }" var="l">
	                    	<tr id="admin${l.id }" onmouseover="showColor('${l.id }')" onmouseout="closeColor('${l.id }')" >
								<td class="table_tr_col">${l.id }</td>
								<td class="table_tr_col">${l.username }</td>
								<td class="table_tr_col">${l.password }</td>
								<td class="table_tr_col">
									<button onclick="selectLimitById('${l.id}')" class="table_tr_col_button">权限</button>
								</td>
								<td class="table_tr_col">
									<button onclick="selectById('${l.id}')" class="table_tr_col_button">修改</button>
								</td>
								<td class="table_tr_col">
									<button onclick="deleteById('${l.id}','${page.currPage}')" class="table_tr_col_button">删除</button>
								</td>
							</tr>
	                    </c:forEach>
					</table>
				</div>
					<!--
                    	描述：分页div
                    -->
					<div id="foot_fenye_div">
						<div id="fenye_div">
							<span>${page.currPage }/${page.countPage } </span>
							<button onclick="fenye(1)" class="fenye_button">首页</button>
							<button onclick="fenye('${page.prePage}')" class="fenye_button">上一页</button>
							<button onclick="fenye('${page.nextPage}')" class="fenye_button">下一页</button>
							<button onclick="fenye('${page.countPage}')" class="fenye_button">尾页</button>
						</div>
					</div>
			</div>
		</div>
		<form id="limitForm" action="admin/selectLimitById" method="post">
			<input type="hidden" value=""  id="limitFormAdminId" name="targetAdminId"/>
		</form>
		${exit}
	</body>
</html>