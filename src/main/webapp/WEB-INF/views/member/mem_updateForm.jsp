<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>updateForm.jsp</title>
<style type="text/css">
	h2{text-align: center;}
	table{
		margin: auto;
		background-color: ivory;
	}
</style>

 <script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
    
  <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
  
  <script>
	function openDaumPostcode(){
		 
		new daum.Postcode({
			oncomplete:function(data){
				document.getElementById('mem_zipcode').value=data.zonecode;
				document.getElementById('mem_addr').value=data.address;
	 		}
			
		}).open();
	}//openDaumPostcode()---
</script>

</head>
<body>
	<h2>내 정보 수정</h2>
	<form action="mem_UpdatePro.do" method="post">
		<table border="1">
			<tr>
				<td>ID</td>
				<td>
				${mdto.mem_id}
				<input type="hidden" name="mem_id" value="${mdto.mem_id}">
				</td>
			</tr>
			
			<tr>
				<td>암호</td>
				<td>
				<input type="password" name="mem_pw" id="mem_pw" size="20" >
				</td>
			</tr>
			
			<%--암호확인 --%>
			<tr>
				<td>암호</td>
				<td>
				<input type="password" name="mem_pw2" id="mem_pw2" size="20" >
				</td>
			</tr>
			<%--암호확인 --%>
			
			<tr>
				<td>이름</td>
				<td>
				<input type="text" name="mem_name" id="mem_name" size="30" value="${mdto.mem_name}">
				</td>
			</tr>
			
			<tr>
				<td>이메일</td>
				<td>
					<input type="text" name="mem_em1" id="mem_em1" value="${email1}">@ <!-- 보여주는것만이렇게  밸류는 밑에-->
					<select name="mem_em2" id="mem_em2">
						<option value="${email2}">${email2}</option>
						<option value="@naver.com">naver.com</option>
						<option value="@daum.net">daum.net</option>
						<option value="@nate.com">nate.com</option>
					</select>
				
				</td>
			</tr>
			
			<tr>
				<td>전화번호</td>
				<td>
					<select name="mem_tel1" id="mem_tel1">
						<option value="${tel1}">${tel1}</option>
						<option value="010">010</option>
						<option value="018">018</option>
						<option value="017">017</option>
					</select>
					
					<input type="text" name="mem_tel2" id="mem_tel2" size="4" value="${tel2}">
					<input type="text" name="mem_tel3" id="mem_tel3" size="4" value="${tel3}">
					
				</td>
			</tr>
			<tr>
				<td>우편번호</td>
				<td>
					<input type="text" name="mem_zipcode" id="mem_zipcode" size="7" value="${mdto.mem_zipcode}">
					<input type="button" value="주소검색" onClick="openDaumPostcode()">
				</td>
			</tr>
			
			<tr>
				<td>주소</td>
				<td>
					<input type="text" name="mem_addr" id="mem_addr" size="60" value="${mdto.mem_addr}" readonly>
				<br>
				상세주소:
				<input type="text" name="mem_addr2" id="mem_addr2" size="40" value="${mdto.mem_addr2}">	
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="내정보수정">&nbsp;&nbsp;
					<input type="button" value="취소" onclick="location='main.do'">
				</td>
			</tr>
			
		</table>
	</form>
</body>
</html>