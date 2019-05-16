function orderInfoSubmit(){
	var phoneNO = document.getElementById("phoneNO").value;
	var userName = document.getElementById("userName").value;
	var userAddr = document.getElementById("userAddr").value;
	var payMethod = document.getElementById("payMethod").value;
	$.post(
			"userShop/addOrder",
			{"phoneNO":phoneNO,"userName":userName,"userAddr":userAddr,"payMethod":payMethod},
			function(data){
				if(data.code == "s"){
					alert("已下单成功！为您跳转到订单列表...")
					location.href="userShop/orderInfo";
				}else{
					alert("下单失败！");
					switch(data.entity.code){
						case "f0":
							alert(data.entity.msg);
							location.href="userShop/addToShopCar";
							break;
						case "f1":
							changeShow("errphoneNO",data.entity.msg);
							break;
						case "f2":
							changeShow("erruserName",data.entity.msg);
							break;
						case "f3":
							changeShow("erruserAddr",data.entity.msg);
							break;
						case "f4":
							changeShow("errpayMethod",data.entity.msg);
							break;
						case "ff":	//保存户数的时候，异常
							changeShow("","");
							alert(data.entity.msg);
							break;
						default:alert(data.entity.msg);
					}
				}
			},
			"json"
	);
}
var errBoxArray=new Array("errphoneNO","erruserName","erruserAddr","errpayMethod");
function changeShow(name,value){
	for(var i=0;i<errBoxArray.length;i++){
		if(errBoxArray[i]==name){
			document.getElementById(errBoxArray[i]).innerHTML=value;
		}else{
			document.getElementById(errBoxArray[i]).innerHTML="";
		}
	}
}
