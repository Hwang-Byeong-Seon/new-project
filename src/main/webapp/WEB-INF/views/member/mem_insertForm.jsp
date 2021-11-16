<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insertForm.jsp</title>
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

<script type="text/javascript">
	function check() {
		//데이터 유효성 체크 
		if($('#mem_id').val()==''){
			alert("Id를 입력하세요");
			$('#mem_id').focus();
			return false;
		}
		
		if($('#mem_pw').val()==''){
			alert("암호를 입력하세요");
			$('#mem_pw').focus();
			return false;
		}
		if($('#mem_pw2').val()==''){
			alert("암호확인을 입력하세요");
			$('#mem_pw2').focus();
			return false;
		}
		//암호와 암호확인이 같은지 비교 
		if($('#mem_pw').val()!=$('#mem_pw2').val()){
			alert("암호와 암호확인이 일치하지 않습니다.");
			$('#mem_pw').val('');
			$('#mem_pw2').val('');
			
			$('#mem_pw').focus();
			return false;
		}
		
		if($('#mem_name').val()==''){
			alert("이름을 입력하세요");
			$('#mem_name').focus();
			return false;
		}
		
		return true;
	}//check-end
	
	//Ajax
	function confirmIDCheck() {
	
		if($('mem_#id').val()==''){
			alert("id를 입력하세요.");
			$('#mem_id').focus();
			
		} else{
			//alert("aaaa");
			$.ajax({
				type:"POST",
				url:"confirmID.do",
				data:"mem_id="+$('#mem_id').val(),  //서버로 넘길 인수값 
				dataType:'JSON', //서버가 보내준 자료타입
				success:function(data){
					//alert(data);
					
					 if(data.check==1){ //사용가능
						alert("사용가능한 ID입니다.");
						$('#mem_pw').focus();
					}
					else if(data.check==-1){ //사용중
						alert("이미 사용중인 ID입니다.");
						$('#mem_id').val('').focus();
					} 
				}//success - end 
			});
		
		}//else-end
		
	}//confirmIDCheck()-end
	
</script>
</head>
<body>
	<h2>회원가입</h2>
	<form action="mem_insertPro.do" method="post" onsubmit="return check()">
		<table border="1">
			<tr>
				<td>ID</td>
				<td>
					<input type="text" name="mem_id" id="mem_id" size="10">
					<input type="button" value="ID중복체크" onclick="confirmIDCheck()">
				</td>
			</tr>
			
			<tr>
				<td>암호</td>
				<td><input type="password" name="mem_pw" id="mem_pw" size="10"> </td>
			</tr>
			
			<tr>
				<td>암호확인</td>
				<td><input type="password" name="mem_pw2" id="mem_pw2" size="10"> </td>
			</tr>
			
			<tr>
				<td>이름</td>
				<td><input type="text" name="mem_name" id="mem_name" size="30"> </td>
			</tr>
			
			<tr>
				<td>이메일</td>
				<td>
					<input type="text" name="mem_em1" id="mem_em1">@ <!-- 보여주는것만이렇게  밸류는 밑에-->
					<select name="mem_em2" id="mem_em2">
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
						<option value="010">010</option>
						<option value="018">018</option>
						<option value="017">017</option>
					</select>
					
					<input type="text" name="mem_tel2" id="mem_tel2" size="4">
					<input type="text" name="mem_tel3" id="mem_tel3" size="4">
					
				</td>
			</tr>
			
			<tr>
				<td>우편번호</td>
				<td>
					<input tpye="text" name="mem_zipcode" id="mem_zipcode" size="7">
					<input type="button" value="주소검색" onClick="openDaumPostcode()">
				</td>
			</tr>
			
			<tr>
				<td>주소</td>
				<td>
					<input type="text" name="mem_addr" id="mem_addr" size="60" readonly>
				<br>
				상세주소:
				<input type="text" name="mem_addr2" id="mem_addr2" size="40">	
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="회원가입">
					<input type="reset" value="다시입력">
					<input type="button" value="가입안함" onclick="location='main.do'">
				</td>
			
			</tr>
			
		</table>
	</form>
</body>
</html>