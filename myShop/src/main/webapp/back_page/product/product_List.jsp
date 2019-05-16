<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.contextPath }/"/>
<link rel="stylesheet" href="css/list.css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	function fenye(curPage){
		var flag = document.getElementById("frmFlag").value;
		if("0"==flag){
			var name=document.getElementById("mohu").value;
			location.href="product/list?curPage="+curPage+"&mohu="+name;
		}else if("1"==flag){
			document.getElementById("searchBeforeFormCurrPage").value=curPage;
			searchProduct("1");
		}
	};
	function mohu() {
		var name=document.getElementById("mohu").value;
		location.href="product/list?curPage=1&mohu="+name;
	};
	function selectById(id) {
		location.href="product/selectById?id="+id;
	};
	function deleteById(id,currPage) {
		if(confirm("你确定删除吗?")){
			var name=document.getElementById("mohu").value;
			location.href="product/deleteById?id="+id+"&curPage="+currPage+"&mohuName="+name;
		}
	};
	function add(){
		location.href="product/addPreList";
	};
	function showColor(id){
		$("#product"+id).css("background-color","#FFEEFF");
	};
	function closeColor(id){
		$("#product"+id).css("background-color","#FFFFFF");
	};
	function searchProduct(flag){
		if("1"==flag){//分页查询
			document.getElementById("searchBeforeForm").action="product/searchProduct";
			document.getElementById("searchBeforeForm").submit();
		}else if("0"==flag){//跳转到更多查询页面
			document.getElementById("searchBeforeForm").action="product/searchBefore";
			document.getElementById("searchBeforeForm").submit();
		}
		
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
				一级分类列表 &lt; <a href="back_page/hello.jsp">&nbsp;首页</a>
				</div>
				<!-- 退出登陆部分  -->
			</div>
			<!-- 根据名字模糊查询  部分-->
			<div id="func_box">		
				<div id="select_button">
					<span>请输入要查询的名字:</span>
					<input type="text" name="select" id="mohu" value="${mohu}" />
					<button onclick="mohu()">查询</button>
				</div>
				<div id="selectMore_button">
					<button onclick="searchProduct('0')" id="gdButton">更多查询</button>
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
							<th class="table_thead_col" id="main_table_name">
								名称
							</th>
							<th class="table_thead_col" id="main_table_miaoshu">
								描述
							</th>
							<th class="table_thead_col">
								是否热门
							</th>
							<th class="table_thead_col">
								市场价格
							</th>
							<th class="table_thead_col">
								商城价格
							</th>
							<th class="table_thead_col" id="imageooo">
								样图
							</th>
							<th class="table_thead_col" id="main_table_time">
								时间
							</th>
							<th class="table_thead_col_button" >
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
	                    	<tr id="product${l.id }" onmouseover="showColor('${l.id }')" onmouseout="closeColor('${l.id }')" >
								<td class="table_tr_col_product">${l.id }</td>
								<td class="table_tr_col_product">${l.name }</td>
								<td class="table_tr_col_product">${l.description }</td>
								<td class="table_tr_col_product">${l.isHot=="1"?"是":"否" }</td>
								<td class="table_tr_col_product">${l.marketPrice }</td>
								<td class="table_tr_col_product">${l.shopPrice }</td>
								<td class="table_tr_col_product"><img src="${l.image }" width="60" height="60"/></td>
								<td class="table_tr_col_product"><fmt:formatDate value="${l.time }" pattern="yyyy年MM月dd日HH点mm分" /></td>
								<td class="table_tr_col_product">
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
                    	描述：表尾 
                    -->
					<div id="foot_fenye_div">
						<div id="fenye_div">
							<span>${page.currPage }/${page.countPage }</span>
							<button onclick="fenye('1')" class="fenye_button">首页</button>
							<button onclick="fenye('${page.prePage}')" class="fenye_button">上一页</button>
							<button onclick="fenye('${page.nextPage}')" class="fenye_button">下一页</button>
							<button onclick="fenye('${page.countPage}')" class="fenye_button">尾页</button>
						</div>
					</div>
			</div>
		</div>
		<form action="product/searchBefore" method="post" id="searchBeforeForm">
			<input type="hidden" name="fromLocation" value="0"/>
			<input type="hidden" name="currPage" value="${page.currPage }" id="searchBeforeFormCurrPage"/>
		</form>
		<input type="hidden" value="${fromLocation}" id="frmFlag"/>
	</body>
</html>