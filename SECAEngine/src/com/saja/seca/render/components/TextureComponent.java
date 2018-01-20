package com.saja.seca.render.components;

import com.saja.seca.back.Component;
import com.saja.seca.render.loader.ResLoader;
import com.saja.seca.render.loader.Texture;

/**
 * @author Andy Nguyen
 * 
 * Points to one Texture that could be set in a Renderer
 * Calling cleanUp() doesn't clean up the model; only the ResLoader can clear out models and textures
 * 
 * The methods & structure in DrawableModelComponent are similar to TextureComponent
 * 
 */

public class TextureComponent extends Component {

	private Texture texture = null;
	
	public TextureComponent(ResLoader r, String textureName) {
		this(r.getTexture(textureName));
	}
	
	public TextureComponent(Texture t) {
		texture = t;
	}
	
	public Texture getTexture() { return texture; }
	public void bindTexture(int bindPoint) { getTexture().bindTexture(bindPoint); }
	
	@Override
	protected void componentCleanUp() {
		texture = null;
	}
	
	@Override
	public String toString() {
		if (texture == null) return "SECA TextureComponent with no set Texture";
		return "SECA TextureComponent with one set Texture";
	}

}
