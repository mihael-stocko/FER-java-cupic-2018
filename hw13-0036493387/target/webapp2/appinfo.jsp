<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
long time = (System.currentTimeMillis() - (Long)getServletContext().getAttribute("startTime"));

long hours = time/3600000;
time -= hours*3600000;

long minutes = time/60000;
time -= minutes*60000;

long seconds = time/1000;
time -= seconds*1000;

long millis = time;

%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Application info</title>
	</head>
	<body bgcolor = "<%= session.getAttribute("pickedBgCol") == null ? 
			new String("white") : (String)session.getAttribute("pickedBgCol") %>">
		The application has been running for <%= hours %> hours, <%= minutes %> minutes, 
		<%= seconds %> seconds and <%= millis %> milliseconds.
	</body>
</html>