<%@ page import="java.util.Random" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%! 
private String getColor() {
	Random random = new Random();
	int num = random.nextInt(4);
	switch(num) {
	case 0:
		return "red";
	case 1:
		return "green";
	case 2:
		return "blue";
	default:
		return "pink";
	}
}
%>

<%
String textColor = getColor();
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Funny story</title>
	</head>
	<body bgcolor = "<%= session.getAttribute("pickedBgCol") == null ? 
			new String("white") : (String)session.getAttribute("pickedBgCol") %>">
		<h1>Kozel</h1>
		<font color="<% out.print(textColor); %> ">
		Bio jednom jedan jarac Tujo. Tujo je imao jako veliku bradicu. Tujina bradica bila je veoma tajnovita bradica.
	<br>Jednog dana Tujo je pjevao i sjedio na kamenu koji mu je bio jedini prijatelj. Tujo je običavao pričati sa svojim 
	<br>kamenom kao da mu je brat, cura, čak i majka. Tujo je nekada u želji za sisom sisao svoj kamen iako je bio odviše 
	<br>star za sisanje sisa, a kamoli kamenja. Tuji su od tolikog sisanja ispala dva prednja zuba i zbog toga je bila još 
	<br>veća vjerojatnost da nikada neće pronaći svoju srodnu Tujku. I tako je Tujo sjedio i pjevao i svirao. Stvarno je 
	<br>bilo upržilo, pa je Tujo čak osjetio kako mu bradica gori, i taman kad je odlučio otići u hlad, osjetio je fin miris 
	<br>svježe koze. Iz grma je izvirila glava ženske koze Kozete koja je brstila taj grm i moglo se reći da je imala kozice. 
	<br>Tujo je pao u travu od silnog iznenađenja i počeo se gušiti svojom bradicom. Kozeta je prišla Tuji i pomogla mu da se 
	<br>prestane gušiti, ali se onda i sama počela gušiti od mirisa gnojiva koje se širilo iz Tujine smočnice. Tuji nije baš 
	<br>bilo do druženja pošto su ga kastrirali još u osnovnoj, ali je ipak odlučio odvesti Kozetu na večeru. Kozeta je bila 
	<br>spremna za jelo. Tujo je došao samo s tanjurima, ali hrani nije bilo ni traga. Kozeta ga je gledala u nevjerici kad 
	<br>je počeo iz svoje tajnovite bradice vaditi silne hrane: sir, banane, sok od trave i Kozel. Kozeta se zgrozila. Tujo 
	<br>je srkao Kozel bez svoja dva prednja zuba i počeo je roktati. Tujo je bio luđak. Kozeta je pobjegla u šumu. 
	<br>Tujo je poželio smrt.
		</font>
	</body>
</html>