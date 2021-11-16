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
	  if($("#writer_faq").val()==''){
		  alert("이름을 입력 하세요");
		  $("#writer_faq").focus();
		  return false;
	  }
	  
	  if($("#subject_faq").val()==''){
		  alert("글제목을 입력 하세요");
		  $("#subject_faq").focus();
		  return false;
	  }
	  
	  if($("#content_faq").val()==''){
		  alert("글내용을 입력 하세요")
		  $("#content_faq").focus();
		  return false;
	  }
	  
	  if($("#pw_faq").val()==''){
		  alert("암호를 입력 하세요")
		  $("#pw_faq").focus();
		  return false;
	  }
	  return true;
  }
</script>

</head>
<body>
<c:if test="${num_faq==0}">
  <h2>게시판 글쓰기</h2>
</c:if>

<c:if test="${num_faq!=0}">
  <h2>게시판 답글쓰기</h2>
</c:if>

  <form method="post" action="writePro_faq.do" onSubmit="return check()" enctype="multipart/form-data">
    <input type="hidden" name="pageNum_faq" value="${pageNum_faq}">
    <input type="hidden" name="num_faq" value="${num_faq}">
    <input type="hidden" name="ref_faq" value="${ref_faq}">
    <input type="hidden" name="re_step_faq" value="${re_step_faq}">
    <input type="hidden" name="re_level_faq" value="${re_level_faq}">
    
    <table border="1">
    
      <tr>
        <td colspan="2" align="right">
          <a href="list_faq.do">글목록</a>
        </td>
      </tr>
      
      <tr>
        <td>이름</td>
        <td><input type="text" name="writer_faq" id="writer_faq" value="관리자" size="30" readonly ></td>
      </tr>
      
      <tr>
        <td>글제목</td>
        <td>
          <!-- 원글 -->
          <c:if test="${num_faq==0}">
            <input type="text" name="subject_faq" id="subject_faq" size="40">
          </c:if>
      
          <!-- 답글 -->
          <c:if test="${num_faq!=0}">
            <input type="text" name="subject_faq" id="subject_faq" size="40" value="[답변]">
          </c:if>
        </td>
      </tr>
      
      <tr>
        <td>글내용</td>
        <td>
          <textarea name="content_faq" id="content_faq" rows="10" cols="50"></textarea>
        </td>
      </tr>
      
      <tr>
        <td>암호</td>
        <td><input type="password" name="pw_faq" id="pw_faq" size="15"></td>
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
          <c:if test="${num_faq==0}">
            <input type="submit" value="글쓰기">
          </c:if>
          
          <c:if test="${num_faq!=0}">
            <input type="submit" value="답글쓰기">
          </c:if>
          
          <input type="reset" value="다시쓰기">
          <input type="button" value="글목록" onClick="location.href='list_faq.do'">
        </td>
      </tr>
     
    </table>
  </form>
  
</body>
</html>