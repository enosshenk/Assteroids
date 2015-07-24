package com.shenko.assteroids;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Particle {
	
	// It's a particle. Just a dotty little dot with a time to expire
	
	public AssteroidsGameScreen Screen;

	public Vector2 Location;
	public Vector2 Velocity;
	
	private float Lifetime;
	private float LifetimeElapsed;
	
	private float Brightness;
	
	private float Size;
	
	public Particle(AssteroidsGameScreen inScreen, Vector2 inLocation, Vector2 inVelocity, float inSize)
	{
		Screen = inScreen;
		
		Location = inLocation;
		Velocity = inVelocity;
		
		LifetimeElapsed = 0;
		Lifetime = Screen.Math.random(0.25f, 0.7f);
		Brightness = 1f;
		
		Size = inSize;
	}
	
	public void Update(float Delta)
	{
		LifetimeElapsed += Delta;
		
		if (LifetimeElapsed > Lifetime)
		{
			// Expire
			Screen.ParticleExpired(this);
		}
		
		Brightness -= Delta / Lifetime;
		
		Location.add(Velocity);
	}
	
	public void Render(ShapeRenderer Renderer)
	{
		Renderer.begin(ShapeType.Line);
		Renderer.setColor(Brightness,Brightness,Brightness,1);
		Renderer.circle(Location.x, Location.y, Size);
		Renderer.setColor(1,1,1,1);
		Renderer.end();
	}
}
