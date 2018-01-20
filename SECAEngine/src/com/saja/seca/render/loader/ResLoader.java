package com.saja.seca.render.loader;

import com.saja.seca.error.SecaException;
import com.saja.seca.render.Renderer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Andy Nguyen
 *
 * The ResLoader stores all the Models and Textures into an object
 * It takes in a CustomLoader, which can be created elsewhere outside of SECA
 * 
 * Models and textures can be loaded from file [ loadModel(String name, String fileLoc, Renderer r) ] 
 * 		or sent in directly as blueprints [ createModel(String name, ModelBlueprint mb, Renderer r) ]
 * 
 * Uncomment the System.out lines to warn when a model/texture requested is not present
 * 
 * Calling cleanUp() is a must once terminating. This is the only way to clear the buffers/data in OpenGL
 */

public class ResLoader {
	
	public static final String console_prefix = "SECA ResLoader: ";
	public static final String missing_files = "An error occured. Missing files?";
	
	private CustomLoader customLoader;
	
	private HashMap<String, Model> models = new HashMap<String, Model>();
	private HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public ResLoader(CustomLoader cl) {
		this.customLoader = cl;
	}
	
	public void loadModel(String name, String fileAddress, Renderer r) throws SecaException {
		try {
			createModel(name, customLoader.loadModel(fileAddress), r);
		} catch (FileNotFoundException e) {
			throw new SecaException(this.getClass(), "Model \'" + name + "\' could not be found", missing_files);
		} catch (IOException e) {
			throw new SecaException(this.getClass(), "Model \'" + name + "\' could not be read", missing_files);
		}
	}
	public void createModel(String name, ModelBlueprint mb, Renderer r) throws SecaException {
		System.out.print(console_prefix + "Creating model \'" + name + "\'... ");
		Model m = new Model(mb, r);
		if (m.getVAOID() == 0) { 
			throw new SecaException(this.getClass(), "Model \'" + name + "\' could not be loaded", missing_files); 
		}
		models.put(name, m);
		System.out.println("created with " 
				+ m.getVBOCount() + " VBOs, attached to renderer \'" + r.getRendererName() + "\'.");
	}
	public Model getModel(String name) {
		Model m = models.get(name);
		//if (m == null) System.out.println(console_prefix + "Warning - model \'" + name + "\' is null");
		return m;
	}
	
	public void loadTexture(String name, String fileAddress) throws SecaException {
		System.out.print(console_prefix + "Creating texture \'" + name + "\'... ");
		try {
			createTexture(name, customLoader.loadTexture(fileAddress));
		} catch (FileNotFoundException e) {
			throw new SecaException(this.getClass(), "Texture \'" + name + "\' could not be found", missing_files);
		} catch (IOException e) {
			throw new SecaException(this.getClass(), "Texture \'" + name + "\' could not be read", missing_files);
		}
	}
	public void createTexture(String name, TextureBlueprint tb) throws SecaException {
		Texture t = new Texture(tb);
		if (t.getTextureID() == 0) { throw new SecaException(this.getClass(), "Texture " + name + " could not be loaded", missing_files); }
		textures.put(name, t);
		System.out.println("created with id " + t.getTextureID());
	}
	public Texture getTexture(String name) {
		Texture t = textures.get(name);
		//if (t == null) System.out.println(console_prefix + "Warning - texture \'" + name + "\' is null");
		return t;
	}
	
	//These two methods find names that can be used
	//They will be in the format "name#", i.e. "terrainChunk2"
	public String createUniqueModelName(String name) {
		int counter = -1;
		Model m = null;
		do {
			counter++;
			m = models.get(name + counter);
		} while (m != null);
		return name + counter;
	}
	public String createUniqueTextureName(String name) {
		int counter = -1;
		Texture t = null;
		do {
			counter++;
			t = textures.get(name + counter);
		} while (t != null);
		return name + counter;
	}
	
	public void removeModel(String name) {
		models.get(name).cleanUp();
		models.remove(name);
	}
	public void removeModels(String name) {
		for (String s : models.keySet()) {
			if (s.contains(name)) {
				models.get((s)).cleanUp();
				models.remove(s);
			}
		}
	}
	public void removeTexture(String name) {
		textures.get(name).cleanUp();
		models.remove(name);
	}
	public void removeTextures(String name) {
		for (String s : textures.keySet()) {
			if (s.contains(name)) {
				textures.get((s)).cleanUp();
				textures.remove(s);
			}
		}
	}
	
	public void cleanUp() {
		System.out.print(console_prefix + "Clearing out " + this.toString() + "... ");
		for (Model m : models.values()) {
			m.cleanUp();
		}
		for (Texture t : textures.values()) {
			t.cleanUp();
		}
		models.clear();
		textures.clear();
		customLoader.cleanUp();
		System.out.println("success");
	}
	
	@Override
	public String toString() {
		int vboCount = 0;
		for (Model m : models.values()) {
			vboCount += m.getVBOCount();
		}
		return "SECA ResLoader with " + models.size() + " models (" + vboCount + " VBOS), "
				+ textures.size() + " textures, and 1 CustomLoader";
	}
}
