package com.shenko.assteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Intersector;

public class Asteroid implements AsteroidInterface {
	
	// Class of an asteroid
	
	private Intersector Intersector;
	
	public AssteroidsGameScreen TheScreen;

	public Vector2 Location;
	public Vector2 Velocity;
	
	public float Rotation;
	public float RotationRate;
	
	public enum ESize { LARGE, MEDIUM, SMALL };
	public ESize Size; 
	
	public Polygon DrawPolygon;
	public Circle Collision;
	
	public boolean debug = false;
	
	// Draw points for the various asteroid sizes
	public float[] DrawPointsLarge = { -4, 1, -3, 2, -3, 4, -1, 5, 1, 2, 3, 4, 4, 1, 3, -1, 2, -2, 3, -4, 1, -5, -1, -3, -2, -4, -4, -3, -3, -1, -4, -1 };
	
	public float[] DrawPointsMedium = { -3, 0, -2, 2, 0, 3, 1, 2, 3, 1, 2, -1, 2, -1, 3, -2, 1, -3, -1, -2, -1, -1, -3, 0 };

	public float[] DrawPointsSmall = { -2, -2, -1, -1, -2, 0, -2, 1, 0, 2, 2, 0, 1, -1, 1, -2, -1, -3, -2, -2 };

	public void Update(float Delta) {
		
		// Wrap location if offscreen
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
		
		
		// Check collision vs the player ship
		if (!TheScreen.Ship.IsInvulnerable && !TheScreen.Ship.IsDead)
		{
			if ( Intersector.overlaps(Collision, TheScreen.Ship.Collision) )
			{
				// Collided with the player ship, blow it up
				TheScreen.PlayShipExplosion(TheScreen.Ship.Location, TheScreen.Ship.Velocity);
				TheScreen.Ship.Hit();
				System.out.print("Collision");
			}
		}
	}

	public void Render(ShapeRenderer Renderer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void TakeHit() {
		
		TheScreen.AsteroidExpired(this);
	}
	
}
