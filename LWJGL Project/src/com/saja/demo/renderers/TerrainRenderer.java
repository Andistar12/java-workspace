package com.saja.demo.renderers;

import com.saja.seca.error.SecaException;
import com.saja.seca.math3d.Matrix4f;
import com.saja.seca.render.Renderer;

import java.io.*;
/**
 * @Author Andy Nguyen
 * 
 * res/shader/terrain.vsh
 * res/shader/terrain.fsh
 */

public class TerrainRenderer extends Renderer {

	public static final String console_prefix = "DEMO TerrainRenderer: ";
	
	public static final String shaderName = "terrain", location = "res/shaders/";
	
	public TerrainRenderer() throws IOException, SecaException, FileNotFoundException {
		super(shaderName, readFile(location + shaderName + ".vsh"), readFile(location + shaderName + ".fsh"), 16);
	}

	public void setModelMatrix(Matrix4f m) {
		super.uniformMatrix4f("u_modelMatrix", m);
	}
	public void setTexture(int textureID) {
		super.uniformTexture("u_texture", textureID);
	}
	public void setViewProjectionMatrix(Matrix4f m) {
		super.uniformMatrix4f("u_viewProjMatrix", m);
	}
	public void setLightPosition(float x, float y, float z) {
		super.uniform3f("u_lightLoc", x, y, z);
	}
	public void setCameraLoc(float x, float y, float z) {
		super.uniform3f("u_cameraLoc", x, y, z);
	}
	public void setSkyColor(float r, float g, float b) {
		super.uniform3f("u_skyColor", r, g, b);
	}
	public void setSunLocation(float x, float y, float z, float atten) {
		super.uniform4f("u_sunLoc", x, y, z, atten);
	}

	@Override
	public void bindAttribsInShaders() {
		super.bindAttribLoc( 0, "in_position");
		super.bindAttribLoc( 1, "in_normal");
		super.bindAttribLoc( 2, "in_textureCoord");

		super.bindAttribLoc( 3, "u_cameraLoc");
		super.bindAttribLoc( 4, "u_lightLoc");
		super.bindAttribLoc( 5, "u_skyColor");
		
		super.bindAttribLoc( 6, "u_viewProjMatrix");
		super.bindAttribLoc(10, "u_modelMatrix");
		super.bindAttribLoc(14, "u_texture");
		
		super.bindAttribLoc(15, "u_sunLoc");
	}

	@Override
	protected void addAttribsToHash() {
		super.addAttribLoc("in_position");
		super.addAttribLoc("in_textureCoord");
		super.addAttribLoc("in_normal");
		
		super.addUniformLoc("u_lightLoc");
		super.addUniformLoc("u_skyColor");
		super.addUniformLoc("u_viewProjMatrix");
		super.addUniformLoc("u_modelMatrix");
		super.addUniformLoc("u_cameraLoc");
		super.addUniformLoc("u_texture");
		super.addUniformLoc("u_sunLoc");
	}
	
	@Override
	public void enableAttribs() {
		super.enableAttrib("in_position");
		super.enableAttrib("in_normal");
		super.enableAttrib("in_textureCoord");
	}
	
	@Override
	public void disableAttribs() {
		super.disableAttrib("in_position");
		super.disableAttrib("in_normal");
	}

	//Read a file given an address
	private static String readFile(String path) throws FileNotFoundException, IOException {
		String currentLine = null;
		String fileContent = "";

		try {
			FileReader fileReader = new FileReader(new File(path).getAbsoluteFile());
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while ((currentLine = bufferedReader.readLine()) != null) {
				fileContent += currentLine + "\n";
			}
			
			bufferedReader.close();
		} catch (FileNotFoundException fnfe) {
			System.err.println("Unable to open file '" + path + "'");
			throw fnfe;
		} catch (IOException ioe) {
			System.err.println("Error reading file '" + path + "'");
			throw ioe;
		} 

		return fileContent;
	}
}
