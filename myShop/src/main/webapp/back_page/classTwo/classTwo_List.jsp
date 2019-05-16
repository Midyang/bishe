<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.contextPath }/"/>
<link rel="stylesheet" href="css/list.css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	function fenye(curPage){
		var name=document.getElementById("mohu").value;
		location.href="secondcategory/list?curPage="+curPage+"&mohu="+name;
	};
	function mohu() {
		var name=document.getElementById("mohu").value;
		location.href="secondcategory/list?curPage=1&mohu="+name;
	};
	function selectById(id) {
		location.href="secondcategory/selectById?id="+id;
	};
	function deleteById(id,currPage) {
		if(confirm("你确定删除吗?")){
			var name=document.getElementById("mohu").value;
			location.href="secondcategory/deleteById?id="+id+"&curPage="+currPage+"&mohu="+name;
		}
	};
	function add(){
		location.href="secondcategory/addPreList";
	};
	function showColor(id){
		$("#classTwo"+id).css("background-color","#FFEEFF");
	};
	function closeColor(id){
		$("#classTwo"+id).css("background-color","#FFFFFF");
	};
</script>
<title>Insert title here</title>
</head>
	<body>
		<!-- 整体DIV  -->
		<div id="start">
			<!-- 标题  -->
			<div id="head">
				<!-- 标题**导航部分  -->
				<div id="head_text">
				二级分类列表 &lt; <a href="back_page/hello.jsp">&nbsp;首页</a>
				</div>
				<!-- 退出登陆部分  -->
			</div>
			<!-- 根据名字模糊查询  部分-->
			<div id="func_box">		
				<div id="select_button">
					<span>请输入二级分类名称:</span>
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
								二级分类名称
							</th>
							<th class="table_thead_col">
								所属一级分类名称
							</th>
							<th class="table_thead_col_button">
								修改
							</th>
							<th class="table_thead_col_button">
								删除
							</th>
						</tr>
						<!--
						描述：表格内容部分
						 -->
	                    <c:forEach items="${list }" var="l">
	                    	<tr id="classTwo${l.id }" onmouseover="showColor('${l.id }')" onmouseout="closeColor('${l.id }')">
								<td class="table_tr_col">${l.id}</td>
								<td class="table_tr_col">${l.name}</td>
								<td class="table_tr_col">${l.category.name}</td>
								<td class="table_tr_col">
									<button onclick="selectById('${l.id}')" class="table_tr_col_button">修改</button>
								</td>
								<td class="table_tr_col">
									<button onclick="deleteById(${l.id},${page.currPage})" class="table_tr_col_button">删除</button>
								</td>
							</tr>
	                    </c:forEach>
					</table>
				</div>
					<!--
                    	描述：表尾 
                    -->
					<div id="foot_fenye_div">
						<div id="fenye_div">
							<span>${page.currPage }/${page.countPage }</span>
							<button onclick="fenye(1)" class="fenye_button">首页</button>
							<button onclick="fenye('${page.prePage}')" class="fenye_button">上一页</button>
							<button onclick="fenye('${page.nextPage}')" class="fenye_button">下一页</button>
							<button onclick="fenye('${page.countPage}')" class="fenye_button">尾页</button>
						</div>
					</div>
			</div>
		</div>
	</body>
</html>