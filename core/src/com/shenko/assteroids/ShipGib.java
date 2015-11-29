package com.shenko.assteroids;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class ShipGib {
	
	// Fragment of the player ship, spawned when he gets owned by an asteroid
	
	private AssteroidsGameScreen Screen;

	public Vector2 Location;
	private Vector2 Length;			// Length of the line fragment
	private Vector2 Velocity;
	
	private float Rotation;
	private float RotationRate;
	
	private float LifetimeElapsed;
	private float Lifetime;
	
	public ShipGib(AssteroidsGameScreen inScreen, Vector2 inLocation, Vector2 inVelocity)
	{
		Screen = inScreen;
		
		Location = inLocation;
		Velocity = inVelocity;
		
		RotationRate = Screen.Math.random(-0.3f, 0.3f);
		Lifetime = Screen.Math.random(0.2f,2f);
		LifetimeElapsed = 0f;
		
		Length = new Vector2(0, Screen.Math.random(30f,40f));
		Rotation = Screen.Math.random(0,359);
		Length.rotate(Rotation);
	}
	
	public void Update(float Delta)
	{
		Location.add(Velocity);
		
		Rotation += RotationRate;
		Length.rotate(RotationRate);
		
		LifetimeElapsed += Delta;
		
		if (LifetimeElapsed > Lifetime)
		{
			// Expire
			Screen.GibExpired(this);
		}
	}
	
	public void Render(ShapeRenderer Renderer)
	{
		Renderer.begin(ShapeType.Line);
		Renderer.setColor(1,1,1,1);
		Renderer.line(Location, Location.cpy().add(Length));
		Renderer.end();
	}
	
}
