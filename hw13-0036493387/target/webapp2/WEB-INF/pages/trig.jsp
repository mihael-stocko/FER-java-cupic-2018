<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Trigonometric functions</title>
	</head>
	
	<body bgcolor = "<%= session.getAttribute("pickedBgCol") == null ? 
			new String("white") : (String)session.getAttribute("pickedBgCol") %>">
		<h1>Trigonometric functions</h1>
		
		<table border=1>
			<thead>
				<tr><th>x</th><th>sin(x)</th><th>cos(x)</th></tr>
			</thead>
			<tbody>
				<c:forEach var="number" items="${trig}">
					<tr><td>${number.fi}</td><td>${number.sin}</td><td>${number.cos}</td></tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>