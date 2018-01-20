package com.saja.seca.render.loader;

import com.saja.seca.error.SecaException;
import com.saja.seca.render.GLTools;
import com.saja.seca.render.Renderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Andy Nguyen
 *
 * A Model is the set of VBOs for an in-game drawable model. 
 * Model takes in a ModelBlueprint and constructs VBOs out of all the supplied data.
 * 
 * The CollisionRadius variable can only be calculated from the raw array of vertex data,
 * 		so that variable is sent in through the ModelBlueprint (useful in spherical collisions)
 * 
 * VBO structure is stored in a separate class
 * 
 * Calling draw() will perform a draw only on this object
 * 
 * cleanUp() is called by the ResLoader
 */

public class Model {
		
	private final float collisionRadius;
	
	private HashMap<String, VBO> vbos = new HashMap<String, VBO>();
	private final int vertexCount;
	private int indexVBO = 0;
	private int vaoID = 0;
	
	private Renderer renderer;
	
	public Model(ModelBlueprint mb, Renderer r) {
		//Some vars
		this.renderer = r;
		collisionRadius = mb.getFurthestDistance();
		vertexCount = mb.getIndicesArray().length;

		//Create VBOs with the data
		indexVBO = GLTools.createShortVBO(mb.getIndicesArray(), GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_STREAM_DRAW);
		for (VBO v : mb.getFloatArrays()) {
			vbos.put(v.getName(), v);
			v.createVBO();
		}

		//Setup the VAO
		vaoID = GLTools.createAndBindVAO();
		r.enableAttribs(); //Have to do this
		for (Entry<String, VBO> e : vbos.entrySet()) {
			//System.out.println("SECA Model: Trying to bind VBO to " + e.getKey() + " at " + r.getBindLocation(e.getKey()));
			e.getValue().bindVBO(r.getBindLocation(e.getKey()));
		}
		GL30.glBindVertexArray(0);
		try {
			GLTools.checkGLError();
		} catch (SecaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mb.cleanUp();
	}
	
	public void drawTriangles() {
		GL30.glBindVertexArray(getVAOID());
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexVBO);
		GL11.glDrawElements(GL11.GL_TRIANGLES, getVertexCount(), GL11.GL_UNSIGNED_SHORT, 0);
	}
	public void drawLines() {
		GL30.glBindVertexArray(getVAOID());
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexVBO);
		//GL11.glDrawArrays(GL11.GL_LINES, 0, this.getVertexCount());
		GL11.glDrawElements(GL11.GL_LINES, getVertexCount(), GL11.GL_UNSIGNED_SHORT, 0);
	}
	public void drawPoints() {
		GL30.glBindVertexArray(getVAOID());
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexVBO);
		GL11.glDrawElements(GL11.GL_POINTS, getVertexCount(), GL11.GL_UNSIGNED_SHORT, 0);
	}
	
	public int getVAOID() { return vaoID; }
	public int getIndicesID() { return indexVBO; }
	public int getVBOCount() { return vbos.size() + 1; } //Add 1 for Index buffer
	public int getVertexCount() { return vertexCount; }
	public float getCollisionRadius() { return collisionRadius; }
	public Renderer getRenderer() { return renderer; }
	
	public void cleanUp() {
		for (VBO vbo : vbos.values()) {
			vbo.cleanUp();
		}
		GL15.glDeleteBuffers(indexVBO);
		GL30.glDeleteVertexArrays(vaoID);
		vbos.clear();
		vaoID = 0;
	}
	
	@Override
	public String toString() {
		return "SECA Model VAO=" + vaoID + " vboCount=" + (vbos.size() + 1); //Add 1 for Index buffer
	}
}
