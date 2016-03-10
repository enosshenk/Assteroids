package com.shenko.assteroids;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class AsteroidSmall extends Asteroid implements AsteroidInterface {
	public AsteroidSmall(AssteroidsGameScreen inScreen, ESize inSize, Vector2 inLocation, Vector2 inVelocity)
	{
		
		TheScreen = inScreen;
		
		Size = inSize;
		Location = inLocation;
		Velocity = inVelocity;
		
		DrawPolygon = new Polygon(DrawPointsSmall);
		DrawPolygon.scale(6f);
		Collision = new Circle(Location, 15);

		// Set up rotation rate
		RotationRate = TheScreen.Math.random(-2f, 2f);
		
	}

	@Override
	public void Update(float Delta) {
		
		super.Update(Delta);
		
		Location.add(Velocity);	
		DrawPolygon.setPosition(Location.x, Location.y);
		Collision.setPosition(Location);
		
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
