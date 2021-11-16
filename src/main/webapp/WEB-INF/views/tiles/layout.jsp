<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%--layout.jsp --%>
 <table border="1" style="width:100%; min-width:700px;">
 
   <tr>
     <td colspan="2" height="60" bgcolor="cyan">
        <tiles:insertAttribute name="header" />
     </td>
   </tr>
   
   <tr>
     <td width="80" height="500">
       <tiles:insertAttribute name="side" />
     </td>
     
     <td valign="top">
       <tiles:insertAttribute name="content" />
     </td>
   </tr>
   
   <tr>
     <td colspan="2" height="100">
       <tiles:insertAttribute name="footer" />
     </td>
   </tr>
 </table>
</body>
</html>