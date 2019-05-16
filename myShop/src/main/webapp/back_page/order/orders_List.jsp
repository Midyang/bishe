<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.contextPath }/">
<link rel="stylesheet" href="css/list.css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	function fenye(currPage){
		var flag = document.getElementById("frmFlag").value;//来源于哪个服务的标志 0：普通查询 1：跟多查询
		if("0"==flag){//简单查询
			var mohuName=document.getElementById("mohu").value;
			var orderState=$("input[type='radio']:checked").val();
			location.href="orders/list?currPage="+currPage+"&mohuName="+mohuName+"&orderState="+orderState;
		}else if("1"==flag){//复杂查询
			document.getElementById("searchBeforeFormCurrPage").value=currPage;//更新查询页
			searchOrder("1");
		}
	};
	function mohu() {
		var mohuName=document.getElementById("mohu").value;
		var orderState=$("input[type='radio']:checked").val();
		location.href="orders/list?currPage=1&mohuName="+mohuName+"&orderState="+orderState;
	};
	function selectById(id) {
		location.href="orders/selectById?id="+id;
	};
	function updateStateById(id){
		var button_id="#"+id;
		if(confirm("你确定修改订单号为:"+id+"的订单状态吗?")){
			$.post(
					"orders/updateStateById",
					{"id":id},
					function(data){
						if(data.code == 1){
							$(button_id).val(data.msg);
						}else{
							alert(data.msg);
						}
					},
					"json"
			);
		}
	};
	function showColor(id){
		$("#order"+id).css("background-color","#FFEEFF");
	};
	function closeColor(id){
		$("#order"+id).css("background-color","#FFFFFF");
	};
	function searchOrder(flag){//根据参数 请求 不同的服务
		if("1"==flag){//进行查询
			document.getElementById("searchBeforeForm").action="orders/searchOrder";
			document.getElementById("searchBeforeForm").submit();
		}else if("0"==flag){//跳转到更多查询页面
			document.getElementById("searchBeforeForm").action="orders/searchOrderBefore";
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
				订单列表 &lt; <a href="back_page/hello.jsp">&nbsp;首页</a>
				</div>
			</div>
			<!-- 根据名字模糊查询  部分-->
			<div id="func_box">		
				<div id="select_button">
					<span>请输入要查询的名字:</span>
					<input type="text" name="select" id="mohu" value="${mohu }" />
					<button onclick="mohu()">查询</button>
				</div>
				<div id="selectMore_button">
					<button onclick="searchOrder('0')" id="gdButton">更多查询</button>
				</div>
			</div>
			<!-- 表格主体   部分-->
			<div id="main">
				<!-- 添加按钮   -->
				<div id="add_button">
					<!-- 分组查询  未付款 未发货 已发货 已收货 -->
					<div id="list_select">
						<input type="radio" name="list_state" value="1" ${list_state=="1"?"checked='checked'":"" } />未付款 
						<input type="radio" name="list_state" value="2" ${list_state=="2"?"checked='checked'":"" } />未发货 
						<input type="radio" name="list_state" value="3" ${list_state=="3"?"checked='checked'":"" } />已发货 
						<input type="radio" name="list_state" value="4" ${list_state=="4"?"checked='checked'":"" } />已收货 
						<input type="radio" name="list_state" value="5" ${list_state=="5"?"checked='checked'":"" } />完成订单
					</div>
				</div>
				
				<div id="table_div">
					<!-- 表格 -->
					<table id="main_table"  cellpadding="0" cellspacing="0">
						<!-- 表格头 -->
						<tr id="main_table_thead">
							<th class="table_thead_col">
								订单号
							</th>
							<th class="table_thead_col">
								用户
							</th>
							<th class="table_thead_col">
								电话
							</th>
							<th class="table_thead_col">
								地址
							</th>
							<th class="table_thead_col">
								总计
							</th>
							<th class="table_thead_col">
								下单时间
							</th>
							<th class="table_thead_col">
								状态
							</th>
							<th class="table_thead_col_button">
								详情
							</th>
						</tr>
						<!--
						描述：表格内容部分
						 -->
	                    <c:forEach items="${list }" var="l">
	                    	<tr id="order${l.id }" onmouseover="showColor('${l.id }')" onmouseout="closeColor('${l.id }')">
								<td class="table_tr_col">${l.id }</td>
								<td class="table_tr_col">${l.name }</td>
								<td class="table_tr_col">${l.phone }</td>
								<td class="table_tr_col">${l.addr }</td>
								<td class="table_tr_col">${l.total }</td>
								<td class="table_tr_col"><fmt:formatDate value="${l.ordertime  }" pattern="yyyy年MM月dd日HH点mm分" /></td>
								<td id="list_state_button_col">
									<input type="button" value="${l.state_name }" onclick="updateStateById('${l.id}')" id="${l.id}" class="list_state_button" />
								</td>
								<td class="table_tr_col">
									<button onclick="selectById('${l.id}')" class="table_tr_col_button">详情</button>
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
		<form action="orders/searchOrderBefore" method="post" id="searchBeforeForm">
			<input type="hidden" value="0" name="fromLocation"/>
			<input type="hidden" name="currPage" value="${data.currPage}" id="searchBeforeFormCurrPage"/>
		</form>
		<input type="hidden" value="${fromLocation}" id="frmFlag"/>
	</body>
</html>