<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>content.jsp</title>

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
 <h2>글 내용 보기</h2>

  <table border="1">
    <tr>
       <td>글번호</td>
       <td>${dto.q_num}</td>
       <td>조회수</td>
       <td>${dto.q_readcount}</td>
    </tr>
    
    <tr>
      <td>이름</td>
      <td>${dto.q_writer}</td>
      <td>작성일</td>
      <td>
      <fmt:formatDate value="${dto.q_regdate}" pattern="yyyy-MM-dd"/>
      </td>
    </tr>
    
    <tr> 
      <td>글제목</td>
      <td colspan="3">${dto.q_subject}</td>
    </tr>
    
    <tr>
      <td>글내용</td>
      
      <td colspan="3"><pre>${q_content}</pre></td>
     
    </tr>
    
    <tr>
      <td colspan="4" align="center">
        <input type="button" value="글수정" onClick="location.href='qna_updateForm.do?q_num=${q_num}&pageNum=${pageNum}'">
        <input type="button" value="글삭제" onClick="location.href='qna_delete.do?q_num=${q_num}&pageNum=${pageNum}'">
        <input type="button" value="새글쓰기" onClick="location.href='qna_writeForm.do'">

        <c:if test="${id=='admin' && pw=='admin'}">
         <input type="button" value="답글쓰기" onClick="location.href='qna_writeForm.do?q_num=${q_num}&pageNum=${pageNum}&q_ref=${dto.q_ref}&q_re_step=${dto.q_re_step}&q_re_level=${dto.q_re_level}'">
        </c:if>
        <input type="button" value="글목록" onClick="location.href='qna_list.do?q_num=${q_num}&pageNum=${pageNum}'">
         
      </td>
    </tr>
    
    
    
    
  </table>
</body>
</html>