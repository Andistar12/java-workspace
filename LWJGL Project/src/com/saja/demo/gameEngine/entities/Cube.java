package com.saja.demo.gameEngine.entities;

import com.saja.demo.gameEngine.components.ColorComponent;
import com.saja.demo.renderers.GameEntityRenderer;
import com.saja.seca.math3d.Vector3f;
import com.saja.seca.render.components.DrawableModelComponent;
import com.saja.seca.render.components.PositionComponent;
import com.saja.seca.render.components.TextureComponent;
import com.saja.seca.render.loader.Model;
import com.saja.seca.render.loader.ResLoader;

/**
 * @author Andy Nguyen
 *
 * A demonstration of the workings of both Entity and Java reflection
 * See how draw() pulls components out of itself 
 */

public class Cube extends GameEntity {

	public Cube(ResLoader r) {
		super();
		this.addComponent(new ColorComponent(1,1,1));
		
		this.addComponent(new TextureComponent(r, "dice"));
		this.addComponent(new DrawableModelComponent(r, "cube"));
	}
	
	public Model getModel() {
		DrawableModelComponent mc = (DrawableModelComponent) getFirstComponentInstance(DrawableModelComponent.class);
		return mc.getModel();
	}
	
	public Vector3f getColor() { //Easier than doing all the stuff in the method
		ColorComponent cc = (ColorComponent) this.getFirstComponentInstance(ColorComponent.class);
		return cc.getColor();
	}
	
	public void setColor(Vector3f color) {
		ColorComponent cc = (ColorComponent) this.getFirstComponentInstance(ColorComponent.class);
		cc.setColor(color.x, color.y, color.z);
	}
	
	public float getCollisionDistance() {
		DrawableModelComponent mc = (DrawableModelComponent) getFirstComponentInstance(DrawableModelComponent.class);
		return mc.getModel().getCollisionRadius();
	}
	
	public void draw() {
		PositionComponent pc = (PositionComponent) this.getFirstComponentInstance(PositionComponent.class);
		DrawableModelComponent mc = (DrawableModelComponent) this.getFirstComponentInstance(DrawableModelComponent.class);
		TextureComponent tc = (TextureComponent) this.getFirstComponentInstance(TextureComponent.class);
		ColorComponent cc = (ColorComponent) this.getFirstComponentInstance(ColorComponent.class);
		GameEntityRenderer r = (GameEntityRenderer) mc.getModel().getRenderer();
		
		r.setModelMatrix(pc.getModelMatrix());
		r.setTexture(tc.getTexture().getTextureID());
		r.setEntityColor(cc.getColor().x, cc.getColor().y, cc.getColor().z);
		mc.drawTriangles();
	}

	@Override
	protected void entityCleanUp() {}
}
