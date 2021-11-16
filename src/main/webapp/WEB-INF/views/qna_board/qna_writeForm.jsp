<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>writeForm.jsp</title>
<style type="text/css">
h2{text-align: center;}
table{
margin:auto;
background-color: ivory;
}
</style>
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
  function check(){
	  if($("#q_writer").val()==''){
		  alert("이름을 입력 하세요");
		  $("#q_writer").focus();
		  return false;
	  }
	  if($("#q_subject").val()==''){
		  alert("글 제목 입력 하세요");
		  $("#q_subject").focus();
		  return false;
	  }
	  if($("#q_content").val()==''){
		  alert("글내용 입력 하세요");
		  $("#q_content").focus();
		  return false;
	  }
	  if($("#q_pw").val()==''){
		  alert("암호을 입력 하세요");
		  $("#q_pw").focus();
		  return false;
	  }
	  return true;
  }
</script>

</head>
<body>
<c:if test="${num==0}">
  <h2>게시판 글쓰기</h2>
</c:if>

<c:if test="${num!=0}">
  <h2>답글쓰기</h2>
</c:if>

  <form method="post" action="qna_writePro.do" onSubmit="return check()">
    <input type="hidden" name="pageNum" value="${pageNum}">
    <input type="hidden" name="q_num" value="${q_num}">
    <input type="hidden" name="q_ref" value="${q_ref}">
    <input type="hidden" name="q_re_step" value="${q_re_step}">
    <input type="hidden" name="q_re_level" value="${q_re_level}">
    
    <table border="1">
      <tr>
        <td colspan="2" align="right">
          <a href="qna_list.do">글목록</a>
        </td>
      </tr>
      
      <tr>
        <td>이름</td>
        <td>
        <c:if test="${mem_id=='admin'}">
        <input type="text" name="q_writer" id="q_writer" value="관리자" size="30" readonly>
        </c:if>
        
         <c:if test="${mem_id!='admin'}">
        <input type="text" name="q_writer" id="q_writer" value="${mem_id}" size="30" readonly>
        </c:if>
        
        </td>
      </tr>
      
     <tr>
       <td>글제목</td>
       <td>
	      <!-- 원글 -->
	      <c:if test="${q_num==0}">
	       <input type="text" name="q_subject" id="q_subject" size="40">
	      </c:if>
	      
	       <!-- 답글 -->
	      <c:if test="${q_num!=0}">
	        <input type="text" name="q_subject" id="q_subject" size="40" value="[답변]">
	      </c:if>
       </td>
     </tr>
     
     <tr>
       <td>글내용</td>
       <td>
        <textarea name="q_content" id="q_content" rows="10" cols="50"></textarea>
       </td>
     </tr>      
      
     <tr>
       <td>암호</td>
       <td><input type="password" name="q_pw" id="q_pw" size="15"></td>
     </tr>
     
     <tr>
     	<td>비밀글</td>
     	<td>
     	<input type='checkbox' name="secret" value="secret" />비밀글 설정 여부 
     	</td>
     </tr>
     <tr>
       <td colspan="2" align="center">
        <c:if test="${q_num==0}">
         <input type="submit" value="글쓰기">
        </c:if>
        
        <c:if test="${q_num!=0}">
         <input type="submit" value="답글쓰기">
        </c:if>
         
        <input type="reset" value="다시쓰기">
        <input type="button" value="글목록" onclick="location.href='qna_list.do'">
       </td>
     </tr>
    </table>
  </form>
</body>
</html>