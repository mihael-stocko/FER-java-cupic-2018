<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>

<%
boolean loggedIn = request.getSession().getAttribute("current.user.id") != null;
String loggedNick = null;
if(loggedIn) {
	loggedNick = (String)request.getSession().getAttribute("current.user.nick");
}
%>

 <% if(!loggedIn) { %>
  	Niste prijavljeni.
  <% } else { %>
    <%=request.getSession().getAttribute("current.user.fn")%>
    <%=request.getSession().getAttribute("current.user.ln")%>
    <a href="../../logout">Odjava</a>
  <% } %>

  <c:choose>
    <c:when test="${blogEntry==null}">
      Nema unosa!
    </c:when>
    <c:otherwise>
      <h1><c:out value="${blogEntry.title}"/></h1>
      <p><c:out value="${blogEntry.text}"/></p>
      
      
      <c:choose>
 		 	<c:when test="${editForm}">
  				<a href="edit?id=${blogEntry.id}">Uredi</a>
 	 		</c:when>
		</c:choose>
		<p>
      
      
      
      <c:if test="${!blogEntry.comments.isEmpty()}">
      <ul>
      <c:forEach var="e" items="${blogEntry.comments}">
        <li><div style="font-weight: bold">[Korisnik=<c:out value="${e.usersEMail}"/>] <c:out value="${e.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${e.message}"/></div></li>
      </c:forEach>
      </ul>
      </c:if>
      
      
      <form action="" method="post">
      
     <% if(!loggedIn) { %>
  		<div>
			 <div>
			  <span class="formLabel">email</span><input type="text" name="email" size="20">
			 </div>
			</div>
 	 <% } else { %>
  		<input type="hidden" name="loggedIn" value="true"/>
  	    <input type="hidden" name="loggedNick" value=<%=loggedNick%>/>
 	 <% } %>
		
		<div>
		 <div>
		  <span class="formLabel">Poruka</span><input type="text" name="message" size="20">
		 </div>
		</div>
      
		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="hidden" name="id" value="${blogEntry.id}"/>
		  <input type="submit" name="metoda" value="Komentiraj">
		</div>
			
		</form>
      
    </c:otherwise>
  </c:choose>

  </body>
</html>
