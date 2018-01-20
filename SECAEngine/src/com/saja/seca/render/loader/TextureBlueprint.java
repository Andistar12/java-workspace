package com.saja.seca.render.loader;

import java.nio.ByteBuffer;

/**
 * @author Andy Nguyen
 *
 * TextureBlueprint circumvents the issue of not being able to access the 
 * 		width and height of an image stored as a ByteBuffer
 * It is created from a CustomLoader and then loaded into OpenGL (as a Texture)
 * 
 * cleanUp() is called in Texture
 */

public class TextureBlueprint {
	
	private ByteBuffer buffer;
	private int width, height;
	
	public TextureBlueprint(ByteBuffer buffer, int width, int height) {
		this.buffer = buffer;
		this.width = width;
		this.height = height; 
	}
	
	public void cleanUp() {
		buffer.clear();
		buffer = null;
		width = 0;
		height = 0;
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public ByteBuffer getBuffer() { return buffer; }
	
	@Override
	public String toString() {
		return "SECA TextureBlueprint with dimensions ("
				+ width + ", " + height 
				+ ") with " + buffer.capacity() + " data values";
	}
}
