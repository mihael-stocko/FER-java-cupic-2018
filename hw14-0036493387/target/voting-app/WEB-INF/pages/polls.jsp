<%@page import="hr.fer.zemris.java.p12.model.PollOption"%>
<%@page import="hr.fer.zemris.java.p12.model.Poll"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%! @SuppressWarnings("unchecked") %>
<%
  List<Poll> polls = (List<Poll>)request.getAttribute("polls");
%>
<html>
  <body>
  <b>Dostupne ankete:</b><br>
    <ul>
    <% for(Poll p : polls) { %>
    <li>
      <a href="./glasanje?pollID=<%=p.getId()%>"><%=p.getTitle()%></a>
    </li>  
    <% } %>  
    </ul>
  </body>
</html>