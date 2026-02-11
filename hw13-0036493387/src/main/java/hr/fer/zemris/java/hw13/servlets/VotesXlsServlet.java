package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet reads a list of bands from an attribute and generates an xls document
 * which it sends to response's output stream.
 * 
 * @author Mihael Stočko
 *
 */
public class VotesXlsServlet extends HttpServlet {

	public static final long serialVersionUID = 1L;
	
	/**
	 * List of bands attribute name
	 */
	public static final String bandsAttribute = "bandsVotes";
	
	/**
	 * Voting results attribute name
	 */
	public static final String resAttribute = "results";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Band> bands = (List<Band>)req.getServletContext().getAttribute(bandsAttribute);
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet(resAttribute);
		HSSFRow rowhead = sheet.createRow((short)0);
		rowhead.createCell((short)0).setCellValue("Band name");
		rowhead.createCell((short)1).setCellValue("Number of votes");
		
		for(int i = 0; i < bands.size(); i++) {
			HSSFRow row = sheet.createRow((short)i+1);
			row.createCell((short) 0).setCellValue(bands.get(i).getName());
			row.createCell((short) 1).setCellValue(bands.get(i).getVotes());
		}
		
		resp.setContentType("application/vnd.ms-excel");
		hwb.write(resp.getOutputStream());
		hwb.close();
	}
}
