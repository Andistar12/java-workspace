package com.saja.demo.gameEngine.entities;

import com.saja.seca.math3d.Vector2f;
import com.saja.seca.render.components.PositionComponent;
import com.saja.seca.render.loader.ResLoader;

/**
 * @author Andy Nguyen
 * 
 * Extends GuiEntity and adds a Runnable
 * A GuiButton is a drawable square that can be "clicked"
 * 
 * Calling checkInput(args) will return whether a normalized position (based on the OpenGL coords)
 * 		is inside the drawing
 * If so, it will return true. Calling run() or click() to 'click' the button must be called elsewhere 
 */

public abstract class GuiButton extends GuiEntity {

	public GuiButton(ResLoader r, String textureName) {
		super(r, textureName);
	}
	
	public boolean checkInput(Vector2f normalizedPosition, float aspectRatio) {
		//Checks whether the position is inside the button
		//We do a rectangle-based bounds check
		PositionComponent pc = (PositionComponent) this.getFirstComponentInstance(PositionComponent.class);
		
		float xLength = pc.getScale().x;
		float yLength = pc.getScale().y * aspectRatio;
		float xCenter = pc.getPosition().x;
		float yCenter = pc.getPosition().y * aspectRatio;
		
		if (normalizedPosition.x >= xCenter - xLength / 2f && normalizedPosition.x <= xCenter + xLength / 2f ) {
			if (normalizedPosition.y >= yCenter - yLength / 2f && normalizedPosition.y <= yCenter + yLength / 2f ) {
				//We are inside the square
				return true;
			}
		}
		return false;
	}
	
	protected abstract void buttonAction();
	
	public void click() {
		buttonAction();
	}
}
