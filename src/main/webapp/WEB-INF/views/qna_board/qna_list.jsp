<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 

<fmt:requestEncoding value="utf-8"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list1s.jsp</title>

<style type="text/css">
 body{background-color:lightgray;}
 h2{text-align:center;}
 table{margin:auto; line-height:30px;background-color:ivory;}
</style>

</head>
<body>
  <h2>글목록(전체글):${count}</h2>
  
  <table width="800">
    <tr>
     <td align="right" bgcolor="#FFB4B4">
       <font color="blue">
         <a href="qna_writeForm.do">글쓰기</a>
       </font>
     </td>
    </tr>
   </table>
   
    <c:if test="${count==0}">
      <table width="800">
        <tr>
         <td align="center">
             게시판에 저장된 글이 없습니다 
         </td>
        </tr>
      </table>
    </c:if>
    
    <c:if test="${count>0}">
      <table width="800">
       <tr bgcolor="#FFB4B4">
         <td>글번호</td>
         <td>글제목</td>
         <td>작성자</td>
         <td>작성일</td>
         <td>조횟수</td>
         <td>IP</td>
       </tr>
       
       <c:forEach var="dto" items="${list}">
         <tr>
          <td>
           <c:out value="${number}"/>
           <c:set var="number" value="${number-1}"/>
          </td>
          
          <!-- 글 제목 줄력 -->
          <td>
           <!-- 답글 -->
           <c:if test="${dto.q_re_level>0}">
             <img src="resources/imgs/level.gif" width="${5*dto.q_re_level}" height="16">
             <img src="resources/imgs/re.gif">
           </c:if>
          
           <!-- 원글 -->
           <c:if test="${dto.q_re_level==0}">
             <img src="resources/imgs/level.gif" width="${5*dto.q_re_level}" height="16">
           </c:if>
           
           <!-- 글제목을 클릭하면 글  내용보기로 간다  -->    
         <c:choose>
         
         <%--관리자 로그인 이라면 --%>  
          <c:when test="${mem_id=='admin'}">
           
           <a href="qna_content.do?q_num=${dto.q_num}&pageNum=${currentPage}&mem_id=${mem_id}&secret=${secret}">
            ${dto.q_subject}
             </a>
          </c:when>             

            <%--- 비밀글 이라면 --%> 
        
       	  	<c:when test="${dto.q_secret==true && dto.q_writer != mem_id}"> 
       	  	
       	  	  <c:choose>
       	  	  
       	  	  		<c:when test="${dto.q_writer == '관리자'}">
       	  	  		 <c:set var= "count" value="0"/>
       	  	  			<c:forEach var="dto1" items="${list}">
       	   				 <c:if test="${dto1.q_ref == dto.q_ref && dto1.q_writer == mem_id}">
       	   				 <c:set var="count" value="${count + 1}" />
       	   				 <a href="qna_content.do?q_num=${dto.q_num}&pageNum=${currentPage}&mem_id=${mem_id}&secret=${secret}">
            				 ${dto.q_subject}
          				 </a>
       	   				 </c:if>
       	   				 </c:forEach>
       	   				 
       	   				 <c:if test="${count==0}">
       	   				 비밀글 입니다.
       	   				 </c:if>
       	  	  		</c:when>
       	  	  		
       	  	  		<c:otherwise>
       	  	  		 비밀글 입니다.
       	  	  		</c:otherwise>
       	  	  </c:choose>
            </c:when>
            
            
            
             <%--비밀글 설정이 아니라면 (공개글 이라면) --%> 
            <c:when test="${dto.q_writer == '관리자' || dto.q_writer==mem_id}">
            
            <c:set var= "count" value="0"/>
       	  	  		<c:forEach var="dto1" items="${list}">
       	   				 <c:if test="${dto1.q_ref == dto.q_ref && dto1.q_writer == mem_id}">
       	   				 <c:set var="count" value="${count + 1}" />
       	   				 <a href="qna_content.do?q_num=${dto.q_num}&pageNum=${currentPage}&mem_id=${mem_id}&secret=${secret}">
            				 ${dto.q_subject}
          				 </a>
       	   				 </c:if>
       	   				 </c:forEach>
       	   				 
       	   				<c:if test="${count==0}">
       	   				비밀글 입니다.
       	   			 </c:if>
           </c:when>
           
           <c:when test="${dto.q_secret==false}">
           <a href="qna_content.do?q_num=${dto.q_num}&pageNum=${currentPage}&mem_id=${mem_id}&secret=${secret}">
             ${dto.q_subject}
           </a>     
           </c:when>
           
          </c:choose>
    
           <c:if test="${dto.q_readcount>=20}">
             <img src="resources/imgs/hot.gif" border="0" height="16">
           </c:if>
          </td>
          <!-- 글 제목 줄력 끝-->
          
          
          <!-- 작성자 -->
          <td>
           ${dto.q_writer}
          </td>
          
          <!-- 날짜 -->
          <td>
           <fmt:formatDate value="${dto.q_regdate}" pattern="yyyy-MM-dd hh:mm:ss"/>
          </td>
          
           <!-- 조횟수 -->
          <td>${dto.q_readcount}</td>
          
          <!-- ip -->
          <td>${dto.q_ip}</td>
         </tr>
       </c:forEach>
      </table>
    </c:if>
    <!-- 실행 -->
    
    <!-- block,page 처리 -->
    <table>
     <tr>
      <td align="center">
    <!-- 에러방지 -->
    <c:if test="${endPage>pageCount}">
      <c:set var="endPage" value="${pageCount}"/>
    </c:if>
    
    <!-- 이전블럭  -->
    <c:if test="${startPage>10}">
     <a href="list.do?pageNum=${startPage-10}">[이전블럭]</a>
    </c:if>
    
    <!-- 페이지처리 -->
    <c:forEach var="i" begin="${startPage}" end="${endPage}">
     <a href="list.do?pageNum=${i}">[${i}]</a>
    </c:forEach>
    
    <!-- 다음블럭 -->
    <c:if test="${endPage<pageCount}">
     <a href="list.do?pageNum=${startPage+10}">[다음블럭]</a>
    </c:if>
    
      </td>
     </tr>
    </table>
</body>
</html>
