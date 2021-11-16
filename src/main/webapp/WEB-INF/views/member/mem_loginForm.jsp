<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>loginForm.jsp</title>
<style type="text/css">
	h2{text-align: center;}
	table{
		margin: auto;
		background-color: ivory;
	}
</style>

<script type="text/javascript">
	function check() {
		if(document.logForm.mem_id.value==''){
			alert('ID를 입력하세요.');
			document.logForm.mem_id.focus();
			return false;
		}
		
		if(document.logForm.mem_pw.value==''){
			alert('암호를 입력하세요.');
			document.logForm.mem_pw.focus();
			return false;
		}
		return true;
	}	
</script>

</head>
<body>
<c:if test="${!msg.equals(null)}">
	<font color="red">
		<center>${msg}</center>
	</font>
</c:if>

<h2>로그인 폼</h2>
	<form name="mem_logForm" method="post" action="mem_loginPro.do" onsubmit="return check()">
		<table border="1">
			<tr>
				<td>ID</td>
				<td>
					<input type="text" name="mem_id" id="mem_id" size="20">
				</td>
			</tr>
			
			<tr>
				<td>암호</td>
				<td>
					<input type="password" name="mem_pw" id="mem_pw" size="20">
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="로그인">
					<input type="reset" value="다시입력">					
				</td>			
			</tr>
		
		</table>
	
	</form>


</body>
</html>