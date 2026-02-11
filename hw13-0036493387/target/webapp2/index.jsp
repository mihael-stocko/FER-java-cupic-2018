<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Index</title>
	</head>
	<body bgcolor = "<%= session.getAttribute("pickedBgCol") == null ? 
			new String("white") : (String)session.getAttribute("pickedBgCol") %>">
		<h1>Index</h1>
		<a href="./colors.jsp">Background color chooser</a><br>
		<a href="./report.jsp">OS survey report</a><br>
		<a href="./trigonometric?a=0&b=90">Trigonometric functions</a>
		<form action="trigonometric" method="GET">
 			Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 			Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 			<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
		</form>
		<p>
		<a href="./stories/funny.jsp">Funny story</a><br>
		<a href="./powers?a=1&b=100&n=3">Powers</a><br>
		<a href="./appinfo.jsp">Application information</a><br>
		<a href="./glasanje">Glasanje za najbolji bend</a>
	</body>
</html>