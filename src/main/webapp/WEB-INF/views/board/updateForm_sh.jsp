<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>updateForm.jsp</title>

<style type="text/css">
h2{text-align: center;}
table{
margin: auto;
background-color: ivory;
width:70%}
</style>

</head>
<body>
  <h2>글 수정 폼</h2>
  <form method="post" action="updatePro_sh.do">
    <table border="1">
      <tr>
        <td>이름</td>
        <td>
          <input type="text" name="writer_sh" value="${dto.writer_sh}" size="30">
          <input type="hidden" name="num_sh" value="${dto.num_sh}">
        </td>
      </tr>
      
      <tr>
        <td>글제목</td>
        <td>
          <input type="text" name="subject_sh" value="${dto.subject_sh}" size="40">
        </td>
      </tr>
      
      <tr>
        <td>글내용</td>
        <td>
          <textarea name="content_sh" rows="10" cols="50">${dto.content_sh}</textarea>
        </td>
      </tr>
      
      <tr>
        <td>암호</td>
        <td>
          <input type="password" name="pw_sh" size="15">
        </td>
      </tr>
      
      <tr>
        <td colspan="2" align="center">
          <input type="submit" value="글수정">&nbsp;
          <input type="reset" value="다시쓰기">&nbsp;
          <input type="button" value="글목록" onClick="location.href='list_sh.do?pageNum_sh=${pageNum_sh}'">
        </td>
      </tr>
      
    </table>
  </form>
</body>
</html>