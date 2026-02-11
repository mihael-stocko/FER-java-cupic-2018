package servlets;

import java.util.concurrent.ThreadLocalRandom;

public class Igra {

	public int id;
	public int brojPokusaja = 7;
	public int zamisljeniBroj;
	public String poruka = "";
	
	public Igra() {
		zamisljeniBroj = ThreadLocalRandom.current().nextInt(0, 101);
	}
}
