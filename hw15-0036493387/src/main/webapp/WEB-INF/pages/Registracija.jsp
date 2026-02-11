<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Registracija</title>
		
		<style type="text/css">
		.greska {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		.formLabel {
		   display: inline-block;
		   width: 100px;
                   font-weight: bold;
		   text-align: right;
                   padding-right: 10px;
		}
		.formControls {
		  margin-top: 10px;
		}
		</style>
	</head>

	<body>
		<h1>
		Registracija novog korisnika
		</h1>

		<form action="register" method="post">

		<div>
		 <div>
		  <span class="formLabel">Ime</span><input type="text" name="firstName" size="20">
		 </div>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Prezime</span><input type="text" name="lastName" size="20">
		 </div>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">EMail</span><input type="text" name="email" size="50">
		 </div>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Nick</span><input type="text" name="nick" size="50">
		 </div>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Lozinka</span><input type="password" name="password" size="50">
		 </div>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Registriraj se">
		  <input type="submit" name="metoda" value="Odustani">
		</div>
		
		</form>
		
		<p style="color:red;">${error}</p>

	</body>
</html>
