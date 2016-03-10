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

	public Vector2 Location;				// On screen location
	public Vector2 Acceleration;			// Acceleration vector
	public Vector2 Velocity;				// Resolved velocity vector
	
	public float Rotation;					// Current rotation
	public float RotationRate;				// Rate of rotation
	public float MaxSpeed;					// Cap for ship velocity
	
	public float[] DrawPoints;				// All our points to form the polygon to draw
	public Polygon Polygon;					// Polygon to render
	
	public Circle Collision;
	
	public boolean debug = false;
	
	public boolean CanFire;					// If true, pressing space fires a new bullet
	public float FireTime;					// Time elapsed since firing a shot
	
	public boolean IsDead;					// True if we're dead, duh
	public float RespawnTimeElapsed;		
	public boolean CanRespawn;
	
	public List<PlayerBullet> Bullets;		// List of all bullets spawned by the ship
	
	public boolean IsInvulnerable;			// If true, player doesn't take damage from being hit
	private float InvulnerableTime;			// Time remaining on invulnerable
	private float InvulnerableBlinkTime;	// Used to time the blinking state
	private boolean InvulnerableBlink;
	
	private boolean FireRight = true;		// Used to alternate left/right fire positions
	
	public Ship(AssteroidsGameScreen inScreen, Vector2 inLocation, float inRotation)
	{
		Screen = inScreen;
		
		Location = inLocation;
		Rotation = inRotation;
		
		Velocity = new Vector2(0f, 0f);
		Acceleration = new Vector2(0f, 0f);
		RotationRate = 0f;
		
		// Set up array of points to create polygon from
		DrawPoints = new float[] { 
				-3f, -4f,
				-1.9351f, -1.1603f,
				-1.9351f, -0.1603f,
				-1.9351f, -1.1603f,
				0f, 4f,
				1.853f, -0.9414f,
				1.853f, 0.0586f,
				1.853f, -0.9414f,
				3f, -4f,
				1.7357f, -3.3679f,
				-1.7357f, -3.3679f,
				1.7357f, -3.3679f,
				1f, -3f,
				0f, -3f,
				0f, 2.1432f,
				1.853f, -2.6308f,
				0f, 2.1432f,
				-1.9351f, -2.6308f,
				0f, 2.1432f,
				0f, -1.2441f,
				-0.3777f, -1.2441f,
				0f, -1.2441f,
				0f, -1.8474f,
				-0.6564f, -1.8474f,
				0f, -1.8474f,
				0f, -2.4352f,
				-0.8422f, -2.4352f,
				0f, -2.4352f,
				0f, -3f,
				-1f, -3f,
				-3, -4f
			};
		
		// Create polygon and scale it
		Polygon = new Polygon(DrawPoints);
		Polygon.setPosition(Location.x, Location.y);
		Polygon.scale(4.5f);
		Polygon.rotate(Rotation);
		
		// Create collision circle
		Collision = new Circle(Location.x, Location.y, 18);
		
		// Set up bullet list
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
			// Get a coordinate to spawn the bullet at.
			float[] Points = Polygon.getTransformedVertices();
			if (FireRight)
			{
				// Fire location should be on the right
				Vector2 FirePoint = new Vector2(Points[12], Points[13]);
				// We shoot, add another bullet to our list
				Bullets.add(new PlayerBullet(this, FirePoint, Rotation, Velocity.len2()));
				FireRight = false;
			}
			else
			{
				// Fire location should be on the left
				Vector2 FirePoint = new Vector2(Points[2], Points[3]);
				// We shoot, add another bullet to our list
				Bullets.add(new PlayerBullet(this, FirePoint, Rotation, Velocity.len2()));
				FireRight = true;
			}
			
			// Reset for next shot
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
			Vector2 ExhaustPoint = new Vector2(Points[6], Points[7]);  // FIX THIS
			
			// Add particles to the screen list
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
