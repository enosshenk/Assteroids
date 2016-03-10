package com.shenko.assteroids;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class AsteroidLarge extends Asteroid implements AsteroidInterface {
	
	public AsteroidLarge(AssteroidsGameScreen inScreen, ESize inSize, Vector2 inLocation, Vector2 inVelocity)
	{
		
		TheScreen = inScreen;
		
		Size = inSize;
		Location = inLocation;
		Velocity = inVelocity;
		
		// Create polygon from the array of draw points
		DrawPolygon = new Polygon(DrawPointsLarge);
		// Scale it
		DrawPolygon.scale(10);
		// Set up collision
		Collision = new Circle(Location, 45);
		
		// Set up rotation rate
		RotationRate = TheScreen.Math.random(-2f, 2f);
		
	}

	@Override
	public void Update(float Delta) {
		
		super.Update(Delta);
		
		// Update location
		Location.add(Velocity);	
		
		// Update polygon and collision location
		DrawPolygon.setPosition(Location.x, Location.y);
		Collision.setPosition(Location);
		
		// Rotate
		Rotation += RotationRate;		
		DrawPolygon.rotate(RotationRate);
	}

	@Override
	public void Render(ShapeRenderer Renderer) {
		
		Renderer.begin(ShapeType.Line);
		Renderer.setColor(1,1,1,1);
		Renderer.polygon(DrawPolygon.getTransformedVertices());
		if (debug)
		{
			Renderer.setColor(1, 0, 0, 1);
			Renderer.circle(Collision.x, Collision.y, Collision.radius);
			Renderer.setColor(1,1,1,1);
		}
		Renderer.end();
	}

	@Override
	public void TakeHit() {
		super.TakeHit();			
	}
	
}
