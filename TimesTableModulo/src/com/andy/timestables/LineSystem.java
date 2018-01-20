package com.andy.timestables;

import com.saja.seca.back.Entity;
import com.saja.seca.error.SecaException;
import com.saja.seca.render.loader.ResLoader;

public class LineSystem extends com.saja.seca.back.System {
	
	private ResLoader r;
	private float currentMultiplier;
	private float bounds;
	
	public LineSystem(ResLoader loader, LineRenderer lr, int vertCount, float start) throws SecaException {
		this.r = loader;
		
		CirclePoints cp = new CirclePoints(vertCount, loader, lr);
		this.addEntity(cp);
		
		currentMultiplier = start;
		bounds = start;
	}
	
	public void update(float gameSpeed) {
		//if (gameSpeed == 0) return;
		if (bounds > currentMultiplier + gameSpeed) return;
		CirclePoints cp = (CirclePoints) this.getFirstEntityInstance(CirclePoints.class);
		cp.changeIndices(currentMultiplier);
		currentMultiplier += gameSpeed;
	}
	
	public void render() {
		CirclePoints cp = (CirclePoints) this.getFirstEntityInstance(CirclePoints.class);
		cp.render();
	}
	
	@Override
	public boolean isEntityAddable(Entity e) {
		if (CirclePoints.class.isInstance(e)) return true;
		return false;
	}

	@Override
	public void clearEntities() {
		super.clearEntities();
		r.removeModel("line");
	}
	
	public float getCurrentMultiplier() { return currentMultiplier; }
	public void resetCurrentMultiplier() {
		currentMultiplier = bounds;
	}
	
	@Override
	protected void systemCleanUp() {
	}

}
