<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>

 <% if(request.getSession().getAttribute("current.user.id") == null) { %>
  	Niste prijavljeni.
  <% } else { %>
    <%=request.getSession().getAttribute("current.user.fn")%>
    <%=request.getSession().getAttribute("current.user.ln")%>
    <a href="../../logout">Odjava</a>
  <% } %>

  <p>

  <form action="" method="post">

		<div>
		 <div>
		  <span class="formLabel">Naslov</span><input type="text" name="title" size="20">
		 </div>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Tekst</span><input type="text" name="content" size="50">
		 </div>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="add" value="Dodaj unos">
		</div>
			
		</form>
		<p style="color:red;">${error}</p>

  </body>
</html>