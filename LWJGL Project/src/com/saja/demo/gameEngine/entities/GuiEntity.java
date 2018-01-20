package com.saja.demo.gameEngine.entities;

import com.saja.demo.gameEngine.components.ColorComponent;
import com.saja.demo.renderers.GuiRenderer;
import com.saja.seca.render.components.DrawableModelComponent;
import com.saja.seca.render.components.PositionComponent;
import com.saja.seca.render.components.TextureComponent;
import com.saja.seca.render.loader.ResLoader;

/**
 * @author Andy Nguyen
 * 
 * One square that can be drawn as a GUI and can be added to a GuiSystem
 */

public class GuiEntity extends GameEntity {

	public static final String model_name = "gui";
	
	public GuiEntity(ResLoader r, String textureName) {
		super();
		
		this.addComponent(new TextureComponent(r, textureName));
		this.addComponent(new DrawableModelComponent(r, model_name));
		this.addComponent(new ColorComponent(1,1,1));
	}
	
	public void draw() {
		PositionComponent pc = (PositionComponent) this.getFirstComponentInstance(PositionComponent.class);
		DrawableModelComponent mc = (DrawableModelComponent) this.getFirstComponentInstance(DrawableModelComponent.class);
		TextureComponent tc = (TextureComponent) this.getFirstComponentInstance(TextureComponent.class);
		ColorComponent cc = (ColorComponent) this.getFirstComponentInstance(ColorComponent.class);
		GuiRenderer r = (GuiRenderer) mc.getModel().getRenderer();
		
		r.setColor(cc.getColor());
		r.setModelMatrix(pc.getModelMatrix());
		r.setTexture(tc.getTexture().getTextureID());
		mc.drawTriangles();
	}
	
	@Override
	protected void entityCleanUp() {}
}
