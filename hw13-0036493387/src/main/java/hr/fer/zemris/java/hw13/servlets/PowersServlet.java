package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet takes three parameters: a, b and n.
 * a - starting number [-100, 100]
 * b - ending number [-100, 100]
 * n - number of sheets
 * 
 * It makes a xls file with n sheets. On each sheet, numbers from a to b are raised to the power of
 * i, i being the index of the sheet. The xls document is sent to response's output stream.
 * 
 * @author Mihael Stočko
 *
 */
public class PowersServlet extends HttpServlet {

	public static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String param1 = req.getParameter("a");
		String param2 = req.getParameter("b");
		String param3 = req.getParameter("n");
		if(param1 == null || param2 == null || param3 == null) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
		int a = 0;
		int b = 0;
		int n = 0;
		try {
			a = Integer.parseInt(param1);
			b = Integer.parseInt(param2);
			n = Integer.parseInt(param3);
		} catch(NumberFormatException e) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
		if(!(a >= -100 && a <= 100 && b >= -100 && b <= 100 && n >= 1 && n <= 5)) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		for(int i = 0; i < n; i++) {
			HSSFSheet sheet = hwb.createSheet(Integer.valueOf(i).toString());
			
			HSSFRow rowhead = sheet.createRow((short)0);
			rowhead.createCell((short)0).setCellValue("Number");
			rowhead.createCell((short)1).setCellValue("Number^" + i);
			
			for(int j = a; j <= b; j++) {
				HSSFRow row = sheet.createRow((short)j-a+1);
				row.createCell((short) 0).setCellValue(j);
				row.createCell((short) 1).setCellValue(Math.pow(j, i));
			}
		}
		
		resp.setContentType("application/vnd.ms-excel");
		hwb.write(resp.getOutputStream());
		hwb.close();
	}
}
