<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
 <body bgcolor = "<%= session.getAttribute("pickedBgCol") == null ? 
			new String("white") : (String)session.getAttribute("pickedBgCol") %>">
 <h1>Rezultati glasanja</h1>
 Ovo su rezultati glasanja.<br><br>
  <table border = "1">   
    <thead>
     <tr>
      <th>Bend</th>
      <th>Broj glasova</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${bandsVotes}" var="band">
      <tr>
       <td>${band.name}</td>
       <td>${band.votes}</td>
      </tr>
 	 </c:forEach>
    </tbody>
  </table>
  <h1>Grafički prikaz rezultata</h1>
  <img src="/webapp2/glasanje-grafika" id="id"/>
  <h1>Rezultati u XLS formatu</h1>
  Rezultati u XLS formatu dostupni su <a href="./glasanje-xls">ovdje</a>.<br>
  <h1>Razno</h1>
  Primjeri pjesama pobjedničkih bendova<br><br>
  <c:forEach items="${winners}" var="band">
    <li><a href="${band.link}">${band.name}</a><br>
  </c:forEach>
 </body>
</html>