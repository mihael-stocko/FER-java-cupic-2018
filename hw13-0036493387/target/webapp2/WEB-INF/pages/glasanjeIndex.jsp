<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
 <body bgcolor = "<%= session.getAttribute("pickedBgCol") == null ? 
			new String("white") : (String)session.getAttribute("pickedBgCol") %>">
 <h1>Glasanje za omiljeni bend:</h1>
 <p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
 <ol>
  <c:forEach items="${bands}" var="band">
    <li><a href="./glasanje-glasaj?id=${band.id}">${band.name}</a><br>
  </c:forEach>
 </ol>
 </body>
</html>