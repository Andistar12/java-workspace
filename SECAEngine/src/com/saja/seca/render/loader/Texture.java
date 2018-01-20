package com.saja.seca.render.loader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * @author Andy
 *
 * A Texture is just one int, stored in OpenGL
 * It takes in a TextureBlueprint, which is loaded from a CustomLoader
 * 
 * cleanUp() is called by the ResLoader
 */

public class Texture {

	private int textureID = 0;

	public Texture(TextureBlueprint tb) {
		
		// Create one OpenGL texture
		textureID = GL11.glGenTextures();
		
		// "Bind" the newly created texture : all future texture functions will modify this texture
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		// Give the image to OpenGL
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, tb.getWidth(), tb.getWidth(), 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, tb.getBuffer());
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		//TODO customize mipmapping
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);

		tb.cleanUp();
	}
	
	public int getTextureID() { return textureID; }

	public void bindTexture(int bindPoint) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL20.glUniform1i(bindPoint, 0);
	}
	
	public void cleanUp() {
		GL11.glDeleteTextures(textureID);
		textureID = 0; //OpenGL ignores 0 when deleting
	}
	
	@Override
	public String toString() {
		return "SECA Texture with id " + textureID;
	}
}
