<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="${pageContext.request.contextPath }/">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商城管理系统</title>
	<script type="text/javascript"></script>
</head>
<frameset rows="78,*" ><!--整体  上下分区 -->
		<frame src="back_page/a_head.jsp" noresize="noresize" frameborder="0" scrolling="no" />
		<frameset cols="190,*" ><!--下 - 左右分区 -->
			<frame src="admin/menu" name="left" noresize="noresize" frameborder="0" />
			<frameset rows="*,36" ><!--右 - 上下分区 -->
				<frame src="back_page/hello.jsp" name="mainFrame" frameborder="0" />
				<frame src="back_page/a_footer.jsp" name="footer" noresize="noresize" frameborder="0" scrolling="no"/>
			</frameset>
		</frameset>
</frameset>
</html>