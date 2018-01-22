package net.zbgames;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class GameGraphics extends Canvas{
	private static final long serialVersionUID = 1L;
	//public static final int GAME_WIDTH = 500;
	//public static final int GAME_HEIGHT = 500;
	public boolean positiveX = true, positiveY = true;
	public int locX, locY, paddleLoc, mouseLocX, mouseLocY, score = 0;
	public static final int increment = 10,
							padding = 5,
							ballDiameter = 25,
							tail = 6,
							tailIncrement = 9,
							paddleSize = 200,
							paddlePadding = 100;
	public GameGraphics(){
		resetValues();
		addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent e) {}
			public void mouseMoved(MouseEvent e) {
				mouseLocX = e.getX() - paddleSize / 2;
				mouseLocY = e.getY();
				if (e.getX() < paddleSize/2) mouseLocX = paddleSize/2;
				if (e.getX() > getWidth() - paddleSize) mouseLocX = getWidth() - paddleSize;
				if (e.getY() < 0) mouseLocY = 0;
				if (e.getY() > getHeight() - paddleSize) mouseLocY = getHeight() - paddleSize;
			}
		});
	}
	
	public void paint(int status){
		if (getBufferStrategy() == null) createBufferStrategy(2);
		BufferStrategy bs = getBufferStrategy();
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Times New Roman", Font.PLAIN, 60));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		if (status == 0) {
			if (positiveY &&
					locY > getHeight() - paddlePadding - ballDiameter &&
					locY < getHeight() - paddlePadding &&
					locX > mouseLocX && 
					locX < mouseLocX + paddleSize) {
				positiveY = !positiveY;
				score++;
			}
			
			if (locX < 0 + padding || locX > getWidth() - padding - ballDiameter) positiveX = !positiveX;
			if (locY < 0 + padding) positiveY = !positiveY;
			locX += positiveX ? increment : -1 * increment;
			locY += positiveY ? increment : -1 * increment;
			g2d.setColor(Color.GRAY);
			
			int x = locX, y = locY;
			boolean posX = !positiveX, posY = !positiveY;
			for (int i = 1; i < tail; i++){
				if (x < 0 + padding || x > getWidth() - padding - ballDiameter) posX = !posX;
				if (y < 0 + padding || y > getHeight() - padding - ballDiameter) posY = !posY;
				x += posX ? tailIncrement : -1 * tailIncrement;
				y += posY ? tailIncrement : -1 * tailIncrement;
				g2d.fillOval(x, y, ballDiameter, ballDiameter);
			}
			g2d.setColor(Color.WHITE);
			g2d.fillOval(locX, locY, ballDiameter, ballDiameter);
			g2d.fillRect(mouseLocX, getHeight() - paddlePadding, paddleSize, 5);
		}
		g2d.setColor(Color.WHITE);
		g2d.drawString("Score: " + score, 1 , 50);
		if (status == 1) g2d.drawString("P A U S E D", 20, getHeight()/2 - g2d.getFont().getSize());
		if (status == 2) g2d.drawString("G A M E   O V E R", 20, getHeight()/2 - g2d.getFont().getSize());
		
		g2d.dispose();
		bs.show();
	}
	
	public void resetValues() {
		Random random = new Random();
		locX = 100;
		locY = 100;
		positiveX = random.nextBoolean();
		positiveY = random.nextBoolean();
	}
}
