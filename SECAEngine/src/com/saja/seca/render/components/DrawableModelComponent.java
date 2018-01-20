package com.saja.seca.render.components;

import com.saja.seca.back.Component;
import com.saja.seca.render.loader.Model;
import com.saja.seca.render.loader.ResLoader;

/**
 * @author Andy Nguyen
 *
 * Points to one Model that can be rendered
 * Calling cleanUp() doesn't clean up the model; only the ResLoader can clear out models and textures
 * 
 * The methods & structure in DrawableModelComponent are similar to TextureComponent
 */

public class DrawableModelComponent extends Component {

	private Model m = null;
	
	public DrawableModelComponent(ResLoader r, String modelName) {
		this(r.getModel(modelName));
	}
	
	public DrawableModelComponent(Model m) {
		this.m = m;
	}
	
	public Model getModel() { return m; }
	public void drawLines() { getModel().drawLines(); }
	public void drawTriangles() { getModel().drawTriangles(); }
	public void drawPoints() { getModel().drawPoints(); }

	@Override
	protected void componentCleanUp() {
		m = null;
	}
	
	@Override
	public String toString() {
		if (m == null) return "SECA DrawableModelComponent with no set Model";
		return "SECA DrawableModelComponent with one set Model";
	}
}
