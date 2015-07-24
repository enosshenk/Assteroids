package com.shenko.assteroids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Polygon;

public class Ship {
	
	// Super awesome player ship class
	
	public AssteroidsGameScreen Screen;

	public Vector2 Location;
	public Vector2 Acceleration;
	public Vector2 Velocity;
	
	public float Rotation;
	public float RotationRate;
	public float MaxSpeed;
	
	public float[] DrawPoints;
	public Polygon Polygon;
	
	public Circle Collision;
	
	public boolean debug = false;
	
	public boolean CanFire;
	public float FireTime;
	
	public boolean IsDead;
	
	public List<PlayerBullet> Bullets;
	
	private float InvulnerableTime;
	public boolean IsInvulnerable;
	private float InvulnerableBlinkTime;
	private boolean InvulnerableBlink;
	
	public Ship(AssteroidsGameScreen inScreen, Vector2 inLocation, float inRotation)
	{
		Screen = inScreen;
		
		Location = inLocation;
		Rotation = inRotation;
		
		Velocity = new Vector2(0f, 0f);
		Acceleration = new Vector2(0f, 0f);
		RotationRate = 0f;
		
		DrawPoints = new float[] { 0, 4, 3, -4, 1, -3, 0, -3, -1, -3, -3, -4, 0, 4 };
		Polygon = new Polygon(DrawPoints);
		Polygon.setPosition(Location.x, Location.y);
		Polygon.scale(4.5f);
		Polygon.rotate(Rotation);
		
		Collision = new Circle(Location.x, Location.y, 18);
		
		Bullets = new CopyOnWriteArrayList<PlayerBullet>();
		
		CanFire = true;
		FireTime = 0f;
		
		IsDead = false;
		
		IsInvulnerable = true;
		InvulnerableTime = 0f;		
		InvulnerableBlinkTime = 0f;
	}
	
	public void Update(float Delta)
	{
		// Player ship is invulnerable for 3 seconds after spawning. Blink the drawn lines to show this.
		if (IsInvulnerable)
		{
			InvulnerableTime += Delta;
			
			if (InvulnerableTime > 3)
			{
				IsInvulnerable = false;
			}
			
			InvulnerableBlinkTime += Delta;
			if (InvulnerableBlinkTime > 0.3)
			{
				if (InvulnerableBlink) { InvulnerableBlink = false; } else { InvulnerableBlink = true; }
				InvulnerableBlinkTime = 0f;
			}
		}
		
		// Handle inputs
		int left = (Screen.LeftDown) ? 1 : 0;
		int right = (Screen.RightDown) ? 1 : 0;
		int up = (Screen.UpDown) ? 1 : 0;
		int down = (Screen.DownDown) ? 1 : 0;		
		
		// Update our rotation rate
		RotationRate = (left * 3f) + (right * -3f);
		
		// Update acceleration vector, then rotate it by our rotation
		Acceleration.setZero();
		Acceleration.y = (up * 0.1f) + (down * -0.1f);
		Acceleration.rotate(Rotation);
		
		// Shoot boolet maybe
		FireTime += Delta;
		
		if (FireTime > 0.3f)
		{
			CanFire = true;
		}
		
		if (Screen.FireDown && CanFire)
		{
			float[] Points = Polygon.getTransformedVertices();
			Vector2 FirePoint = new Vector2(Points[0], Points[1]);
			
			// We shoot, add another bullet to our list
			Bullets.add(new PlayerBullet(this, FirePoint, Rotation, Velocity.len2()));
			
			CanFire = false;
			FireTime = 0f;
		}
		
		// Set velocity
		Velocity.add(Acceleration);
		Velocity.limit(5f);
		Velocity.scl(0.99f);
		
		// Update location
		Location.add(Velocity);
		
		// Screen wrap
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
		
		// Rotation
		Rotation += RotationRate;
		
		// Update polygon and collision
		Polygon.setPosition(Location.x, Location.y);
		Polygon.rotate(RotationRate);
		
		Collision.setPosition(Location);
		
		// Tell all bullets to update
		for (PlayerBullet B: Bullets)
		{
			if (B != null)
			{
				B.Update(Delta);
			}
		}
		
		if (Screen.UpDown)
		{
			float[] Points = Polygon.getTransformedVertices();
			Vector2 ExhaustPoint = new Vector2(Points[6], Points[7]);
			
			Screen.Particles.add(
					new Particle(
							Screen,
							ExhaustPoint.add(Screen.randomUnitVector()),
							Velocity.cpy().rotate(180).scl(2).add(Screen.randomUnitVector()),
							0.5f
							)
					);
		}

	}
	
	public void Render(ShapeRenderer Renderer)
	{
		
		if (!IsDead)
		{
			Renderer.begin(ShapeType.Line);
			
			if (IsInvulnerable)
			{
				if (InvulnerableBlink)
				{
					Renderer.setColor(0.5f,0.5f,0.5f,1);
				}
				else
				{
					Renderer.setColor(1,1,1,1);
				}
			}
			
			Renderer.polygon(Polygon.getTransformedVertices());
			if (debug)
			{
				Renderer.setColor(1, 0, 0, 1);
				Renderer.circle(Collision.x, Collision.y, Collision.radius);
				Renderer.setColor(1,1,1,1);
			}
			Renderer.end();
			
			for (PlayerBullet B : Bullets)
			{
				B.Render(Renderer);
			}
		}
	}
	
	public void BulletExpired(PlayerBullet theBullet)
	{
		Bullets.remove(theBullet);
	}
}
