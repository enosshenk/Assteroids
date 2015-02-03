package com.shenko.assteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Asteroid {
	
	public AssteroidsGameScreen TheScreen;

	public Vector2 Location;
	public Vector2 Velocity;
	
	public float Rotation;
	public float RotationRate;
	
	public enum ESize { LARGE, MEDIUM, SMALL };
	public ESize Size; 
	
	public Polygon DrawPolygon;
	public Circle Collision;
	
	public float[] DrawPointsLarge = { -4, 1, -3, 2, -3, 4, -1, 5, 1, 2, 3, 4, 4, 1, 3, -1, 2, -2, 3, -4, 1, -5, -1, -3, -2, -4, -4, -3, -3, -1, -4, -1 };
	
	public float[] DrawPointsMedium = { -3, 0, -2, 2, 0, 3, 1, 2, 3, 1, 2, -1, 2, -1, 3, -2, 1, -3, -1, -2, -1, -1, -3, 0 };

	public float[] DrawPointsSmall = { -2, -2, -1, -1, -2, 0, -2, 1, 0, 2, 2, 0, 1, -1, 1, -2, -1, -3, -2, -2 };

	public void Update(float Delta) {
		
		if (Location.x < -50)
		{
			Location.x = Gdx.graphics.getWidth() + 50;
		}
		else if (Location.x > Gdx.graphics.getWidth() + 50)
		{
			Location.x = -50;
		}		
		
		if (Location.y < -50)
		{
			Location.y = Gdx.graphics.getHeight() + 50;
		}
		else if (Location.y > Gdx.graphics.getHeight() + 50)
		{
			Location.y = -50;
		}
		
	}

	public void Render(ShapeRenderer Renderer) {
		// TODO Auto-generated method stub
		
	}
}
