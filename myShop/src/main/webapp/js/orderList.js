var divNameArray=new Array("unpaidOrder_div","receiveOfgoodsOrder_div","finishOrder_div");
var nameArray=new Array("unpaidOrder","receiveOfgoodsOrder","finishOrder");
function divVisibility(id){
	for(var i = divNameArray.length-1; i>=0; i--){
		if( divNameArray[i]==id ){
			document.getElementById(nameArray[i]).style.backgroundColor="white";
			document.getElementById(divNameArray[i]).style.visibility="visible";
		}else{
			document.getElementById(nameArray[i]).style.backgroundColor="#EEEEEE";
			document.getElementById(divNameArray[i]).style.visibility="hidden";
		}
	}
}
/**
 * 分页请求 
 **/
function refreshPage(flag,value,localtion){
	document.getElementById(flag).value=value;
	document.getElementById("changeLocaltion").value=localtion;
	document.getElementById("refreshPageForm").submit();
}
/**
 * 加载事件触发，回到上次相应页面 
 **/
function changeLocaltion(localtion){
	if(localtion != 'midy'){
		divVisibility(localtion);
	}
}
/**
 * 订单详情
 **/
function orderItemInfo(orderID){
	document.getElementById("orderUUID").value=orderID;
	document.getElementById("orderItemForm").submit();
}
/**
 * 更改订单状态
 */
function changeOrdSta(orderID){
	if(orderID==null || orderID=="" || orderID=="undefined" ) return;
		$.post(
				"userShop/changeOrderState",
				{"orderId":orderID},
				function(data){
					if(data != null){
						if(data.code == 2){
							document.getElementById("order_total_num").innerText="已付款";
							document.getElementById("order_total_num").onclick=function(){};
						}else if(data.code == 4){
							document.getElementById("order_total_num").innerText="已收货";
							document.getElementById("order_total_num").onclick=function(){};
						}
					}
				},
				"json"
		);
}






















