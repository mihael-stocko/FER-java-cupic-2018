<html>

Pokusaj broj:   ${brojPokusaja} <br>

<img src="/number-guesser/igra/slika?id=${id }" id="id"/>

		<form action="play?id=${id}" method="POST">
 			<input type="text" name="broj" min="0" max="100" step="1" value="0"><br>
 			<input type="submit" value="Probaj">
		</form>
		
		
		${pok}
		<br>
		${poruka }




${secretNumber }
</html>