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
<%--side.jsp --%>

<!-- <a href="/memboardTiles/list.do">게시판</a><br> -->
  <c:if test="${mem_id==null}">
    <a href="/shop_project/mem_loginForm.do">로그인</a><br>
    <a href="/shop_project/mem_insertForm.do">회원가입</a><br>
  </c:if>
 
  <c:if test="${mem_id!=null}">
  	<a href="/shop_project/mem_logOut.do">로그아웃</a><br>
  </c:if>
   <a href="/shop_project/list_no.do">공지사항</a><br>
   <a href="/shop_project/list_faq.do">FAQ게시판</a><br>
   <a href="/shop_project/qna_list.do">qna게시판</a><br>
</body>
</html>