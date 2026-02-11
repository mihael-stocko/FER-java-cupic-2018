package hr.fer.zemris.java.hw13.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * This servlet reads a list of bands from an attribute and generates a pie chart
 * which it sends to response's output stream.
 * 
 * @author Mihael Stočko
 *
 */
public class VotesImageServlet extends HttpServlet {

	public static final long serialVersionUID = 1L;
	
	/**
	 * List of bands attribute name
	 */
	public static final String bandsAttribute = "bandsVotes";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		List<Band> bands = (List<Band>)req.getServletContext().getAttribute(bandsAttribute);
		
		for(Band band : bands) {
			dataset.setValue(band.getName(), band.getVotes());
		}
		
	    JFreeChart chart = createChart(dataset, "Rezultati glasanja za najbolji bend");
	    BufferedImage objBufferedImage = chart.createBufferedImage(500, 400);
	    
	    resp.setContentType("image/png");
	    ImageIO.write(objBufferedImage, "PNG", resp.getOutputStream());
	}
	
	/**
	 * An auxiliary method used for creating a pie chart.
	 * @param dataset Data for the chart
	 * @param title Title of the chart
	 * @return Created pie chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }
}
