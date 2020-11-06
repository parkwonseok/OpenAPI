<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Home</title>
	<style>
		body {text-align: center; padding-top: 50px;}
		input[type=text] {width: 500px; height: 50px; font-size: 20px; margin: 3px; padding-left: 10px;}
		input[type=submit] {width: 500px; height: 50px; font-size: 20px; margin-top: 30px; background: rgba(0,0,0,0.8); color: white; border: none;}
		input[type=submit]:hover {cursor: pointer;}
	</style>
</head>
<body>
	<h1>아파트 실거래가 데이터 DB에 넣기</h1>
	<form action="${pageContext.request.contextPath }/insert" method="post">
		<input type="text" name="startYears" placeholder="시작년도"/><br>
		<input type="text" name="startMonths" placeholder="시작월"/><br>
		<input type="text" name="endYears" placeholder="마지막년도"/><br>
		<input type="text" name="endMonths" placeholder="마지막월"/><br>
		<input type="submit" value="insert"/>
	</form>
</body>
</html>
