package net.zbgames;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RoundShot extends Applet implements Runnable, MouseListener{
	private static final long serialVersionUID = 1L;
	private boolean running, masterStop = true;
	public GameGraphics gameGraphics;
	public Thread gameLoop;
	public int statusFlag = 0;
	public void init(){
		setLayout(new BorderLayout());
		gameGraphics = new GameGraphics();
		gameGraphics.addMouseListener(this);
		add(gameGraphics, BorderLayout.CENTER);
		running = true;
		gameLoop = new Thread(this);
		gameLoop.start();
	}
	
	public void start(){}
	public void stop(){
		running = false;
		masterStop = false;
		statusFlag = 2;
	}
	public void destroy(){}
	@Override
	public void run() {
		while(masterStop) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (running){
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				checkStop();
				if (running) gameGraphics.paint(statusFlag);
			}
			if (masterStop) gameGraphics.paint(statusFlag);
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {
		running = !running;
		if (statusFlag == 0) {
			statusFlag = 1;
			return;
		}
		if (statusFlag == 2) gameGraphics.resetValues();
		if (statusFlag == 1 || statusFlag == 2) statusFlag = 0;

	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	public void checkStop(){
		if (gameGraphics.locY > gameGraphics.getHeight()){
			statusFlag = 2;
			running = !running;
		}
	}
}
