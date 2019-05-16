<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.contextPath }/">
<link rel="stylesheet" href="css/classTwo_Add_Update.css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	function returnPage(){
		location.href="secondcategory/list";
	};
	function verify(){
		var name=document.getElementById("name").value.trim();
		if(name.length==0 || name.length>20){
			$("#err_name").html("必选字段! 不能超过20个字符");
			return false;
		}else{
			$("#err_name").html("");
			return true;
		}
	};
</script>
<title>Insert title here</title>
</head>
	<body>
		<!-- 开始 -->
		<div id="start">
			<!-- 标题  -->
			<div id="head">
				<!-- 标题**导航部分  -->
				<div id="head_text">
				编辑二级分类 &lt; <a href="SecondcategoryServlet?m=getList"> 二级分类列表 </a>  &lt; <a href="back_page/hello.jsp">&nbsp;首页</a>
				</div>
			</div>
			<!-- 表格-->
			<div id="main">
					<!-- 要提交的表单-->
					<form action="secondcategory/update" method="post" onsubmit="return verify()">
						<!-- 表格-->
						<table id="main_table" border="0" cellpadding="0" cellspacing="0">
							<!--第一行-->
							<tr id="table_tr">
								<!--添加二级分类名称-->
								<td  class="title_tr_td">二级分类名称:</td>
								<td >
									<input type="hidden" name="id" value="${second.id }"/>
									<input type="hidden" name="oldName" value="${second.name }"/>
									<input type="hidden" name="oldCid" value="${second.cid }"/>
									<input type="text" name="name" class="content_table" value="${second.name }" id="name"/>
								</td>
								<!--选择所属一级分类-->
								<td  class="title_tr_td">所属的一级分类:</td>
								<td class="title_tr_td">
									<select name="cid" class="content_table">
										<c:forEach items="${list }" var="l">
											<option value="${l.id }" ${l.id==second.cid?"selected='selected'":"" }>${l.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr id="err_tr">
								<td colspan="4">
									<font color="red" id="err_name">
									${message.msg }
									</font>
								</td>
							</tr>
							<!--第二行 功能按钮-->
							<tr>
								<td colspan="4" >
									<div id="func_button_div">
										<input type="reset" value="重置" class="func_button"/>
										<input type="submit" value="提交" class="func_button"/>
										<input type="button" onclick="returnPage();" value="返回" class="func_button"/>
									</div>
								</td>
							</tr>
						</table>
					</form>
			<!-- 表格div-->
			</div>
		<!--主体div -->
		</div>
	</body>
</html>