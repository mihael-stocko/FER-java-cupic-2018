<%@page import="hr.fer.zemris.opp.model.BlogEntry"%>
<%@page import="hr.fer.zemris.opp.model.Participant"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  List<BlogEntry> entries = (List<BlogEntry>)request.getAttribute("entries");
%>

<html>
  <body>
  <% if(request.getSession().getAttribute("current.user.id") == null) { %>
  	Niste prijavljeni.
  <% } else { %>
    <%=request.getSession().getAttribute("current.user.fn")%>
    <%=request.getSession().getAttribute("current.user.ln")%>
    <a href="../logout">Odjava</a>
  <% } %>
  
  <p>
  <ul>
    <% for(BlogEntry e : entries) { %>
    <li>
      <a href="./${nick}/<%=e.getId()%>"><%=e.getTitle()%></a>
    </li>  
    <% } %>  
    </ul>
    
    <p>
    
    <c:choose>
 	 <c:when test="${addForm}">
  		<a href="${nick}/new">Novi unos</a>
  		<p>
 	 </c:when>
	</c:choose>

  </body>
</html>
