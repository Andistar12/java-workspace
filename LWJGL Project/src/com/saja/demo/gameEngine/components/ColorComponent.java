package com.saja.demo.gameEngine.components;

import com.saja.seca.back.Component;
import com.saja.seca.math3d.Vector3f;


/**
 * @author Andy Nguyen
 *
 * All entities (should) have a color component, 
 * which gives the color to be sent into the EntityRenderer
 */

public class ColorComponent extends Component {

	private Vector3f color;
	
	public ColorComponent(float r, float g, float b) {
		setColor(r, g, b);
	}
	
	public ColorComponent() {
		setDefaultColor();
	}
	
	public void setColor(float r, float g, float b) {
		color = new Vector3f(r, g, b);
	}
	
	public void setDefaultColor() {
		color = new Vector3f(1, 1, 1);
	}
	
	public Vector3f getColor() { return color; }
	
	@Override
	protected void componentCleanUp() {
		color = null;
	}
	
}
