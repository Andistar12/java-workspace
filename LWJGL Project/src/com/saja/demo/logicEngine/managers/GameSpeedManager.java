package com.saja.demo.logicEngine.managers;

/**
 * @author Andy Nguyen
 *
 * Regulates game speed. Also calculates FPS
 * 
 * updateSpeed() should only be called once per frame
 */

//TODO clean up
public class GameSpeedManager {
	private long lastTime = 0l, currentTime = 0l;
	//Letter l for long, not number 1
	private long lastFPSTime = 0l, currentFPSTime = 0l;
	private int currentFPS = 0, fpscount = 0;
	
	public static final int FPS_CHECK_SPEED = 1000; //The shorter the more accurate, the longer the less 'spam' to the console
	public static boolean display_FPS = false; //Will display FPS every FPS_CHECK_SPEED if on
	
	private int delta;
	
	public GameSpeedManager(boolean displayFPS) {
		this();
		display_FPS = displayFPS;
	}
	
	public GameSpeedManager() {
		lastTime = getTimeMillis();
		lastFPSTime = getTimeMillis();
	}
	
	public int getFPS() { return currentFPS; }
	public void toggleFPS() { display_FPS = !display_FPS; }
	
	//Should only called once per frame
	public float updateSpeed() {
		//Update the delta from the last frame
		currentTime = getTimeMillis();
		delta = (int) (currentTime - lastTime);
		lastTime = getTimeMillis();
		
		//FPS
		nextFrame();
		
		return getCurrentSpeed();
	}
	public float getCurrentSpeed() { return delta/1000f; }
	
	private void nextFrame() {
		fpscount++;
		currentFPSTime = getTimeMillis();
		if (currentFPSTime >= lastFPSTime + FPS_CHECK_SPEED) {
			currentFPS = fpscount * 1000 / FPS_CHECK_SPEED;
			fpscount = 0;
			lastFPSTime = getTimeMillis();
			
			if (display_FPS) System.out.println("DEMO GameSpeedManager: FPS " + currentFPS);
		}
	}
	
	private static long getTimeMillis() {
		//1 million nanoseconds in a millisecond
	    return System.nanoTime() / 1000000;
	}
	
	
}
