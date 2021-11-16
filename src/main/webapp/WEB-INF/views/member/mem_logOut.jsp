<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>logOut.jsp</title>
</head>
<body>
	<%--logout.jsp --%>
	굿 바이~~~
	<c:remove var="mem_id" scope="session"/> <%--세션무효화  아이디 제거(로그아웃) --%>
	<!-- <meta http-equiv="Refresh" content="0;url=memboardTiles"/> -->
	<meta http-equiv="Refresh" content="0;url=list_sh.do"/>
	
</body>
</html>