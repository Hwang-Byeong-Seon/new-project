<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>writeForm.jsp</title>
<style type="text/css">
h2{text-align: center;}
table{
margin: auto;
background-color: ivory;
}
</style>
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
  function check(){
	  if($("#writer_sh").val()==''){
		  alert("이름을 입력 하세요");
		  $("#writer_sh").focus();
		  return false;
	  }
	  
	  if($("#subject_sh").val()==''){
		  alert("글제목을 입력 하세요");
		  $("#subject_sh").focus();
		  return false;
	  }
	  
	  if($("#content_sh").val()==''){
		  alert("글내용을 입력 하세요")
		  $("#content_sh").focus();
		  return false;
	  }
	  
	  if($("#pw_sh").val()==''){
		  alert("암호를 입력 하세요")
		  $("#pw_sh").focus();
		  return false;
	  }
	  return true;
  }
</script>

</head>
<body>
<c:if test="${num_sh==0}">
  <h2>게시판 글쓰기</h2>
</c:if>

<c:if test="${num_sh!=0}">
  <h2>게시판 답글쓰기</h2>
</c:if>

  <form method="post" action="writePro_sh.do" onSubmit="return check()">
    <input type="hidden" name="pageNum_sh" value="${pageNum_sh}">
    <input type="hidden" name="num_sh" value="${num_sh}">
    <input type="hidden" name="ref_sh" value="${ref_sh}">
    <input type="hidden" name="re_step_sh" value="${re_step_sh}">
    <input type="hidden" name="re_level_sh" value="${re_level_sh}">
    
    <table border="1">
    
      <tr>
        <td colspan="2" align="right">
          <a href="list_sh.do">글목록</a>
        </td>
      </tr>
      
      <tr>
        <td>이름</td>
        <td><input type="text" name="writer_sh" id="writer_sh" size="30"></td>
      </tr>
      
      <tr>
        <td>글제목</td>
        <td>
          <!-- 원글 -->
          <c:if test="${num_sh==0}">
            <input type="text" name="subject_sh" id="subject_sh" size="40">
          </c:if>
      
          <!-- 답글 -->
          <c:if test="${num_sh!=0}">
            <input type="text" name="subject_sh" id="subject_sh" size="40" value="[답변]">
          </c:if>
        </td>
      </tr>
      
      <tr>
        <td>글내용</td>
        <td>
          <textarea name="content_sh" id="content_sh" rows="10" cols="50"></textarea>
        </td>
      </tr>
      
      <tr>
        <td>암호</td>
        <td><input type="password" name="pw_sh" id="pw_sh" size="15"></td>
      </tr>
      
      <tr>
        <td colspan="2" align="center">
          <c:if test="${num_sh==0}">
            <input type="submit" value="글쓰기">
          </c:if>
          
          <c:if test="${num_sh!=0}">
            <input type="submit" value="답글쓰기">
          </c:if>
          
          <input type="reset" value="다시쓰기">
          <input type="button" value="글목록" onClick="location.href='list_sh.do'">
        </td>
      </tr>
     
    </table>
  </form>
  
</body>
</html>