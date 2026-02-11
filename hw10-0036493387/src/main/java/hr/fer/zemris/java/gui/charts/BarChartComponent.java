package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * This component is a bar chart that can be supplied with necessary data 
 * using a BarChart object.
 * 
 * @author Mihael Stočko
 *
 */
public class BarChartComponent extends JComponent {
	
	public static final long serialVersionUID = 1L;

	/**
	 * Gap between axis and numbers & numbers and title
	 */
	private static final int fixedGap = 5;
	
	/**
	 * Space reserved for arrows
	 */
	private static final int fixedOffset = 10;
	
	/**
	 * BarChart object with necessary data
	 */
	BarChart chart;
	
	/**
	 * Constructor. Takes a BarChart object and stores it.
	 * Also sets minimum size of the component.
	 * 
	 * @param chart BarChart object
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
		setMinimumSize(new Dimension(300, 300));
	}
	
	/**
	 * Paints the component onto the available space.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();
		Dimension dim = getSize();
		Rectangle r = new Rectangle(
				ins.left, 
				ins.top, 
				dim.width-ins.left-ins.right,
				dim.height-ins.top-ins.bottom);
		if(isOpaque()) {
			g.setColor(Color.WHITE);
			g.fillRect(r.x, r.y, r.width, r.height);
		}
		g.setColor(Color.BLACK);
		FontMetrics fm = g.getFontMetrics();
		
		
		
		int amountY = (chart.yMax - chart.yMin)/chart.step + 1;
		int numWidth1 = fm.stringWidth(Integer.toString(chart.yMin));
		int numWidth2 = fm.stringWidth(Integer.toString(chart.yMax));
		int numWidth = numWidth1 > numWidth2 ? numWidth1 : numWidth2;
		
		int yCurrentX = r.x + fm.getAscent() + fixedGap;
		int yCurrentY = r.y + r.height - fm.getAscent()*2 - fixedGap*2;
		int yFinalY = r.y + fixedOffset;
		int gap = -(yFinalY - yCurrentY)/(amountY-1);
		
		for(int i = 0; i < amountY; i++) {
			String num = Integer.toString(chart.yMin + i*chart.step);
			g.drawString(num, yCurrentX+numWidth-fm.stringWidth(num), yCurrentY - i*gap + fm.getAscent()/2);
		}
		
		
		
		int amountX = chart.values.size();
		int slotWidth = (r.width - fixedOffset - 2*fixedGap - fm.getAscent() - numWidth)/amountX;
		
		int xCurrentX = r.x + fm.getAscent() + numWidth + fixedGap*2;
		int xCurrentY = r.y + r.height - fixedGap - fm.getAscent();
		
		for(int i = 0; i < amountX; i++) {
			String value = Integer.toString(chart.getValues().get(i).getX());
			g.drawString(value, xCurrentX + slotWidth/2 - fm.stringWidth(value)/2, xCurrentY);
			xCurrentX += slotWidth;
		}
		
		
		
		g.drawLine(r.x + fm.getAscent() + fixedGap*2 + numWidth, r.y + fixedOffset, 
				r.x + fm.getAscent() + fixedGap*2 + numWidth, r.y + r.height - fm.getAscent()*2 - fixedGap*2);
		g.drawLine(r.x + fm.getAscent() + fixedGap*2 + numWidth, r.y + fixedOffset, 
				r.x + fm.getAscent() + fixedGap*2 + numWidth, r.y + fixedOffset - 5);
		g.drawLine(r.x + fm.getAscent() + fixedGap*2 + numWidth, r.y + fixedOffset - 5, 
				r.x + fm.getAscent() + fixedGap*2 + numWidth - 5, r.y + fixedOffset);
		g.drawLine(r.x + fm.getAscent() + fixedGap*2 + numWidth, r.y + fixedOffset - 5, 
				r.x + fm.getAscent() + fixedGap*2 + numWidth + 5, r.y + fixedOffset);
		g.drawLine(r.x + fm.getAscent() + fixedGap*2 + numWidth, r.y + r.height - fm.getAscent()*2 - fixedGap*2, 
				r.x + r.width - fixedOffset, r.y + r.height - fm.getAscent()*2 - fixedGap*2);
		g.drawLine(r.x + r.width - fixedOffset, r.y + r.height - fm.getAscent()*2 - fixedGap*2, 
				r.x + r.width - fixedOffset + 5, r.y + r.height - fm.getAscent()*2 - fixedGap*2);
		g.drawLine(r.x + r.width - fixedOffset + 5, r.y + r.height - fm.getAscent()*2 - fixedGap*2, 
				r.x + r.width - fixedOffset, r.y + r.height - fm.getAscent()*2 - fixedGap*2 - 5);
		g.drawLine(r.x + r.width - fixedOffset + 5, r.y + r.height - fm.getAscent()*2 - fixedGap*2, 
				r.x + r.width - fixedOffset, r.y + r.height - fm.getAscent()*2 - fixedGap*2 + 5);
		
		
		
		int yX = r.x + fm.getAscent() + fixedGap*2 + numWidth;
		int yStartY = r.y + r.height - fm.getAscent()*2 - fixedGap*2;
		int yEndY = r.y + fixedOffset;
		int yDiff = (yEndY - yStartY)/(amountY-1);
		for(int i = 0; i < amountY; i++) {
			g.drawLine(yX, yStartY + i*yDiff, r.width - fixedOffset, yStartY + i*yDiff);
		}
		
		
		
		int xY = r.height - fixedGap*2 - fm.getAscent()*2;
		int xStartX = r.x + fm.getAscent() + fixedGap*2 + numWidth;
		int xEndX = r.width - fixedOffset;
		int xDiff = (xEndX - xStartX)/(amountX);
		for(int i = 0; i <= amountX; i++) {
			g.drawLine(xStartX + i*xDiff, xY, xStartX + i*xDiff, fixedOffset);
		}
		
		
		
		g.setColor(Color.ORANGE);
		yDiff = -yDiff;
		for(int i = 0; i < amountX; i++) {
			int value = (chart.getValues().get(i).getY() - chart.yMin)*yDiff;
			
			g.fillRect(xStartX + xDiff*i, xY - value/2, 
					xDiff, value/2);
		}
		
		
		
		g.setColor(Color.BLACK);
		g.drawString(chart.xAxis, r.x + (r.width-fm.stringWidth(chart.xAxis))/2, r.y+r.height-fm.getAscent()/2);
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2.setTransform(at);
		g2.drawString(chart.yAxis, -r.y - r.height/2 - fm.stringWidth(chart.yAxis), r.x + fm.getAscent());
	}
}
