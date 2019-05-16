		function formSubmit(){
			document.getElementById("searchForm").submit();
		}
		function pageSearch(currPage){
			document.getElementById("currentPages").value=currPage;
			formSubmit();
		}
		function changeTwoClassStatus(twoClassId){
			var inptScidObj = document.getElementById("scid");//用于要传到后台的数据的存放处
			var liID = "TwoClass"+twoClassId;//当前要操作的，li 对象的ID
			var inptLiIDObj=document.getElementById(liID);//当前要操作的，li 对象
			var lastTwoClassObj= document.getElementById("lastTwoClass");//记录上一个操作li对象的ID
			if( inptScidObj.value==twoClassId ){//如果两个值相等，则当前li对象是被选中的状态
				inptScidObj.value=0;//将记录上传对象归零
				inptLiIDObj.style.backgroundColor="#FEFEFE";//改变当前li对象的未选中状态
			}else{//当前状态为：未选中
				if( lastTwoClassObj.value != "0" ){//如果上一个操作对象处存在，则先改变上一个操作对象的状态为未选中
					document.getElementById(lastTwoClassObj.value).style.backgroundColor="#FEFEFE";
				}
				inptScidObj.value=twoClassId;//将记录对象的值设置为当前要改变为选中的对象的商品ID
				inptLiIDObj.style.backgroundColor="#B8D4EE";//改变当前li对象的状态为选中状态
				lastTwoClassObj.value=liID;//将上一个修改状态的li对象的id保存起来
			}
			
		}
		function showShopInfo(shopId){
			window.location.href="userShop/shopInfoById?shopId="+shopId;
		}
		function load(){
			var inptScidObj= document.getElementById("scid");
			if( inptScidObj.value != 0 ){//存在被选中的li时，设置那个li为显示状态
				var liID="TwoClass"+inptScidObj.value;
				document.getElementById(liID).style.backgroundColor="#B8D4EE";
				document.getElementById("lastTwoClass").value=liID;
			}
		}
		function shopCarIndexSubmit(){
			document.getElementById("shopCarIndexForm").submit();
		}