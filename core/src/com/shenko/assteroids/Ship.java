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
	
	public boolean CanFire;					// If true, pressing space fires a new bullet
	public float FireTime;					// Time elapsed since firing a shot
	
	public boolean IsDead;
	public float RespawnTimeElapsed;
	public boolean CanRespawn;
	
	public List<PlayerBullet> Bullets;
	
	public boolean IsInvulnerable;			// If true, player doesn't take damage from being hit
	private float InvulnerableTime;			// Time remaining on invulnerable
	private float InvulnerableBlinkTime;	// Used to time the blinking state
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
		RespawnTimeElapsed = 0f;
		CanRespawn = false;
		
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
		
		// Update acceleration vector, then rotate it by our rotation
		Acceleration.setZero();
		if (!IsDead)
		{
			// Update our rotation rate
			RotationRate = (left * 3f) + (right * -3f);
			
			Acceleration.y = (up * 0.1f) + (down * -0.1f);
			Acceleration.rotate(Rotation);
		}
		else
		{
			// Do respawn timer
			RespawnTimeElapsed += Delta;
			if (RespawnTimeElapsed >= 3)
			{
				CanRespawn = true;
			}
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
		
		// Shoot boolet maybe
		FireTime += Delta;
		
		if (FireTime > 0.3f)
		{
			CanFire = true;
		}
		
		if (Screen.FireDown && CanFire && !IsDead)
		{
			// Get a coordinate to spawn the bullet at. Nose of our ship transformed into world space.
			float[] Points = Polygon.getTransformedVertices();
			Vector2 FirePoint = new Vector2(Points[0], Points[1]);
			
			// We shoot, add another bullet to our list
			Bullets.add(new PlayerBullet(this, FirePoint, Rotation, Velocity.len2()));
			
			CanFire = false;
			FireTime = 0f;
		}
		
		// Tell all bullets to update
		for (PlayerBullet B: Bullets)
		{
			if (B != null)
			{
				B.Update(Delta);
			}
		}
		
		// If we're holding accelerate, add some exhaust particles
		if (Screen.UpDown && !IsDead)
		{
			// Get coordinate for the exhaust particles
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
		
		// Handle keypress to respawn
		if (IsDead && Screen.FireDown)
		{
			Screen.RespawnShip();
			
			// Reset all our shit
			Location.x = Gdx.graphics.getWidth() / 2;
			Location.y = Gdx.graphics.getHeight() / 2;
			
			Rotation = 0;
			RotationRate = 0;
			Acceleration.setZero();
			Velocity.setZero();
			
			IsInvulnerable = true;
			InvulnerableTime = 0f;		
			InvulnerableBlinkTime = 0f;
			
			Polygon.setPosition(Location.x, Location.y);
			Polygon.setRotation(Rotation);
			Collision.setPosition(Location);
			
			IsDead = false;
		}
		
	}
	
	public void Render(ShapeRenderer Renderer)
	{
		
		if (!IsDead)
		{
			Renderer.begin(ShapeType.Line);
			
			// Handle invulnerable blinking by drawing at half brightness
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
			
			// Render that sweet sexy polygon
			Renderer.polygon(Polygon.getTransformedVertices());
			if (debug)
			{
				Renderer.setColor(1, 0, 0, 1);
				Renderer.circle(Collision.x, Collision.y, Collision.radius);
				Renderer.setColor(1,1,1,1);
			}
			Renderer.end();
			
			// Render all our bullets
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
	
	public void Hit()
	{
		// We got our asses plowed
		IsDead = true;
		CanRespawn = false;
		RespawnTimeElapsed = 0f;
	}
}
