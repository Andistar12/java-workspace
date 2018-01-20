package com.saja.demo.gameEngine.entities;

import com.saja.demo.gameEngine.worldGen.WorldGenerator;
import com.saja.demo.renderers.TerrainRenderer;
import com.saja.seca.error.SecaException;
import com.saja.seca.math3d.Vector3f;
import com.saja.seca.render.components.DrawableModelComponent;
import com.saja.seca.render.components.PositionComponent;
import com.saja.seca.render.components.TextureComponent;
import com.saja.seca.render.loader.ModelBlueprint;
import com.saja.seca.render.loader.ResLoader;

/**
 * @author Andy
 *
 * Represents one 'chunk' of a terrain. All arguments come from TerrainSystem
 * 
 * Note how the WorldGenerator provides the heights and normals
 * Note how the newly created Model is loaded directly into the ResLoader.
 * 		The ModelBlueprint is created in code
 */

public class TerrainChunk extends GameEntity {
		
	private Vector3f position;
	private int size;
	
	public TerrainChunk(TerrainRenderer renderer, ResLoader rl, String textureName, WorldGenerator wg, Vector3f position, int size) throws SecaException{
		super();
		
		//this.removeAllComponents(PositionComponent.class); 
		//We cannot use the PositionComponent because we aren't really using the model matrix
		//TODO check removeAllComponents
		this.size = size;
		this.position = new Vector3f(position.x, position.y, position.z);
		
		Vector3f pos = position;
		pos.x -= size / 2f; //Position data is sent in giving center of the square
		pos.z -= size / 2f; //We need it at the bottom left to generate vertices
		
		int VERTEX_COUNT = size + 1; //Size 32 means 33 vertices along each chunk idk
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		short[] indices = new short[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		int vertexPointer = 0;
		
		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				vertices[vertexPointer * 3] = pos.x + j;
				vertices[vertexPointer * 3 + 1] = wg.getHeight(pos.x + j, pos.z + i);
				vertices[vertexPointer * 3 + 2] = pos.z + i;
				
				Vector3f normal = wg.getNormal(pos.x + j, pos.z + i);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y; 
				normals[vertexPointer * 3 + 2] = normal.z;
				 
				textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
			for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
				short topLeft = (short) ((gz * VERTEX_COUNT) + gx);
				short topRight = (short) (topLeft + 1);
				short bottomLeft = (short) (((gz + 1) * VERTEX_COUNT) + gx);
				short bottomRight = (short) (bottomLeft + 1);
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		
		
		ModelBlueprint mb = new ModelBlueprint(indices, (float) (size * Math.sqrt(2) ) );
		mb.addArray("in_position", vertices, 3);
		mb.addArray("in_textureCoord", textureCoords, 2);
		mb.addArray("in_normal", normals, 3);
		
		String name = rl.createUniqueModelName("tile");
		
		rl.createModel(name, mb, renderer);
		
		this.addComponent(new TextureComponent(rl, textureName));
		this.addComponent(new DrawableModelComponent(rl.getModel(name)));
		
		//System.out.println("DEMO TerrainChunk: Chunk created with size " + size + " at location (" + this.position.x + ", " + this.position.z + ")");
		//System.out.println("DEMO TerrainChunk: Chunk created:\n\tVertices: " + java.util.Arrays.toString(vertices) + "\n\tTexCoord: " + java.util.Arrays.toString(textureCoords));
	}
	
	public void draw() {
		PositionComponent pc = (PositionComponent) this.getFirstComponentInstance(PositionComponent.class);
		DrawableModelComponent mc = (DrawableModelComponent) this.getFirstComponentInstance(DrawableModelComponent.class);
		TextureComponent tc = (TextureComponent) this.getFirstComponentInstance(TextureComponent.class);
		TerrainRenderer r = (TerrainRenderer) mc.getModel().getRenderer();
		
		r.setModelMatrix(pc.getModelMatrix());
		r.setTexture(tc.getTexture().getTextureID());
		mc.drawTriangles();
	}

	public boolean isPositionInTile(Vector3f pos) {
		return isPositionInTile(pos.x, pos.z);
	}
	
	public boolean isPositionInTile(float x, float z) {
		if (x < position.x - size / 2f) return false;
		if (x > position.x + size / 2f) return false;
		if (z < position.z - size / 2f) return false;
		if (z > position.z + size / 2f) return false;
		return true;
	}

	@Override
	protected void entityCleanUp() { position = null; }
}
