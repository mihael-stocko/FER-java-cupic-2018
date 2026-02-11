package servlets;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/igra/slika")
public class Slika extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		
		int width = 500;
	    int height = 50;
	    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	    Graphics2D g = (Graphics2D) image.getGraphics();
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, width, height);
	    
	    int id = Integer.parseInt(req.getParameter("id"));
	    int brojPokusaja = IgraStart.listaIgara.get(id).brojPokusaja;
	    int istroseno = 7 - brojPokusaja;
	    
	    
	    g.setColor(Color.BLUE);
	    g.fillRect(0, 0, (width/7)*(7-brojPokusaja), height);
	    
	    g.setColor(Color.YELLOW);
	    
	    
	    NumberFormat formatter = new DecimalFormat("#0.00%");
	    String s = formatter.format(istroseno/7.0);
	    FontMetrics fm = g.getFontMetrics();
	    int x = (width - fm.stringWidth(s)) / 2;
	    int y = (fm.getAscent() + (height - (fm.getAscent() + fm.getDescent())) / 2);
	    g.drawString(s, x, y);
	    
	     
	    
	    
	    
	    ImageIO.write(image, "PNG", resp.getOutputStream());
	}
}
