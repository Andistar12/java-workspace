package com.saja.demo.renderers;

import com.saja.seca.error.SecaException;
import com.saja.seca.math3d.Matrix4f;
import com.saja.seca.math3d.Vector3f;
import com.saja.seca.render.MasterRenderer;
import com.saja.seca.render.Renderer;

import java.io.*;
/**
 * @Author Andy Nguyen
 * 
 * res/shader/gui.vsh
 * res/shader/gui.fsh
 */

public class GuiRenderer extends Renderer {
	public static final String shaderName = "gui", location = "res/shaders/";
	public static final String console_prefix = "DEMO GuiRenderer";

	public GuiRenderer() throws IOException, SecaException, FileNotFoundException {
		super(shaderName, readFile(location + shaderName + ".vsh"), readFile(location + shaderName + ".fsh"), 8);
	}

	public void setTexture(int textureID) {
		super.uniformTexture("u_texture", textureID);
	}

	public void setModelMatrix(Matrix4f m) {
		super.uniformMatrix4f("u_modelMatrix", m);
	}
	public void setAspectRatio(float a) {
		super.uniform1f("u_aspectRatio", a);
	}
	public void setColor(Vector3f color) {
		super.uniform3f("u_guiColor", color);
	}

	@Override
	public void bindAttribsInShaders() {
		super.bindAttribLoc(0, "in_position");
		super.bindAttribLoc(1, "in_textureCoord");
		super.bindAttribLoc(2, "u_guiColor");
		super.bindAttribLoc(3, "u_texture");
		super.bindAttribLoc(4, "u_aspectRatio");
		super.bindAttribLoc(5, "u_modelMatrix");
	}
	@Override
	protected void addAttribsToHash() {
		super.addAttribLoc("in_position");
		super.addAttribLoc("in_textureCoord");
		super.addUniformLoc("u_guiColor");
		super.addUniformLoc("u_texture");
		super.addUniformLoc("u_aspectRatio");
		super.addUniformLoc("u_modelMatrix");
	}

	@Override
	public void enableAttribs() {
		super.enableAttrib("in_position");
		super.enableAttrib("in_textureCoord");
	}

	@Override
	public void disableAttribs() {
		super.disableAttrib("in_position");
		super.disableAttrib("in_textureCoord");
	}

	@Override
	public void useRenderer() {
		super.useRenderer();
		//GUIs can disable this stuff entirely, bypass useless calculations
		MasterRenderer.disableDepthTest();
		MasterRenderer.disableCullFace();
		MasterRenderer.disableBlending();
	}

	@Override
	public void unuseRenderer() {
		super.unuseRenderer();
		MasterRenderer.enableDepthTest();
		MasterRenderer.enableCullFace();
		MasterRenderer.enableBlending();
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
