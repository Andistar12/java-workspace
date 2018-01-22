package net.zbgames;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class GameGraphics extends Canvas {
	private static final long serialVersionUID = -6374540448066980874L;
	public static Font textFont = new Font("Times New Roman", Font.PLAIN, 45);
	public int diameter = 50;
	public Color squareColor = Color.BLUE;
	public GameGraphics(){}
	
	public int getDiameter() {
		return diameter;
	}
	
	public void paint(ArrayList<Point> pointList, int score, double alive){
		try {
			if (getBufferStrategy() == null) createBufferStrategy(2);
			BufferStrategy bs = getBufferStrategy();
			Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
			
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			
			g2d.setColor(Color.WHITE);
			g2d.setStroke(new BasicStroke(5));
			g2d.setFont(textFont);
			g2d.drawLine(0, getHeight() - textFont.getSize(), getWidth(), getHeight() - textFont.getSize());
			g2d.drawString("Square Collector v1.0 DEV   |   Score: " + score + "   |   Alive time (secs): " + alive, 1, getHeight() - 8);

			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(squareColor);
			if (!pointList.isEmpty()) {
				for (Point p: pointList) {
					g2d.drawRect(p.x, p.y, diameter, diameter);
				}
			}
		
			g2d.dispose();
			bs.show();
		} catch (ConcurrentModificationException cme) {	
		} catch (NullPointerException e){
			//Should come from g2d creation attempt, just swallow
		}
	}
	public Color getTimeColor(int seconds) {
		switch (seconds) {
		case 0:
		case 1:
		case 2:
		case 3:
			return Color.GREEN;
		case 4:
		case 5:
		case 6: 
		case 7:
			return Color.YELLOW;
		}
		return Color.RED;
	}
}
