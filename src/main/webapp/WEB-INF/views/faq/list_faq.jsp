<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list.jsp</title>
<style type="text/css">
h2{text-align: center;}
table{
margin: auto;
background-color: ivory;
}
</style>
</head>
<body>
 <h2>자주 묻는 질문 (전체글:${count})</h2>
  
  <table width="800">
    <tr>
      <td align="right">
        <c:if test="${mem_id=='admin'}">
          <a href="writeForm_faq.do">질문 등록</a>
        </c:if>
      </td>
    </tr>
  </table>
  
  <c:if test="${count==0}">
    저장된 글이 없습니다
  </c:if>
  
  <!-- 글이 존재 하면 -->
  <c:if test="${count>0}">
    <table width="800">
      <tr bgcolor="#ffb4b4">
        <td>글번호</td>
        <td>글제목</td>
        <td>글쓴이</td>
        <td>작성일</td>
        <td>조회수</td>
        <td>IP</td>
      </tr>
      <c:forEach var="dto" items="${list}">
        <tr>
          <td>
            <c:out value="${number}"/>
            <c:set var="number" value="${number-1}"/>
          </td>
          <!-- 글제목 -->
          <td>
          <!-- 답글 -->
            <c:if test="${dto.re_level_faq>0}">
              <img src="resources/imgs/level.gif" width="${5*dto.re_level_faq}" height="16">
              <img src="resources/imgs/re.gif">
            </c:if>
          
          <!-- 원글 -->
          <c:if test="${dto.re_level_faq==0}">
            <img src="resources/imgs/level.gif" width="${5*dto.re_level_faq}" height="16">
          </c:if>  
          
          <!-- 제목을 클릭하면 내용보기로 간다 -->
          <a href="content_faq.do?num_faq=${dto.num_faq}&pageNum_faq=${pageNum_faq}">
            ${dto.subject_faq}
          </a>
          
          <c:if test="${dto.readcount_faq>20}">
            <img src="resources/imgs/hot.gif" height="16">
          </c:if>
          </td>
          <!-- 글제목  끝-->
          
          <td>${dto.writer_faq}</td>
          <td><fmt:formatDate value="${dto.regdate_faq}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
          <td>${dto.readcount_faq}</td>
          <td>${dto.ip_faq}</td>
        </tr>
      </c:forEach>
    </table>
  </c:if>
  <!-- 글이 존재 하면 끝-->
  <!-- 실행 -->
  
  <!-- 블럭처리,페이지 처리 -->
  <table width="800">
    <tr>
      <td align="center">
        <!-- 에러방지 -->
        <c:if test="${endPage>pageCount}">
          <c:set var="endPage" value="${pageCount}"/>
        </c:if>
        
        <!-- 이전블럭 -->
        <c:if test="${startPage>10}">
          <a href="list_faq.do?pageNum_faq=${startPage-10}">[이전블럭]</a>
        </c:if>
        
        <!-- 페이지 처리 -->
        <c:forEach var="i" begin="${startPage}" end="${endPage}">
          <a href="list_faq.do?pageNum_faq=${i}">[${i}]</a>
        </c:forEach>
        
        <!-- 다음블럭 -->
        <c:if test="endPage<pageCount">
          <a href="list_faq.do?pageNum_faq=${startPage+10}">[다음블럭]</a>
        </c:if>
      </td>
    </tr>
  </table>
</body>
</html>