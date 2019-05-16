<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.contextPath }/">
<link rel="stylesheet" href="css/treecss/dtree.css" />
<link rel="stylesheet" href="css/treecss/left.css" /> 
<script type="text/javascript" src="js/dtree.js" ></script>
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<title>Insert title here</title>
</head>
<body>
		<div class="dtree">
			<a href="javascript:d.openAll();">展开所有</a>|<a href="javascript:d.closeAll()">关闭所有</a>
			<script type="text/javascript">
				/*描述：跟路径 (当前节点代号,  父级节点(根 的父亲为-1),  显示的名称,  要跳转的路径 , , 在哪个frameset中显示 ) -->
				 不用跳转的 就只写前三项*/
				var menus=${menu};
				d=new dTree("d");
				d.add('1',-1,'系统菜单');
				if(menus == null || menus == "" || menus == "undefined"){//没有权限或其他情况：待处理，暂时保留
					document.write(d);
				}else{
					for(var num=0;num<menus.length;num++){
						var itemMap=menus[num];
						var parent = itemMap.parent;
						d.add(parent.menuId,parent.parentMenuId,parent.menuName);
						var children = itemMap.children;
						for(var numSub=0;numSub<children.length;numSub++){
							var childrenMenu=children[numSub];
							d.add(childrenMenu.menuId,childrenMenu.parentMenuId,childrenMenu.menuName,childrenMenu.menuURL,'','mainFrame');
						}
					}
					 document.write(d);
				 }
			</script>
		</div>
</body>
</html>