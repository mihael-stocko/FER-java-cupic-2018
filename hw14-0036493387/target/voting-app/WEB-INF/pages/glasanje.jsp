<%@page import="hr.fer.zemris.java.p12.model.PollOption"%>
<%@page import="hr.fer.zemris.java.p12.model.Poll"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%! @SuppressWarnings("unchecked") %>
<%
List<PollOption> pollOptions = (List<PollOption>)request.getAttribute("pollOptions");
%>
<html>
  <body>
  <b>${message}</b><br>
    <ul>
    <% for(PollOption p : pollOptions) { %>
    <li>
      <a href="./glasanje-glasaj?optionID=<%=p.getId()%>"><%=p.getOptionTitle()%></a>
    </li>  
    <% } %>  
    </ul>
  </body>
</html>