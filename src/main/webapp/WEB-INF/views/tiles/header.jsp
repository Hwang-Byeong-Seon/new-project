<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%--header.jsp --%>

 
<table border="0" style="width:100%; mon-width:700px;">
<tr>
 <td align="left">
 <a href="list_sh.do" style='text-decoration:none;'>
      홈으로
 </a>
 </td>
<!--  <td align="left">
 <a href="/memboardTiles/" style='text-decoration:none;'>
      홈으로(패키지 마지막 이름)=>프로젝트이름=컨텍스트이름
 </a>
 </td> -->
 
 <td align="right">
  <a href="/shop_project/list_sh.do">게시판</a>&nbsp;&nbsp;
  <a href="/shop_project/list_no.do">공지사항</a>&nbsp;&nbsp;
  
  <c:if test="${mem_id==null}">
    <a href="/shop_project/mem_loginForm.do">로그인</a>
  <a href="/shop_project/mem_insertForm.do">회원가입</a>
 </c:if>
 
  <c:if test="${mem_id!=null}">
  	<a href="/shop_project/mem_logOut.do">로그아웃</a>
  </c:if>
 </td>
</table>
 

</body>
</html>