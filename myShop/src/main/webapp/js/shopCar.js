/**
 * 全选框点击函数
 **/
function quanxuan(obj){
	var checkedStatus=obj.checked;/*当前选择框点击之后 按钮的状态 true为已选择 false为未选择*/
	var checkedBoxArray=document.getElementsByClassName("order_info_body_select");
	for(var i=checkedBoxArray.length-1; i>=0;i--){
		if(checkedStatus){
			checkedBoxArray[i].checked="checked";
		}else{
			checkedBoxArray[i].checked="";
		}
	}
	totalPrice();
}
/**
 * 数量改变，页面小计，总计值刷新，向后台发起请求！
 **/
function calculatePrice(proID){
	var inputObj = document.getElementById("number"+proID);
	var hiddenObj = document.getElementById("numberChange"+proID);
	var inputObjValue = inputObj.value;
	var hiddenObjValue = hiddenObj.value;
	if( inputObjValue != hiddenObjValue ){//值是否被改变！此处还有添加其他筛选条件如：正整数，值在一定的范围
		if( inputObjValue > 0){
			//当前端值被改变的时候，就向后台发起异步请求,先不管后台，直接先刷新前台数值的变化
			document.getElementById("allPrice"+proID).innerText = Number(inputObj.value)*Number(document.getElementById("perPrice"+proID).innerText);
			totalPrice();
			$.post(
					"userShop/changeToShopCar",
					{"productId":proID,"statu":1,"count":inputObjValue},
					function(data){
						if(data.code == 1){
							//后台执行成功以后，再改变前台，可能造成延迟
							hiddenObj.value = inputObj.value;//请求成功！更新隐藏真实数量的值
						}else{
							inputObj.value = hiddenObj.value;//请求失败！将数量显示框的值还原,保证了前后台数值的一致性(此时,隐藏值和显示值一致)
							//再次刷新页面的值，与后台保持一致
							document.getElementById("allPrice"+proID).innerText = Number(inputObj.value)*Number(document.getElementById("perPrice"+proID).innerText);
							totalPrice();
						}
					},
					"json"
			);
		}else{//将值还原！
				inputObj.value = hiddenObjValue;
		}
	}
}

/**
 *计算总价
 *先获取所有的商品项，然后筛选到被选中的，然后计算出单元商品的价格，再相加 
 **/
function totalPrice(){
	/*
	 *通过className 拿到所有的商品Array，然后逐个判断
	 **/
	var checkedBoxArray = document.getElementsByClassName("order_info_body_select");
	var totalPrice = 0;
	var selectProductNum = 0;
	for(var i = checkedBoxArray.length-1; i>=0; i--){
		if( checkedBoxArray[i].checked ){/*这里面的都是被选中的*/
			selectProductNum +=1;
			var idName = checkedBoxArray[i].id;
			totalPrice += Number( document.getElementById( "number"+idName ).value ) * Number( document.getElementById( "perPrice"+idName ).innerText);
		}
	}
	document.getElementById("totalPrice").innerText = totalPrice;
	document.getElementById("selectProductNum").innerText = selectProductNum;
}
/**
 * 根据ID删除商品项
 * */
function deleteItemById(id){
	document.getElementById("productIdValue").value=id;
	document.getElementById("deleteItemByIdForm").submit();
}
/**
 * 根据ID批量删除商品项
 * */
function deleteItemManyByIdForm(){
	var selectIDStr = "";
	var checkedBoxArray = document.getElementsByClassName("order_info_body_select");
	for(var i = checkedBoxArray.length-1; i>=0; i--){
		if( checkedBoxArray[i].checked ){/*这里面的都是被选中的*/
			selectIDStr +=","+ checkedBoxArray[i].id ;
		}
	}
	if( selectIDStr.length>0 ){
		document.getElementById("productIdValueTwo").value=selectIDStr.substring(1,selectIDStr.length);
		document.getElementById("deleteItemManyByIdForm").submit();
	}else{
		alert("	请选择要删除的商品！");
	}
}
/*
 * 购物车表单提交
 **/
function formSubmit(){
	var productID = "";
	var checkedBoxArray = document.getElementsByClassName("order_info_body_select");
	for(var i = checkedBoxArray.length-1; i>=0; i--){
		if( checkedBoxArray[i].checked ){/*这里面的都是被选中的*/
			productID += ","+checkedBoxArray[i].id;
		}
	}
	alert(productID);
	if( productID.length>0 ){
		document.getElementById("productIdVArray").value=productID.substring(1,productID.length);
		document.getElementById("formSubmitBeforeForm").submit();
	}else{
		alert("您未选择任何商品！");
	}
}