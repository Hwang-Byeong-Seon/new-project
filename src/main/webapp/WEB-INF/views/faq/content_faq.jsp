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
      <td>${dto.num_faq}</td>
      <td>조회수</td>
      <td>${dto.readcount_faq}</td>
    </tr>
    
    <tr>
      <td>이름</td>
      <td>${dto.writer_faq}</td>
      <td>작성일</td>
      <td>
        <fmt:formatDate value="${dto.regdate_faq}" pattern="yyyy-MM-dd"/>
      </td>
    </tr>
    
    <tr>
      <td>글제목</td>
      <td colspan="3">${dto.subject_faq}</td>
    </tr>
    
    <tr>
      <td>글내용</td>
      <td colspan="3"><pre>${content_faq}</pre></td>
    </tr>
   
    <tr>
      <c:if test="${dto.fileName!=null}">
        <td>첨부파일</td>
	    <td align="left">
	      <a href="fileDownload.do?fileName=${dto.fileName}">${dto.fileName}</a>
	    </td>
      </c:if>
    </tr>
    
    <tr>
      <c:if test="${dto.fileName2!=null}">
        <td>첨부파일</td>
	    <td align="left">
	      <a href="fileDownload2.do?fileName2=${dto.fileName2}">${dto.fileName2}</a>
	    </td>
      </c:if>
    </tr>
    
    <tr>
      <c:if test="${dto.fileName3!=null}">
        <td>첨부파일</td>
	    <td align="left">
	      <a href="fileDownload3.do?fileName3=${dto.fileName3}">${dto.fileName3}</a>
	    </td>
      </c:if>
    </tr>
  
    <tr>
      <td colspan="4" align="center">
        <c:if test="${mem_id=='admin'}">
          <input type="button" value="글수정" onClick="location.href='updateForm_faq.do?num_faq=${num_faq}&pageNum_faq=${pageNum_faq}'">
          <input type="button" value="글삭제" onClick="location.href='delete_faq.do?num_faq=${num_faq}&pageNum_faq=${pageNum_faq}'">
          <input type="button" value="새글쓰기" onClick="location.href='writeForm_faq.do'">
          <input type="button" value="답글쓰기" onClick="location.href='writeForm_faq.do?num_faq=${num_faq}&pageNum_faq=${pageNum_faq}&ref_faq=${dto.ref_faq}&re_step_faq=${dto.re_step_faq}&re_level_faq=${dto.re_level_faq}'">
        </c:if>
          <input type="button" value="글목록" onClick="location.href='list_faq.do?num_faq=${num_faq}&pageNum_faq=${pageNum_faq}'">
      </td>
    </tr>
  
  </table>
</body>
</html>