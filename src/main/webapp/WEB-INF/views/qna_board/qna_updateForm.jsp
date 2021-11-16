<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>updateForm.jsp</title>

<style type="text/css">
 h2{text-align:center;}
 table{
 margin:auto; 
 background-color:ivory;
 width:70%;
 }
</style>
</head>
<body>
	<h2>글 수정 폼</h2>
	<form method="post" action="qna_updatePro.do">
		<table border="1">
			 <tr>
        		<td>이름</td>
        		<td>
        		<input type="text" name="q_writer" value="${dto.q_writer}" size="30">
        		<input type="hidden" name="q_num" value="${dto.q_num}">
        		</td>
      		</tr>
      
     		<tr>
       			<td>글제목</td>
       			<td><input type="text" name="q_subject" value="${dto.q_subject}" size="40"></td>
       		</tr>
       		
       		<tr>
       			<td>글 내용</td>
       			<td>
       			<textarea  name="q_content" rows="10" cols="50">${dto.q_content}</textarea>
       			</td>
       		</tr>
       		
       		<tr>
       			<td>암호</td>
       			<td><input type="password" name="q_pw" size="15"></td>
       		</tr>
       		
       		<tr>
       			<td colspan="2" align="center">
       			<input type="submit" value="글수정">&nbsp;
       			<input type="reset" value="다시쓰기">&nbsp;
       			<input type="button" value="글 목록" onclick="location.href='qna_list.do?pageNum=${pageNum}'">
       		</tr>
       	</table>
	</form>

</body>
</html>