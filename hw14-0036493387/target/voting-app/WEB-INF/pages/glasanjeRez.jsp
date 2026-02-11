<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
 <body>
 <h1>Rezultati glasanja</h1>
 Ovo su rezultati glasanja.<br><br>
  <table border = "1">   
    <thead>
     <tr>
      <th>Opcija</th>
      <th>Broj glasova</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${options}" var="option">
      <tr>
       <td>${option.optionTitle}</td>
       <td>${option.votesCount}</td>
      </tr>
 	 </c:forEach>
    </tbody>
  </table>
  <h1>Grafički prikaz rezultata</h1>
  <img src="/voting-app/servleti/glasanje-grafika?pollID=${pollID}" id="id"/>
  <h1>Rezultati u XLS formatu</h1>
  Rezultati u XLS formatu dostupni su <a href="./glasanje-xls?pollID=${pollID}">ovdje</a>.<br>
  <h1>Razno</h1>
  Primjeri pobjedničkih opcija<br><br>
  <c:forEach items="${winners}" var="option">
    <li><a href="${option.optionLink}">${option.optionTitle}</a><br>
  </c:forEach>
 </body>
</html>