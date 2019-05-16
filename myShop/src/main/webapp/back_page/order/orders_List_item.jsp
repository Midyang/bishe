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
	function back_prePage(){
		document.location.href="orders/list";
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
				订单详情 &lt; <a href="OrderServlet?m=getList">&nbsp;订单列表</a> &lt; <a href="back_page/hello.jsp">&nbsp;首页</a>
				</div>
				<!-- 退出登陆部分  -->
			</div>
			<!-- 表格主体   部分-->
			<div id="main">
				<!-- 添加按钮   -->
				<div id="table_div">
					<!-- 表格 -->
					<table id="main_table"  cellpadding="0" cellspacing="0">
						<!-- 表格头 -->
						<tr id="main_table_thead">
							<th class="table_thead_col">
								ID
							</th>
							<th class="table_thead_col">
								商品
							</th>
							<th class="table_thead_col">
								数量
							</th>
							<th class="table_thead_col">
								单价
							</th>
							<th class="table_thead_col">
								小计
							</th>
						</tr>
						<!--
						描述：表格内容部分
						 -->
	                    <c:forEach items="${list }" var="l">
	                    	<tr id="main_table_thead_bg">
								<td class="table_tr_col">${l.id }</td>
								<td class="table_tr_col">${l.product.name }</td>
								<td class="table_tr_col">${l.count }</td>
								<td class="table_tr_col">${l.product.shopPrice }</td>
								<td class="table_tr_col">${l.subtotal }</td>
							</tr>
	                    </c:forEach>
	                    <tr id="main_table_thead_info2">
	                    	<td colspan="5" class="table_thead_col2" id="table_thead_col3">
	                    		<span>-----------订-----------单-----------详-----------情-----------页-----------</span>
	                    	</td>
						</tr>
	                    <tr class="main_table_thead_info" id="main_table_thead_infoId">
							<td class="table_thead_col2" colspan="2">
								订单号 : ${order.id }
							</td>
							<td class="table_thead_col2" colspan="2">
								下单时间 : <fmt:formatDate value="${order.ordertime }" pattern="yyyy年MM月dd日HH点mm分" />
							</td>
							<td class="table_thead_col2" colspan="1">
								总计 : ${order.total }
							</td>
						</tr>
						<tr class="main_table_thead_info">
							<td class="table_thead_col2" colspan="3">
								手机号 :  ${order.phone }
							</td>
							<td class="table_thead_col2" colspan="2">
								姓名 :  ${order.name }
							</td>
						</tr>
						<tr class="main_table_thead_info">
							<td class="table_thead_col2" colspan="5">
								收货地址 :  ${order.addr }
							</td>
						</tr>
					</table>
				</div>
					<!--
                    	描述：表尾 
                    -->
					<div id="foot_fenye_div">
						<div id="back_prePage_div">
							<button onclick="back_prePage()" id="back_prePage_button">返回上一页</button>
						</div>
					</div>
			</div>
		</div>
	</body>
</html>