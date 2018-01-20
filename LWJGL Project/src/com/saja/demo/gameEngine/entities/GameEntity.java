package com.saja.demo.gameEngine.entities;

import com.saja.seca.back.Component;
import com.saja.seca.back.Entity;
import com.saja.seca.math3d.Matrix4f;
import com.saja.seca.render.components.PositionComponent;

/**
 * @author Andy Nguyen
 * 
 * All gameEngine entities subclass this, and so all are guaranteed a position 
 * Inheritance in Entity/Component Setups only guarantee presence of components
 */

public abstract class GameEntity extends Entity {

	public GameEntity() {
		this.addComponent(new PositionComponent());
	}

	public Matrix4f getModelM() {
		PositionComponent pc = (PositionComponent) this.getFirstComponentInstance(PositionComponent.class);
		return pc.getModelMatrix();
	}
	
	@Override
	public boolean isComponentAddable(Component c) {
		return true; //Able to add all components, can be overridden again by subclasses
	}
	
	protected abstract void entityCleanUp(); //Subclasses still have to address this
}
