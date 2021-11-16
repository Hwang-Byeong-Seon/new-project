<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list.jsp</title>
</head>

<style type="text/css">
h2{text-align: center;}
table{
margin: auto;
background-color: ivory;
}
</style>

<body>
  <h2>공지사항 (전체글:${count})</h2>
  <table width="800">
    <tr>
      <td align="right">
        <c:if test="${mem_id=='admin'}">
          <a href="writeForm_no.do">공지사항 등록</a>
        </c:if>       
      </td>
    </tr>
  </table>
  
  <c:if test="${count==0}">
       저장된 글이 없습니다
  </c:if>
  
  <c:if test="${count>0}">
    <table width="800" border="1">
      <tr bgcolor="#ffb4b4">
        <td width="57" align="center">번호</td>
        <td width="55%" align="center">제목</td>
        <td width="57" align="center">글쓴이</td>
        <td align="center">작성일</td>
        <td width="57" align="center">조회수</td>
      </tr>
      
      <c:forEach var="dto" items="${list}">
        <tr>
          <td align="center">
            <c:out value="${number}"/>
            <c:set var="number" value="${number-1}"/>
          </td>
          <!-- 글제목 -->
          <td>
          <!-- 답글이면 -->
            <c:if test="${dto.re_level_no>0}">
              <img src="resources/imgs/level.gif" width="${5*dto.re_level_no}" height="16">
              <img src="resources/imgs/re.gif">
            </c:if>
            
          <!-- 원글 -->
            <c:if test="${dto.re_level_no==0}">
              <img src="resources/imgs/level.gif" width="${5*dto.re_level_no}" height="16">
            </c:if>
          <!-- 제목을 클릭하면 내용보기 -->
            <a href="content_no.do?num_no=${dto.num_no}&pageNum=${pageNum}">
              ${dto.subject_no}
            </a>  
            
            <c:if test="${dto.readcount_no>20}">
              <img src="resources/imgs/hot.gif" height="16">
            </c:if>
          </td>
          <!-- 글제목 끝 -->
          
          <td align="center">${dto.writer_no}</td>
          <td align="center"><fmt:formatDate value="${dto.regdate_no}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
          <td align="center">${dto.readcount_no}</td>
        </tr>
      </c:forEach>
    </table>       
  </c:if>
  <!-- 글이 존재하면 끝 -->
  
  <!-- 블럭처리, 페이지 처리 -->
  <table width="800">
    <tr>
      <td align="center">
        <!-- 에러방지 -->
        <c:if test="${endPage>pageCount}">
          <c:set var="endPage" value="${pageCount}"/>
        </c:if>
        
        <!-- 이전 블록 -->
        <c:if test="${startPage>10}">
          <a href="list_no.do?pageNum=${startPage-10}">[이전블럭]</a>
        </c:if>
        
        <!-- 페이지 처리 -->
        <c:forEach var="i" begin="${startPage}" end="${endPage}">
          <a href="list_no.do?pageNum=${i}">[${i}]</a>
        </c:forEach>
        
        <!-- 다음 블럭 -->
        <c:if test="${endPage<pageCount}">
          <a href="list_no.do?pageNum=${startPage+10}">[다음블럭]</a>
        </c:if>
      </td>
    </tr>
  </table>
  
</body>
</html>