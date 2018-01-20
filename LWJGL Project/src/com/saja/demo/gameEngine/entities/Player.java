package com.saja.demo.gameEngine.entities;

import com.saja.seca.back.Component;
import com.saja.seca.back.Entity;
import com.saja.seca.math3d.Matrix4f;
import com.saja.seca.math3d.Vector3f;
import com.saja.seca.render.components.ViewpointComponent;

/**
 * @author Andy Nguyen
 * 
 * Player class adds a ViewpointComponent to itself. All arguments are passed directly to the VPC
 */

public class Player extends Entity {
	
	//Since this component is used so often per frame, we just make a direct pointer to it
	private ViewpointComponent vpc;
	
	public Player(Vector3f pos, float pitch, float yaw, float roll, int width, int height, float nearPlane, float farPlane, int FOV, float camRadius) {
		vpc = new ViewpointComponent(pos, pitch, yaw, roll, width, height, nearPlane, farPlane, FOV, camRadius);
		this.addComponent(vpc);
	}

	public ViewpointComponent getViewpointComponent() { return vpc; }
	public Matrix4f getViewProjectionMatrix() { return getViewpointComponent().getViewProjectionMatrix(); }
	
	@Override
	public boolean isComponentAddable(Component c) {
		return true;
	}

	@Override
	protected void entityCleanUp() {
		vpc = null;
	}

}
