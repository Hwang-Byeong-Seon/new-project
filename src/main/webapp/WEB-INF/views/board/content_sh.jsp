<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>content.jsp</title>

<style type="text/css">
h2{text-align: center;}
table{
margin: auto;
background-color: ivory;
width:70%}
</style>

</head>
<body>
  <h2>글 내용 보기</h2>
  <table border="1">
    <tr>
      <td>글번호</td>
      <td>${dto.num_sh}</td>
      <td>조회수</td>
      <td>${dto.readcount_sh}</td>
    </tr>
    
    <tr>
      <td>이름</td>
      <td>${dto.writer_sh}</td>
      <td>작성일</td>
      <td>
        <fmt:formatDate value="${dto.regdate_sh}" pattern="yyyy-MM-dd"/>
      </td>
    </tr>
    
    <tr>
      <td>글제목</td>
      <td colspan="3">${dto.subject_sh}</td>
    </tr>
    
    <tr>
      <td>글내용</td>
      <td colspan="3"><pre>${content_sh}</pre></td>
    </tr>
    
    <tr>
      <td colspan="4" align="center">
        <input type="button" value="글수정" onClick="location.href='updateForm_sh.do?num_sh=${num_sh}&pageNum_sh=${pageNum_sh}'">
        <input type="button" value="글삭제" onClick="location.href='delete_sh.do?num_sh=${num_sh}&pageNum_sh=${pageNum_sh}'">
        <input type="button" value="새글쓰기" onClick="location.href='writeForm_sh.do'">
        
        <input type="button" value="답글쓰기" onClick="location.href='writeForm_sh.do?num_sh=${num_sh}&pageNum_sh=${pageNum_sh}&ref_sh=${dto.ref_sh}&re_step_sh=${dto.re_step_sh}&re_level_sh=${dto.re_level_sh}'">
        <input type="button" value="글목록" onClick="location.href='list_sh.do?num_sh=${num_sh}&pageNum_sh=${pageNum_sh}'">
      </td>
    </tr>
    
  </table>
</body>
</html>