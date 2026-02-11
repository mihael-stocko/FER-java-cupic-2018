package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * This servlet reads a list of options from an attribute and generates an xls document
 * which it sends to response's output stream.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class XlsServlet extends HttpServlet {

	public static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<PollOption> options = DAOProvider.getDao().
				getPollOptionsForPoll(Long.parseLong(req.getParameter("pollID")));
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("results");
		HSSFRow rowhead = sheet.createRow((short)0);
		rowhead.createCell((short)0).setCellValue("Option title");
		rowhead.createCell((short)1).setCellValue("Number of votes");
		
		for(int i = 0; i < options.size(); i++) {
			HSSFRow row = sheet.createRow((short)i+1);
			row.createCell((short) 0).setCellValue(options.get(i).getOptionTitle());
			row.createCell((short) 1).setCellValue(options.get(i).getVotesCount());
		}
		
		resp.setContentType("application/vnd.ms-excel");
		hwb.write(resp.getOutputStream());
		hwb.close();
	}
}
