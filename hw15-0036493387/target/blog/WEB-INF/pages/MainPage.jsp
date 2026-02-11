<%@page import="hr.fer.zemris.opp.model.Participant"%>
<%@page import="java.util.List"%>
<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!@SuppressWarnings("unchecked")%>
<%
	List<Participant> users = (List<Participant>)request.getAttribute("users");
%>

<html>
  <body>
    
  <%
      	if(request.getSession().getAttribute("current.user.id") == null) {
      %>
  	<form action="login" method="post">
		
		<div>
		 <div>
		  <span class="formLabel">Nick</span><input type="text" name="nick" size="50" value="${nick}">
		 </div>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Lozinka</span><input type="password" name="password" size="50">
		 </div>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="prijava" value="Prijavi se">
		</div>
		
	</form>
	
	<p style="color:red;">${error}</p>

	<p>
	<a href="./register">Registracija</a>
	<p>
  <%
  	} else {
  %>
	<form action="logout" method="post">
		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="odjava" value="Odjavi se">
		</div>
		
	</form>
  <%
  	}
  %>
 

  <ul>
    <%
    	for(Participant u : users) {
    %>
    <li>
      <a href="./author/<%=u.getNick()%>"><%=u.getLastName()%> <%=u.getFirstName()%></a>
    </li>  
    <% } %>  
    </ul>

  </body>
</html>
