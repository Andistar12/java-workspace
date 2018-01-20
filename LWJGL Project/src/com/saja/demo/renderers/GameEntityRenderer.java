package com.saja.demo.renderers;

import com.saja.seca.error.SecaException;
import com.saja.seca.math3d.Matrix4f;
import com.saja.seca.render.Renderer;

import java.io.*;

/**
 * @author Andy Nguyen
 *
 * res/shader/entity.vsh
 * res/shader/entity.fsh
 */

public class GameEntityRenderer extends Renderer {

	public static final String shaderName = "entity", location = "res/shaders/";
	
	public GameEntityRenderer() throws SecaException, IOException, FileNotFoundException {
		super(shaderName, readFile(location + shaderName + ".vsh"), readFile(location + shaderName + ".fsh"), 17);		
	}

	public void setModelMatrix(Matrix4f m) {
		super.uniformMatrix4f("u_modelMatrix", m);
	}
	public void setViewProjectionMatrix(Matrix4f m) {
		super.uniformMatrix4f("u_viewProjMatrix", m);
	}
	public void setTexture(int textureID) {
		super.uniformTexture("u_myTexture", textureID);
	}
	public void setSkyColor(float r, float g, float b) {
		super.uniform3f("u_skyColor", r, g, b);
	}
	public void setCameraLoc(float x, float y, float z) {
		super.uniform3f("u_cameraLoc", x, y, z);
	}
	public void setEntityColor(float r, float g, float b) {
		super.uniform3f("u_entityColor", r, g, b);
	}
	public void setLightPos(float x, float y, float z) {
		super.uniform3f("u_lightPos", x, y, z);
	}
	public void setSunLocation(float x, float y, float z, float atten) {
		super.uniform4f("u_sunLoc", x, y, z, atten);
	}
	
	@Override
	protected void bindAttribsInShaders() {
		//super.bindAttribLoc(int location, String name);
		
		super.bindAttribLoc( 0, "in_position");
		super.bindAttribLoc( 1, "in_textureCoord");
		super.bindAttribLoc( 2, "in_normal");

		super.bindAttribLoc( 3, "u_cameraLoc");
		super.bindAttribLoc( 4, "u_skyColor");
		super.bindAttribLoc( 5, "u_entityColor");
		super.bindAttribLoc( 6, "u_myTexture");
		super.bindAttribLoc( 7, "u_lightPos");
		
		super.bindAttribLoc( 8, "u_sunLoc");
		
		super.bindAttribLoc( 9, "u_viewProjMatrix");
		super.bindAttribLoc(13, "u_modelMatrix");
	}

	@Override
	protected void addAttribsToHash() {
		super.addAttribLoc("in_position");
		super.addAttribLoc("in_textureCoord");
		super.addUniformLoc("u_cameraLoc");
		super.addUniformLoc("u_skyColor");
		super.addAttribLoc("in_normal");
		super.addUniformLoc("u_entityColor");
		super.addUniformLoc("u_myTexture");
		super.addUniformLoc("u_lightPos");
		super.addUniformLoc("u_sunLoc");
		super.addUniformLoc("u_viewProjMatrix");
		super.addUniformLoc("u_modelMatrix");
	}
	
	@Override
	public void enableAttribs() {
		super.enableAttrib("in_position");
		super.enableAttrib("in_textureCoord");
		super.enableAttrib("in_normal");	
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
	
	@Override
	public void disableAttribs() {
		super.disableAttrib("in_position");
		super.disableAttrib("in_textureCoord");
		super.disableAttrib("in_normal");
	}

}
