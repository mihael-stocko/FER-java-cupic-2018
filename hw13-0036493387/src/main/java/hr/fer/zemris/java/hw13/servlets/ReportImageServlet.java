package hr.fer.zemris.java.hw13.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
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
 * This servlet generates a pie chart showing results of a survey and sends it to 
 * response's output stream.
 * 
 * @author Mihael Stočko
 *
 */
public class ReportImageServlet extends HttpServlet {

	public static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DefaultPieDataset dataset = new DefaultPieDataset();
	    dataset.setValue("Linux", 29);
	    dataset.setValue("Mac", 20);
	    dataset.setValue("Windows", 51);

	    JFreeChart chart = createChart(dataset, "OS survey results");
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
