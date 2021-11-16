<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>loginSuccess.jsp</title>
<script type="text/javascript">

	function mem_update() {
		document.mem_updateForm.action="mem_UpdateForm.do";//내정보 수정
		document.mem_updateForm.submit();//서버로 전송
	}
	
	function mem_delete() {
		document.mem_delForm.action="mem_delete.do";//탈퇴
		document.mem_delForm.submit();
	}
	
	if(self.name != 'reload'){
		self.name='reload';
		self.location.reload(true);
		
	}
	else self.name='';
	
	
	
</script>

</head>
<body>
	<%-- --%>
	<h2>${mdto.mem_name}님 저희 홈페이지에 오신것을 환영합니다.</h2>
	<c:set var="mem_id" value="${mdto.mem_id}" scope="session"></c:set> <%--jsp 로그인 세션등록과 같아 --%>
	<c:set var="pw" value="${mdto.mem_pw}" scope="session"></c:set>
	
		<!-- <a href="list.do">게시판 목록</a>
 	-->
	<a href="javascript:mem_update()">내 정보 수정</a>&nbsp;
	<a href="javascript:mem_delete()">탈퇴</a>&nbsp;
	<a href="mem_logOut.do">로그아웃</a>
	
	<form name="mem_updateForm" method="post">
		<input type="hidden" name="mem_id" value="${mem_id}">
	</form>
	
	<form name="mem_delForm" method="post">
		<input type="hidden" name="mem_id" value="${mem_id}">
	</form>
	
	
	
	
</body>
</html>