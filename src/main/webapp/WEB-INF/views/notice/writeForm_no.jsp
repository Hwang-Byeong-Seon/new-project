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
	  
	  if($("#subject_no").val()==''){
		  alert("글제목을 입력하시오");
		  $("#subject_no").focus();
		  return false;
	  }
	  
	  if($("#content_no").val()==''){
		  alert("글내용을 입력하시오");
		  $("#content_no").focus();
		  return false;
	  }
	  return true;
  }
</script>
</head>
<body>
<c:if test="${num_no==0}">
  <h2>공지사항 등록</h2>
</c:if>

<c:if test="${num_no!=0}">
  <h2>추가사항 등록</h2>
</c:if>

<form method="post" action="writePro_no.do" onSubmit="return check()" enctype="multipart/form-data">
  <input type="hidden" name="pageNum" value="${pageNum}">
  <input type="hidden" name="num_no" value="${num_no}">
  <input type="hidden" name="ref_no" value="${ref_no}">
  <input type="hidden" name="re_step_no" value="${re_step_no}">
  <input type="hidden" name="re_level_no" value="${re_level_no}">
  
  <table border="1">
 
    <tr>
      <td>이름</td>
      <td><input type="text" name="writer_no" id="writer_no" size="30" value="관리자" readonly></td>
    </tr>
    
    <tr>
      <td>글제목</td>
      <td>
        <!-- 원글 -->
        <c:if test="${num_no==0}">
          <input type="text" name="subject_no" id="subject_no" size="40">
        </c:if>
    
        <!-- 답글 -->
        <c:if test="${num_no!=0}">
          <input type="text" name="subject_no" id="subject_no" size="40" value="[추가사항]">
        </c:if>
      </td>
    </tr>
    
    <tr>
      <td>글내용</td>
      <td>
        <textarea name="content_no" id="content_no" rows="10" cols="50"></textarea>
      </td>
    </tr>

    <tr>
      <td>파일 첨부</td>
      <td><input type="file" name="uploadFile"/></td>
    </tr>
      
    <tr>
      <td>파일 첨부</td>
      <td><input type="file" name="uploadFile2"/></td>
    </tr>
      
    <tr>
      <td>파일 첨부</td>
      <td><input type="file" name="uploadFile3"/></td>
    </tr>
     
    <tr>
      <td colspan="2" align="center">
        <c:if test="${num_no==0}">
          <input type="submit" value="등록">
        </c:if>
        
        <c:if test="${num_no!=0}">
          <input type="submit" value="추가등록">
        </c:if>
        
        <input type="reset" value="다시쓰기">
        <input type="button" value="글목록" onClick="location.href='list_no.do'">
      </td>
    </tr>
  </table>
</form>
</body>
</html>