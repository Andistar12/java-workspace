package com.saja.demo.logicEngine.managers;

import com.saja.seca.math3d.Matrix4f;
import com.saja.seca.math3d.Vector3f;
import com.saja.seca.math3d.Vector4f;

/**
 * @author Andy Nguyen
 * 
 * Will be use to do calculations involving mouse location
 * Will be purely a static library-like class
 */

public class MouseManager {

	public static Vector3f getMouseRayInGame(Matrix4f viewM, Matrix4f projM) {
		Vector4f mouseRay = new Vector4f(0, 0, -1, 1); //Right at the center, points into the screen
		
		Matrix4f invertProjM = Matrix4f.invert(projM);
		Matrix4f invertViewM = Matrix4f.invert(viewM);
		
		Vector4f m2 = Matrix4f.transform(invertProjM, mouseRay);
		m2.z = -1; m2.w = 1;
		Vector4f m3 = Matrix4f.transform(invertViewM, m2);
		
		Vector3f result = new Vector3f(-m3.x, -m3.y, m3.z);
		result = result.normalize();
		
		return result;
	}
		
}
