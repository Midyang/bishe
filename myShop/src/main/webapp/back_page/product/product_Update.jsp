<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.contextPath }/"/>
<link rel="stylesheet" href="css/product_Add_Update.css" />
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	function returnPage(){
		location.href="product/list";
	};
	function verify(){

		var name=$("#name").val().trim();
		if(name.length==0 || name.length>20){
			$("#err_name").html("必选字段!");
			return false;
		}
		$("#err_name").html("");
		
		var market_price=$("#market_price").val().trim();
		if(!/^[+]?\d+(\.\d+)?$/.test(market_price)){
			$("#err_market").html("必选字段! 请输入有效数值!");
			return false;
		}
		$("#err_market").html("");
		
		var shop_price=$("#shop_price").val().trim();
		if(!/^[+]?\d+(\.\d+)?$/.test(shop_price)){
			$("#err_shop").html("必选字段! 请输入有效数值!");
			return false;
		}
		$("#err_shop").html("");
		
		var photoName=$("#upload_file").val();
		if(!photoName==""){
			var str=photoName.substring(photoName.lastIndexOf(".")+1);
			if(str=="jpg" || str=="png"){
				return true;
			}else{
				$("#err_photo").html("*上传的文件只能是 jpg 和 png 格式");
				return false;
			}
		}else{
			return true;
		}
		return true;
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
				编辑商品  &lt; <a href="product/list"> 二级分类列表 </a>  &lt; <a href="back_page/hello.jsp">&nbsp;首页</a>
				</div>
				<!-- 退出登陆部分  -->
			</div>
			<!-- 表格-->
			<div id="main">
					<!-- 要提交的表单-->
					<form action="product/update" method="post" enctype="multipart/form-data" onsubmit="return verify()">
						<!-- 表格-->
						<table id="main_table" border="0" cellpadding="0" cellspacing="0">
							<!--第一行-->
							<tr id="table_tr">
								<!--输入商品名-->
								<td  class="title_name">商品名称:</td>
								<td class="text_input_td">
									<input type="hidden" name="id" value="${message.entity.id }"/>
									<input type="hidden" name="image" value="${message.entity.image }"/>
									<input type="text" name="name" value="${message.entity.name }" class="text_input" id="name"/>
								</td>
								<!--输入是否热门-->
								<td class="title_name">是否热门:</td>
								<td>
									<select name="isHot" class="table_select">
										<option value="1" ${(message.entity.isHot=="1")?"selected='selected'":"" }>是</option>
										<option value="0" ${(message.entity.isHot=="0")?"selected='selected'":"" }>不是</option>
									</select>
								</td>
							</tr>
							<tr class="err_row">
								<td colspan="4">
									<font color="red" id="err_name">
										${message.map["nameMsg"] }
									</font>
								</td>
							</tr>
							<!--第二行-->
							<tr>
								<!--输入市场价格-->
								<td class="title_name">市场价格:</td>
								<td class="text_input_td">
									<input type="text" name="marketPrice"  value="${message.entity.marketPrice }" class="text_input" id="market_price"/>
								</td>
								<!--输入商城价格-->
								<td class="title_name">商城价格:</td>
								<td class="text_input_td">
									<input type="text" name="shopPrice"  value="${message.entity.shopPrice }" class="text_input" id="shop_price"/>
								</td>
							</tr>
							<tr class="err_row">
								<td colspan="2">
									<font color="red" id="err_market">
										${message.map["marketPriceMsg"] }
									</font>
								</td>
								<td colspan="2">
									<font color="red" id="err_shop">
										${message.map["shopPriceMsg"] }
									</font>
								</td>
							</tr>
							<!--第三行-->
							<tr>
								<!--上传图片-->
								<td class="title_name">商品图片:</td>
								<td colspan="3">
									<img src="${message.entity.image }" width="80" height="100"/>
									<input type="file" name="images"  id="upload_file"/>
									<font color="red" id="err_photo">
										${message.map["imageMsg"] }
									</font>
								</td>
							</tr>
							<!--第四行-->
							<tr>
								<!--选择所属分类-->
								<td class="title_name">所属的二级分类:</td>
								<td colspan="3">
									<select name="scid" class="table_select">
										<c:forEach items="${message.list }" var="l">
											<option value="${l.id }" ${message.entity.scid==l.id?"selected='selected'":"" }>${l.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<!--第五行-->
							<tr id="">	
								<!--输入商品描述信息-->
								<td class="title_name" >商品描述:</td>
								<td colspan="3">
									<textarea name="description" id="table_textarea">${message.entity.description}</textarea>
								</td>
							</tr>
							<!--第六行 功能按钮-->
							<tr>
								<td colspan="4" >
									<div id="func_button_div">
										<input type="reset" value="重置" class="func_button"/>
										<input type="submit" value="提交" class="func_button"/>
										<input type="button" onclick="returnPage()" value="返回" class="func_button"/>
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