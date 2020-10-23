<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Курсы</title>
</head>
<body>
	<h1>Курсы</h1>
	<c:if test="${not empty courses}">
		<table border="1">
			<tr>
				<td>Название</td>
				<td>Часы</td>
				<td>Описание</td>
			</tr>
			<c:forEach items="${courses}" var="course">
				<tr>
					<td>${course.title}</td>
					<td>${course.length}</td>
					<td>${course.description}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>