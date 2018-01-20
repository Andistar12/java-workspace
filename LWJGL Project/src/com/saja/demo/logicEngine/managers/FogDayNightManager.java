package com.saja.demo.logicEngine.managers;

/**
 * @author Andy Nguyen
 * 
 * Pulled out from my last project
 */

//TODO clean up
public class FogDayNightManager {

	public static final int TICKS_PER_DAY = 2400, INCREMENT = 75; //Per second
	//Length of day = TICKS_PER_DAY / INCREMENT
	
	public static final float MIN_FOG = 0f, MAX_FOG = 1f;
	
	public static final float SUNRISE = 530, DAY_LENGTH = 1400, SUNLIGHT_STRENGTH = 5;
	
	private float time;
	private float currentColor;
	
	public FogDayNightManager(int startTime) {
		time = startTime;
		update(0); //Init
	}
	
	public void update(float speed) {
		time += INCREMENT * speed;
		time %= TICKS_PER_DAY;
		//Invariant: time is always positive and in the range of [0, TICKS_PER_DAY)
		
		if (time <= SUNRISE | time >= SUNRISE + DAY_LENGTH) {
			//Night time according to our model, don't bother doing calculations
			//You can actually check this check, because the bottom function will return the same result
			currentColor = MIN_FOG;
		} else {
			currentColor = (float) Math.sin((Math.PI * (time - SUNRISE) / DAY_LENGTH));
			currentColor = Math.max(0, currentColor); //Keep current color > 0
			currentColor = (float) Math.pow(currentColor, 7 / SUNLIGHT_STRENGTH);
			
			currentColor = Math.max(Math.min(MAX_FOG, currentColor), MIN_FOG);
		}
	}
	
	public float getCurrentColor() { return currentColor; }
	public float getTime() { return time; }
	
	public float getClockRotation() {
		return 360f * -(time) * 2f / TICKS_PER_DAY;
	}
	
	public void reset() {
		time = 0;
	}
}
