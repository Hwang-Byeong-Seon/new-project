<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>content.jsp</title>
</head>
<style type="text/css">
h2{text-align: center;}
table{
margin: auto;
width:70%;
}
</style>
<body bgcolor="ivory">
  <table>
    <tr>
      <td colspan="6" align="left"">
        <font size="6">공지사항</font>
        <hr size="5" noshade color="black">
      </td>  
    </tr>

    <tr>
      <td colspan="6" align="center"><font size="5">${dto.subject_no}</font><hr noshade></td>
      
    </tr>
    
    <tr>
      <td width="57">이름</td>
      <td width="70"><font color="gray">${dto.writer_no}</font></td>
      <td width="57">조회수</td>
      <td width="57"><font color="gray">${dto.readcount_no}</font></td>
      <td width="57">작성일</td>
      <td>
        <font color="gray">
          <fmt:formatDate value="${dto.regdate_no}" pattern="yyyy-MM-dd"/>
        </font>
      </td>
    </tr>
    <tr>
      <td colspan="6">
        <hr noshade>
      </td>
    </tr>
  </table>
  
  <table>
 
    <tr>
      <td colspan="3"><pre>${dto.content_no}</pre></td>
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
      <td align="center">
        <c:if test="${mem_id=='admin'}">
          <input type="button" value="수정" onClick="location.href='updateForm_no.do?num_no=${num_no}&pageNum=${pageNum}'">
          <input type="button" value="삭제" onClick="location.href='delete_no.do?num_no=${num_no}&pageNum=${pageNum}'">
          <input type="button" value="공지사항등록" onClick="location.href='writeForm_no.do'">
          <input type="button" value="추가등록" onClick="location.href='writeForm_no.do?num_no=${num_no}&pageNum=${pageNum}&ref_no=${dto.ref_no}&re_step_no=${dto.re_step_no}&re_level_no=${dto.re_level_no}'">
        </c:if>
          <input type="button" value="글목록" onClick="location.href='list_no.do?num=${num}&pageNum=${pageNum}'">
      </td>
    </tr>
  </table>
</body>
</html>