package hr.fer.zemris.java.hw13.servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet reads information about bands from a file, sorts the bands by the number of
 * votes, picks the winners and puts them into a new list, sets both lists as global attributes,
 * and then calls a jsp that displays the results.
 * 
 * @author Mihael Stočko
 *
 */
public class GlasanjeRezultatiServlet extends HttpServlet {

	public static final long serialVersionUID = 1L;
	
	/**
	 * List of bands attribute name
	 */
	public static final String bandsAttribute = "bandsVotes";
	
	/**
	 * List of bands attribute name
	 */
	public static final String winnersAttribute = "winners";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String filepath = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(filepath));
		List<Band> bands = new LinkedList<>();
		for(String line : lines) {
			String[] lineParams = line.split("\t");
			if(lineParams.length != 3) {
				req.getRequestDispatcher("/WEB-INF/pages/badfile.jsp").forward(req, resp);
			}
			int id = 0;
			try {
				id = Integer.parseInt(lineParams[0]);
			} catch(NumberFormatException e) {
				req.getRequestDispatcher("/WEB-INF/pages/badfile.jsp").forward(req, resp);
			}
			String name = lineParams[1];
			String link = lineParams[2];
			bands.add(new Band(id, name, link));
		}
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		if(!Paths.get(fileName).toFile().exists()) {
			Paths.get(fileName).toFile().createNewFile();
			OutputStream os = new FileOutputStream(fileName);
			int numOfBands = GlasanjeServlet.numOfBands;
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < numOfBands; i++) {
				sb.append(i+1);
				sb.append('\t');
				sb.append(0);
				sb.append('\r');
				sb.append('\n');
			}
			sb.setLength(sb.length()-2);
			os.write(sb.toString().getBytes());
			os.close();
		}
		
		List<String> votes = Files.readAllLines(Paths.get(fileName));
		Map<Integer, Integer> map = new HashMap<>();
		for(String line : votes) {
			String[] lineParams = line.split("\t");
			if(lineParams.length != 2) {
				req.getRequestDispatcher("/WEB-INF/pages/badfile.jsp").forward(req, resp);
			}
			try {
				map.put(Integer.parseInt(lineParams[0]), Integer.parseInt(lineParams[1]));
			} catch(NumberFormatException e) {
				req.getRequestDispatcher("/WEB-INF/pages/badfile.jsp").forward(req, resp);
			}
		}
		
		for(Band band : bands) {
			band.setVotes(map.get(band.getId()));
		}
		
		Collections.sort(bands, new Comparator<Band>() {
			@Override
			public int compare(Band arg0, Band arg1) {
				return arg1.getVotes().compareTo(arg0.getVotes());
			}
		});
		
		int maxVotes = 0;
		for(Band band : bands) {
			if(band.getVotes() > maxVotes) {
				maxVotes = band.getVotes();
			}
		}
		
		List<Band> winners = new LinkedList<>();
		for(Band band : bands) {
			if(band.getVotes() == maxVotes) {
				winners.add(band);
			}
		}
		
		req.getServletContext().setAttribute(bandsAttribute, bands);
		req.getServletContext().setAttribute(winnersAttribute, winners);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
