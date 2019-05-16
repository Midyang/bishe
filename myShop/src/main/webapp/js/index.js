			/*显示隐藏块*/
			function xs(id){
				document.getElementById(id).style.visibility = "visible";
				document.getElementById("main_right_bottom").style.visibility = "hidden";
				document.getElementById("imgObj").style.visibility = "hidden";//脱离了文档流，如果不加，会有延迟效果
			}
			/*隐藏隐藏块*/
			function yc(id){
				document.getElementById(id).style.visibility = "hidden";
				document.getElementById("main_right_bottom").style.visibility = "visible";
				document.getElementById("imgObj").style.visibility = "visible";//脱离了文档流，如果不加，会有延迟效果
			}
/*-----------------------图片轮播部分---------------------------------------*/
			/* 暂时这样课用AJAX程序中取值)，保留 */
			var imgArr = new Array("img/t1.jpg","img/t2.jpg","img/t3.jpg","img/t4.jpg","img/t5.jpg");
			/* 暂时这样，保留 */
			var tipArr = new Array("tplb_div_sort1","tplb_div_sort2","tplb_div_sort3","tplb_div_sort4","tplb_div_sort5");
			/* 用来获取img标签对象 */
			function imgObject(){
				return document.getElementById("imgObj");
			}
			/*判断 str1 是以 str2 结尾的吗*/
			function endWith(str1,str2){
				var length = str1.length-str2.length;
				return length >= 0 && str1.lastIndexOf(str2) == length;
			}
			/* 改变图片对应的序号的亮度 */
			function changeTip(id){
				for( var i = tipArr.length-1; i >= 0; i-- ){
					if( i == id ){
						document.getElementById(tipArr[i]).style.opacity = "1";
						document.getElementById(tipArr[i]).style.backgroundColor = "#F0F8FF";
					}else{
						document.getElementById(tipArr[i]).style.opacity = "0.3";
						document.getElementById(tipArr[i]).style.backgroundColor = "";
					}
				}
			}
			/*图片轮播左滑动*/
			function leftMove(){
				var imgObj = imgObject();
				for( var i = imgArr.length-1; i >= 0; i-- ){
					if ( endWith( imgObj.src, imgArr[i] ) ) {/*如果 img标签的属性值等于 当前序号 中的值 */
						if( i == 0 ){/*判断i 等于0吗*/
							imgObj.src = imgArr[imgArr.length-1];
							changeTip(imgArr.length-1);
						}else{/*判断 不小等于0吗*/
							imgObj.src = imgArr[i-1];
							changeTip(i-1);
						}
						return;/*程序终止不继续执行*/
					}
				}
			}
			/*图片轮播 横向导航栏 点击事件*/
			function onclickMove( id ){
				var imgObj = imgObject();
				 for(var i = tipArr.length-1; i >= 0; i--){	 	
				 	if( tipArr[i] == id ){
				 		imgObj.src = imgArr[i];/* img 标签对象 赋值*/
				 		document.getElementById( tipArr[i] ).style.opacity =  1;
						document.getElementById(tipArr[i]).style.backgroundColor = "#F0F8FF";
				 	}else{
				 		document.getElementById( tipArr[i] ).style.opacity = "0.3";
						document.getElementById( tipArr[i] ).style.backgroundColor = "";
				 	}
				}
			}
			/*右滑动*/
			function rightMove(){
				var imgObj = imgObject();
				for( var i=imgArr.length-1; i>=0; i-- ){
					if ( endWith( imgObj.src, imgArr[i] ) ) {/*如果 img标签的属性值等于 当前序号 中的值 */
						if( i == imgArr.length-1 ){/*判断i 等于0吗*/
							imgObj.src = imgArr[0];
							changeTip(0);
						}else{/*判断 不小等于0吗*/
							imgObj.src = imgArr[i+1];
							changeTip(i+1);
						}
						return;/*程序终止不继续执行*/
					}
				}
			}
			//鼠标事件
			function changeColorOnOver(id){
				document.getElementById(id).style.opacity="1";
			}
			
			function changeColorOnOut(id){/*当鼠标移开的时候*/
				document.getElementById(id).style.opacity="0.3";
			}
			/*自动调用，当鼠标移入进图片轮播的时候，就停止自动播放，移出图片轮播的区域的时候，就自动播放*/
			var timeRun=window.setInterval(rightMove,2000);
			
			function clearRun(){
				window.clearInterval(timeRun);
			}
			
			function addRun(){
				timeRun=window.setInterval(rightMove,2000);
			}
/*-------------------------跳转到商品想详情页-----------------------------------------------*/
			function showShopInfo(shopId){
				window.location.href="userShop/shopInfoById?shopId="+shopId;
			}
			function targetShopCarFormSubmit(){
				document.getElementById("targetShopCarForms").submit();
			}