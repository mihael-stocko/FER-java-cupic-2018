package hr.fer.zemris.java.hw17;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is used to retrieve concrete images from the directory. It takes the image title
 * as a parameter.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servlets/getImage")
public class ImageProvider extends HttpServlet {
	
	public static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		String extension = title.substring(title.lastIndexOf(".")+1);
		
		BufferedImage image = null;
		try {
			File file = new File(req.getServletContext().getRealPath("/WEB-INF/slike/" + title).toString());
			image = ImageIO.read(file);
		} catch (IOException e) {
			return;
		}
		
		resp.setContentType("image/" + extension);
	    ImageIO.write(image, extension.toUpperCase(), resp.getOutputStream());
	}
}