<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.time.LocalTime"%>

 <%@ page isELIgnored="false" %>

<!-- Основные теги создания циклов, определения условий, вывода информации на страницу и т.д.  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!--  Теги для работы с XML-документами -->
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<!--  Теги для работы с базами данных -->
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/sql"%>

<!--  Теги для форматирования и интернационализации информации (i10n и i18n) -->
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>
	<%
		LocalTime now = LocalTime.now();
		out.print(String.format("Current Server time: %tR", now));
		String s = String.format("Current Server time: %tR", now);
	%>
	</h1>
	<h2>
		<%= s%>
	</h2>
	<h2>
		<c:out value="16+64"/>
	</h2>
	<h2>
		<c:out value="${16+64*2}"/>
		<c:set var="salary" value="300000" scope="session"/>
	</h2>
	<h2>
		<c:if test="${salary > 100000 }">
			<p> Salary = <c:out value="${salary}"/></p>
		</c:if>
	</h2>
</body>
</html>