<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Error</title>
	</head>
	<body bgcolor = "<%= session.getAttribute("pickedBgCol") == null ? 
			new String("white") : (String)session.getAttribute("pickedBgCol") %>">
		Invalid parameters have been given.<br>
		<a href="./index.jsp">Index</a>
	</body>
</html>