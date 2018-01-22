package net.zbgames;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

public class SpeedClick extends Applet implements Runnable{
	public static final long serialVersionUID = 871606640544724364L;
	public static final int spawnDelay = 250,
			deathStart = 7;
	public boolean running = false, masterRun = false;
	public Thread gameLoop, addPointLoop, colorThread, scoreUpdate;
	public GameGraphics graphics;
	public int score = 1;
	public double alive = 0.0;
	
	public ArrayList<Point> circleLists;
	
	public void init(){
		setLayout(new BorderLayout());
		graphics = new GameGraphics();
		add(graphics);
		circleLists = new ArrayList<Point>();
		graphics.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent arg0) {}
			public void mouseMoved(MouseEvent e) {
				checkPoint(e.getPoint());
			}
		});
		graphics.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {
				restart();
			}
		});
		gameLoop = new Thread(this, "Paint Thread");
		addPointLoop = new Thread(new Runnable(){
			public void run(){
				while (masterRun) {
					while (running){
						try {
							Thread.sleep(spawnDelay);
							addPoint();
						} catch (IndexOutOfBoundsException ioobe){
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, "Spawner Thread");
		colorThread = new Thread(new Runnable(){
			@Override
			public void run() {
				while(masterRun) {
					while (running) {
						try {
							Thread.sleep(3000);
							graphics.squareColor = new Color((new Random()).nextInt(0xFFFFFF));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		scoreUpdate = new Thread(new Runnable(){
			@Override
			public void run() {
				while (masterRun) {
					while (running) {
						try {
							Thread.sleep(1000);
							if (circleLists.size() > 7) score -= circleLists.size() - 7;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		running = true;
		masterRun = true;
		gameLoop.start();
		addPointLoop.start();
		colorThread.start();
		scoreUpdate.start();
	}
	
	public void start(){}
	public void stop(){
		running = false;
		masterRun = false;
	}
	public void destroy(){}
	
	public void restart() {
		running = false;
		circleLists.clear();
		score = 1;
		alive = 0.0;
		running = true;
	}
	@Override
	public void run() {
		while (masterRun) {
			while (running){
				try {
					Thread.sleep(10);
					alive += 0.01;
					alive = round(alive, 2);
					checkLoss();
					graphics.paint(circleLists, score, alive);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void checkLoss() {
		if (score <= 0) running = false;
	}
	
	public void checkPoint(Point p) {
		try {
			for (Point i: circleLists) {
				if (p.getX() >= i.getX() && p.getX() <= i.getX() + graphics.getDiameter() &&
					p.getY() >= i.getY() && p.getY() <= i.getY() + graphics.getDiameter()) {
					score++;
					circleLists.remove(i);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addPoint(){
		try {
			Random r = new Random();
			Point p = new Point();
			p.x = r.nextInt(graphics.getWidth() - GameGraphics.textFont.getSize() - graphics.getDiameter());
			p.y = r.nextInt(graphics.getHeight() - GameGraphics.textFont.getSize() - graphics.getDiameter());
			circleLists.add(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
