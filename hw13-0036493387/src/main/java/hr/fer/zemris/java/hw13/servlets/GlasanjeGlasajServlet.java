package hr.fer.zemris.java.hw13.servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet reads information about bands and voting results from files. It increments
 * the number of votes for the band whose id is given through a parameter by one and then
 * writes the voting results back to the file they were read from. It then triggers the servlet
 * {@link GlasanjeRezultatiServlet}.
 * 
 * @author Mihael Stočko
 *
 */
public class GlasanjeGlasajServlet extends HttpServlet {

	public static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = 0;
		try {
			id = Integer.parseInt(req.getParameter("id"));
		} catch(NumberFormatException e) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
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
		
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		Map<Integer, Integer> map = new HashMap<>();
		for(String line : lines) {
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
		
		map.put(id, map.get(id) + 1);
		
		Paths.get(fileName).toFile().delete();
		Paths.get(fileName).toFile().createNewFile();
		OutputStream os = new FileOutputStream(fileName);
		StringBuilder sb = new StringBuilder();
		map.forEach((k, v) -> {
			sb.append(k);
			sb.append('\t');
			sb.append(v);
			sb.append('\r');
			sb.append('\n');
		});
		sb.setLength(sb.length()-2);
		os.write(sb.toString().getBytes());
		os.close();
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
