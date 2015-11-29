package com.shenko.assteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class PlayerBullet {
	
	public Ship Ship;
	
	public Vector2 Location;
	public Vector2 Velocity;
	public float Rotation;
	
	public Circle Collision;
	
	public float Lifetime;
	public float Brightness;
	
	public float[] DrawPoints;
	public Polygon Polygon;
	
	public boolean debug = false;

	public PlayerBullet(Ship inShip, Vector2 inLocation, float inRotation, float VPlus)
	{
		Ship = inShip;
		
		Location = inLocation;
		Rotation = inRotation;
		
		Velocity = new Vector2(0, 5);
		Velocity.rotate(Rotation);
		
		Collision = new Circle(Location.x, Location.y, 1);
		
		DrawPoints = new float[] { 0, 1, 1, 0, 0, -3, -1, 0, 0, 1 };
		Polygon = new Polygon(DrawPoints);
		
		Lifetime = 0f;
		Brightness = 1f;
	}
	
	public void Update(float Delta)
	{
		Location.add(Velocity);

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
		
		Collision.setPosition(Location);
		
		// Check collision
		for (Asteroid a : Ship.Screen.Asteroids)
		{
			if (this.Collision.overlaps(a.Collision) && !Ship.IsDead)
			{
				a.TakeHit();
				Ship.BulletExpired(this);
				break;
			}
		}
		
		Lifetime += Delta;
		
		if (Lifetime > 3)
		{
			Ship.BulletExpired(this);
		}
		
		Brightness -= Delta / 4;
	}
	
	public void Render(ShapeRenderer Renderer)
	{
		Renderer.begin(ShapeType.Line);
		Renderer.setColor(Brightness,Brightness,Brightness,1);
		Polygon.setScale(1.5f, 1.5f);
		Polygon.setRotation(Rotation);
		Polygon.setPosition(Location.x, Location.y);
		Renderer.polygon(Polygon.getTransformedVertices());

		if (debug)
		{
			Renderer.setColor(1,0,0,1);
			Renderer.circle(Collision.x, Collision.y, Collision.radius);
			Renderer.setColor(1,1,1,1);
		}
		Renderer.end();
	}
}
