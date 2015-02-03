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
		
		// Set up polygon and collision
		switch (Size)
		{
		case LARGE:
			DrawPolygon = new Polygon(DrawPointsLarge);
			DrawPolygon.scale(10);
			Collision = new Circle(Location, 4);
			break;
			
		case MEDIUM:
			DrawPolygon = new Polygon(DrawPointsMedium);
			DrawPolygon.scale(7.2f);
			Collision = new Circle(Location, 3);
			break;
			
		case SMALL:
			DrawPolygon = new Polygon(DrawPointsSmall);
			DrawPolygon.scale(3.5f);
			Collision = new Circle(Location, 2);
			break;
			
		default:
			DrawPolygon = new Polygon(DrawPointsMedium);
			Collision = new Circle(Location, 3);
			break;		
		}
		
		// Set up rotation rate
		RotationRate = TheScreen.Math.random(-3, 3);
		
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
		Renderer.polygon(DrawPolygon.getTransformedVertices());
		Renderer.end();
	}
	
}
