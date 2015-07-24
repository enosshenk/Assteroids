package com.shenko.assteroids;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface AsteroidInterface {

	public void Update(float Delta);
	
	public void Render(ShapeRenderer Renderer);
	
	public void TakeHit();
	
	
}
