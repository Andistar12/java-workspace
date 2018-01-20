package com.andy.timestables;

import com.saja.seca.math3d.Vector3f;

import java.awt.*;

public class ColorIterator {

	private Vector3f currentColor = new Vector3f(1,0,0);
	private float h = 0;
	
	public ColorIterator() {}
	
	public void updateColor(float gameSpeed) {
		if (gameSpeed == 0) return;
		h += gameSpeed;
		Color c = Color.getHSBColor(h, 1f, 1f);
		currentColor = new Vector3f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f);
	}
	
	public Vector3f getCurrentColor() { return currentColor; }
	
}
