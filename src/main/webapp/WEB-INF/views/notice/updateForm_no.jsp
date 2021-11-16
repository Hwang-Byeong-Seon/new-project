<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>updateForm.jsp</title>
</head>
<style type="text/css">
h2{text-align: center;}
table{
margin: auto;
background-color: ivory;
width:70%;
}
</style>
<body>
  <h2>공지사항 수정</h2>
  <form method="post" action="updatePro_no.do">
    <table border="1">
      <tr>
        <td>이름</td>
        <td>
          <input type="text" name="writer_no" value="${dto.writer_no}" size="30" readonly>
          <input type="hidden" name="num_no" value="${dto.num_no}">
        </td>
      </tr>
      
      <tr>
        <td>글제목</td>
        <td><input type="text" name="subject_no" value="${dto.subject_no}" size="40"></td>
      </tr>
      
      <tr>
        <td>글내용</td>
        <td>
          <textarea name="content_no" rows="10" cols="50">${dto.content_no}</textarea>
        </td>
      </tr>
      
      <tr>
        <td colspan="2" align="center">
          <input type="submit" value="글수정">&nbsp;
          <input type="reset" value="다시쓰기">&nbsp;
          <input type="button" value="목록" onClick="location.href='list_no.do?pageNum=${pageNum}'">&nbsp;
        </td>
      </tr>
      
    </table>
  </form>
</body>
</html>