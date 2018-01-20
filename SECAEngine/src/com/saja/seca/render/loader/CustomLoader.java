package com.saja.seca.render.loader;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Andy Nguyen
 *
 * The CustomLoader interface makes an easy way to configure loading models and textures by using engines outside of SECA. 
 * 		(SECA does not manage reading files, it only loads read data into memory appropriately).
 * A class implementing CustomLoader is passed to the ResLoader, which uses that CutsomLoader to create Models and Textures
 * 
 * Both methods take in a direct address to the specified file, i.e. "res/img/box.png"
 * 
 * cleanUp() is called by the ResLoader when the ResLoader is cleaned up
 */

public interface CustomLoader {
	
	public ModelBlueprint loadModel(String fileAddress) throws FileNotFoundException, IOException;
	
	public TextureBlueprint loadTexture(String fileAddress) throws FileNotFoundException, IOException;	
	
	public void cleanUp();
}
