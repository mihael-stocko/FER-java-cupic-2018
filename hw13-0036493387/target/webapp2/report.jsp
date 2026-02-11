<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
	</head>
	<body bgcolor = "<%= session.getAttribute("pickedBgCol") == null ? 
			new String("white") : (String)session.getAttribute("pickedBgCol") %>">
		<h1>OS usage</h1>
		<p>Here are the results of OS usage in survey that we completed.</p>
		<br>
		<img src="/webapp2/reportImage" id="id"/>
	</body>
</html>