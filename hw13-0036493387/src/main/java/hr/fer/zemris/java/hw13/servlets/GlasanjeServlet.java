package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet reads information about bands from a file and sets a list of bands
 * as a global attribute. It calls the jsp that shows a link for each band.
 * 
 * @author Mihael Stočko
 *
 */
public class GlasanjeServlet extends HttpServlet {
	
	public static final long serialVersionUID = 1L;
	
	/**
	 * List of bands attribute name
	 */
	public static final String bandsAttribute = "bands";
	
	/**
	 * Number of bands
	 */
	public static int numOfBands = 0;

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
		
		Collections.sort(bands, new Comparator<Band>() {
			@Override
			public int compare(Band arg0, Band arg1) {
				return arg0.getId().compareTo(arg1.getId());
			}
		});
		
		numOfBands = bands.size();
		
		req.getServletContext().setAttribute(bandsAttribute, bands);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}