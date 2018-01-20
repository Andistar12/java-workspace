package com.saja.demo.loader;

import com.saja.seca.render.loader.CustomLoader;
import com.saja.seca.render.loader.ModelBlueprint;
import com.saja.seca.render.loader.TextureBlueprint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @author Andy Nguyen
 * Implements the CustomLoader from the SECA render.loader package
 * Currently uses an .obj file (OBJReader) reader and a .png file reader (PNGDecoder)
 */

public class OBJPNGLoader implements CustomLoader {

	//public static final String resLos = "res/";
	public static final String obj_ext = ".obj";
	public static final String png_ext = ".png";
	
	@Override
	public ModelBlueprint loadModel(String fileAddress) throws FileNotFoundException, IOException {
		if (!fileAddress.endsWith(obj_ext)) fileAddress += obj_ext;
		return OBJReader.loadOBJ(fileAddress);
	}

	@Override
	public TextureBlueprint loadTexture(String fileAddress) throws FileNotFoundException, IOException {
		if (!fileAddress.endsWith(png_ext)) fileAddress += png_ext;
		InputStream in = new FileInputStream(fileAddress);
		PNGDecoder decoder = new PNGDecoder(in);

		ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
		decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA); // 3 = RGB, 4 = RGBA
		buf.flip();

		TextureBlueprint tb = new TextureBlueprint(buf, decoder.getWidth(), decoder.getHeight());

		in.close();
		decoder = null;
		return tb;
	}

	@Override
	public void cleanUp() {
		//Both OBJReader and PNGDecoder are static classes so no need to clean up anything
	}
	
}
