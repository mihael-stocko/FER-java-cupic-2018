package hr.fer.zemris.java.hw17;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is used to retrieve concrete thumbnails from the directory. It takes the image title
 * as a parameter.
 * 
 * @author Mihael Stočko
 *
 */
@WebServlet("/servlets/getThumbnail")
public class ThumbnailProvider extends HttpServlet {

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
		
		java.nio.file.Path p = Paths.get(req.getServletContext().getRealPath("/WEB-INF/thumbnails"));
		if(!(p.toFile().exists() && p.toFile().isDirectory())) {
			Files.createDirectories(p);
		}
		
		File outputfile = new File(req.getServletContext().getRealPath("/WEB-INF/thumbnails/" + title));
		BufferedImage resizedImage = null;
		if(!outputfile.exists()) {
			int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();

			resizedImage = resizeImage(image, type, 150, 150);
    	
			ImageIO.write(resizedImage, title.substring(title.lastIndexOf(".")+1), outputfile);
		
		} else {
			File file = new File(req.getServletContext().getRealPath("/WEB-INF/thumbnails/" + title).toString());
			resizedImage = ImageIO.read(file);
		}
		
		resp.setContentType("image/" + extension);
	    ImageIO.write(resizedImage, extension.toUpperCase(), resp.getOutputStream());
	}
	
	/**
	 * This method resizes the given image to the given dimensions.
	 * 
	 * @param originalImage Original image
	 * @param IMG_WIDTH Desired width
	 * @param IMG_HEIGHT Desired height
	 * @return Resized image
	 */
	private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
	    BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
	    g.dispose();

	    return resizedImage;
	}
}
