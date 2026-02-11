<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Color chooser</title>
	</head>
	<body bgcolor = "<%= session.getAttribute("pickedBgCol") == null ? 
			new String("white") : (String)session.getAttribute("pickedBgCol") %>">
		<a href="./setcolor?bgcolor=white">WHITE</a><br>
		<a href="./setcolor?bgcolor=red">RED</a><br>
		<a href="./setcolor?bgcolor=green">GREEN</a><br>
		<a href="./setcolor?bgcolor=cyan">CYAN</a>
	</body>
</html>