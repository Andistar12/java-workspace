package com.saja.seca.render.loader;

import com.saja.seca.render.GLTools;
import org.lwjgl.opengl.GL15;

/**
 * @author Andy Nguyen
 *
 * The VBO class is a structure only accessible by the render.loader package of SECA (everything is protected)
 * It stores the float data created by ModelBlueprint, and also stores the VBO handle.
 * 		(When the VBO is created, the arrayData is nullified)
 * 
 * cleanUp() is called by its associated model
 */

/*
 * @Author Andy Nguyen
 * 
 * The VBO class is a structure only accessible by the render.loader package of SECA (everything is protected)
 * It stores the float data created by ModelBlueprint, and also stores the VBO handle.
 * 		(When the VBO is created, the arrayData is nullified)
 * 
 * VBO should only be accessed by members of the seca.loader package
 * 
 * cleanUp() is called by its associated model
 */

class VBO {
		
	private int vboHandle;
	private String vboName;
	private int size;
	private float[] arrayData;
	
	protected VBO(String vboName, float[] arrayData, int elementSize) {
		this.size = elementSize;
		this.arrayData = arrayData;		
		this.vboName = vboName;
	}
	
	protected float[] getArrayData() { return arrayData; }
	protected int getVBOHandle() { return vboHandle; }
	protected int getElementSize() { return size; }
	protected String getName() { return vboName; }
	
	protected void createVBO() {
		//Create the VBO
		this.vboHandle = GLTools.createFloatVBO(arrayData, GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW);
		arrayData = null;
	}
	
	protected void bindVBO(int bindLoc) {
		GLTools.floatBufferVertexArrayBind(vboHandle, bindLoc, size);
	}
	
	protected void cleanUp() {
		GL15.glDeleteBuffers(vboHandle);
		vboHandle = 0; //OpenGL ignores 0 when deleting
	}
	
	@Override
	public String toString() {
		return "SECA VBO \'" + vboName + "\' with id " + vboHandle;
	}
	
}