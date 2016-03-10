package com.shenko.assteroids;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.*;
import com.shenko.assteroids.Asteroid.ESize;

public class AssteroidsGameScreen implements Screen {
	
	public AssteroidsGame Game;
	
	private ShapeRenderer Shape;
	private OrthographicCamera Camera;
	
	public MathUtils Math;
	
	public List<Asteroid> Asteroids;		// List of asteroids currently on screen
	public Ship Ship;						// The players current ship
	
	public boolean LeftDown;				// Control booleans
	public boolean RightDown;
	public boolean UpDown;
	public boolean DownDown;
	public boolean FireDown;
	
	public int Score;						// Player's current score
	public int Lives;						// How many lives does the player have
	public ScoreRenderer ScoreRenderer;		// Class to render score elements
	public TextRenderer TextRenderer;		// Class to render text on screen
	
	public List<Particle> Particles;		// List of currently rendered particles
	public List<ShipGib> Gibs;				// List of currently rendered ship giblets
	
	public float LevelResetTimeElapsed;		// Time elapsed since last level was completed
	public int Level;
	
											// Game states yay
	public enum EGameMode { STARTUP, RUNNING, LEVELDONE, PLAYERDEAD, GAMEOVER }
	public EGameMode Mode = EGameMode.STARTUP;
	
	public AssteroidsGameScreen(AssteroidsGame inGame)
	{
		Game = inGame;
	}

	@Override
	public void show() {
	
		Shape = new ShapeRenderer();
		
		// Set up the camera
		Camera = new OrthographicCamera();
		Camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 1);
		
		// Set up lists
		Asteroids = new CopyOnWriteArrayList<Asteroid>();
		Particles = new CopyOnWriteArrayList<Particle>();
		Gibs = new CopyOnWriteArrayList<ShipGib>();
		
		// Initial score and lives
		Score = 0;
		Lives = 3;
		
		// Instantiate renderers
		ScoreRenderer = new ScoreRenderer(this);
		TextRenderer = new TextRenderer();
		
		// Call function that generates asteroids
		NewLevel();
		
		LeftDown = false;
		RightDown = false;
		UpDown = false;
		DownDown = false;
		FireDown = false;
		
	}

	@Override
	public void render(float delta) {
		
		// Handle inputs
		LeftDown = Gdx.input.isKeyPressed(Keys.LEFT);
		RightDown = Gdx.input.isKeyPressed(Keys.RIGHT);
		UpDown = Gdx.input.isKeyPressed(Keys.UP);
		DownDown = Gdx.input.isKeyPressed(Keys.DOWN);
		FireDown = Gdx.input.isKeyPressed(Keys.SPACE);
		
		if (Ship != null) { Ship.Update(delta); }
		
		// Update all game objects
		for (Asteroid a : Asteroids)
		{
			a.Update(delta);
		}
		for (Particle p : Particles)
		{
			p.Update(delta);
		}
		for (ShipGib g : Gibs)
		{
			g.Update(delta);
		}
		
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        Camera.update();
        
		Shape.setProjectionMatrix(Camera.combined);
		Shape.setColor(1,1,1,1);
		
		// If the ship exists, tell it to render
		if (Ship != null) { Ship.Render(Shape); }
		
		// Check to see if all the asteroids are destroyed
		if (Mode == EGameMode.RUNNING && Asteroids.isEmpty())
		{
			LevelClear();
		}
		
		// Render game objects
		for (Asteroid a : Asteroids)
		{
			a.Render(Shape);
		}
		for (Particle p : Particles)
		{
			p.Render(Shape);
		}
		for (ShipGib g : Gibs)
		{
			g.Render(Shape);
		}
		
		// Do level transition
		if (Mode == EGameMode.LEVELDONE)
		{
			LevelResetTimeElapsed += delta;
			
			String Text = "LEVEL " + String.valueOf(Level) + " CLEARED";
			
			TextRenderer.DrawTextCentered(Shape, Text, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), 4);
			
			if (LevelResetTimeElapsed >= 3)
			{
				// Shown that enough, get back to the killing
				NewLevel();
			}
		}
		
		// Draw the UI if we're in gamerunning state
		if (Mode == EGameMode.RUNNING || Mode == EGameMode.PLAYERDEAD || Mode == EGameMode.LEVELDONE)
		{
			ScoreRenderer.DrawScore( Shape, Score, new Vector2(Gdx.graphics.getWidth() * 0.01f, Gdx.graphics.getHeight() * 0.9f), 8 );
			ScoreRenderer.DrawShips( Shape, Lives, new Vector2(Gdx.graphics.getWidth() * 0.01f, Gdx.graphics.getHeight() * 0.82f), 5 );
		}
	}
	
	public void NewLevel()
	{
		Mode = EGameMode.RUNNING;
		
		Level += 1;
		
		// ADD INCREASING SPAWNS FOR HIGHER LEVELS LATER
		for (int i=0; i < 3; i++)
		{
				// Populate a few randomly generated asteroids on screen
				Asteroids.add( 
						new AsteroidLarge(
							this,
							ESize.LARGE, 
							new Vector2( GetAsteroidSpawnLoc() ),
							new Vector2( 
									(float)(Math.random(-1f, 1f)),
									(float)(Math.random(-1f, 1f))
							)
						));
		}
			
		// Spawn the player ship
		if (Ship == null)
		{
			Ship = new Ship(this, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), 0);	
		}
	}
	
	private Vector2 GetAsteroidSpawnLoc()
	{
		// Generate spawn locations at the edges of the screen
		Vector2 OutLoc = new Vector2();
		
		OutLoc.x = Math.random(-50, 50);
		if (Math.randomBoolean())
		{
			OutLoc.x += Gdx.graphics.getWidth() / 2;
		}
		else
		{
			OutLoc.x -= Gdx.graphics.getWidth() / 2;
		}
		
		OutLoc.y = Math.random(-50, 50);
		if (Math.randomBoolean())
		{
			OutLoc.y += Gdx.graphics.getHeight() / 2;
		}
		else
		{
			OutLoc.y -= Gdx.graphics.getHeight() / 2;
		}		
		
		return OutLoc;
	}
	
	public void LevelClear()
	{
		// You beat the level you big mlg pro gamer
		Mode = EGameMode.LEVELDONE;
		LevelResetTimeElapsed = 0f;
	}
	
	public void AsteroidExpired(Asteroid inAsteroid)
	{
		// Function called when an asteroid takes a shot from the ship bullet
		// Why did I call this AsteroidExpired? I don't know.
		Vector2 SpawnLocation = new Vector2();
		Vector2 SpawnVelocity = new Vector2();
		
		switch (inAsteroid.Size)
		{
			// Large and medium asteroids split into two of the next smaller class
			case LARGE:
				for (int i=0; i<2; i++)
				{
					SpawnLocation = inAsteroid.Location.add(randomUnitVector().scl(50));
					SpawnVelocity = inAsteroid.Velocity.add(randomUnitVector());
					PlayAsteroidExplosion(new Vector2(inAsteroid.Location.cpy()));
					Asteroids.add( new AsteroidMedium(this, Asteroid.ESize.MEDIUM, SpawnLocation.cpy(), SpawnVelocity.cpy()) );
				}
				Score += 20;
				break;
				
			case MEDIUM:
				for (int i=0; i<2; i++)
				{
					SpawnLocation = inAsteroid.Location.add(randomUnitVector().scl(30));
					SpawnVelocity = inAsteroid.Velocity.add(randomUnitVector());
					PlayAsteroidExplosion(new Vector2(inAsteroid.Location.cpy()));
					Asteroids.add( new AsteroidSmall(this, Asteroid.ESize.SMALL, SpawnLocation.cpy(), SpawnVelocity.cpy()) );
				}
				Score += 50;
				break;
				
			// Small asteroids are destroyed
			case SMALL:
				PlayAsteroidExplosion(inAsteroid.Location.cpy());
				Score += 100;
				break;
				
			default:				
				break;		
		}
		
		Asteroids.remove(inAsteroid);
	}
	
	public void PlayAsteroidExplosion(Vector2 inLocation)
	{
		// Adds some random particles to the particle list
		Vector2 SpawnLocation = new Vector2();
		Vector2 SpawnVelocity = new Vector2();
		
		for (int i=0; i<10; i++)
		{		
			// Give it a source location and a random velocity
			SpawnLocation = inLocation.cpy().add( randomUnitVector() );
			SpawnVelocity = SpawnLocation.cpy().sub(inLocation);
			
			Particles.add( new Particle( this, SpawnLocation.cpy(), SpawnVelocity.cpy(), 1 ) );
		}
	}
	
	public void PlayShipExplosion(Vector2 inLocation, Vector2 inVelocity)
	{
		// Gibs first
		Vector2 GibSpawnLocation = new Vector2();
		Vector2 GibSpawnVelocity = new Vector2();
		float NumGibs = Math.random(2,4);
		
		for (int i=0; i<NumGibs; i++)
		{
			GibSpawnLocation = inLocation.cpy().add( randomUnitVector().scl(4) );
			GibSpawnVelocity = inVelocity.cpy().add(randomUnitVector());
			
			Gibs.add( new ShipGib(this, GibSpawnLocation.cpy(), GibSpawnVelocity.cpy()) );
		}
		
		// Particles
		Vector2 SpawnLocation = new Vector2();
		Vector2 SpawnVelocity = new Vector2();
		
		for (int i=0; i<20; i++)
		{		
			SpawnLocation = inLocation.cpy().add( randomUnitVector().scl(3) );
			SpawnVelocity = SpawnLocation.cpy().sub(inLocation);
			SpawnVelocity.add(inVelocity.cpy());
			
			Particles.add( new Particle( this, SpawnLocation.cpy(), SpawnVelocity.cpy(), Math.random(0.5f, 1f) ) );
		}
		
		Lives -= 1;
		
		if (Lives < 0)
		{
			// Game over man, game over
			Mode = EGameMode.GAMEOVER;
		}
		else
		{
			// Switch modes and wait for respawn
			Mode = EGameMode.PLAYERDEAD;
		}
	}
	
	public void RespawnShip()
	{
		// Stuff should probably go here
	}
	
	public void ParticleExpired(Particle inParticle)
	{
		Particles.remove(inParticle);
	}
	
	public void GibExpired(ShipGib inGib)
	{
		PlayAsteroidExplosion(inGib.Location);
		Gibs.remove(inGib);		
	}
	
	public Vector2 randomUnitVector() {
		Vector2 out = new Vector2();
		do {
		  // Generate a random vector in the cube [-1,1]x[-1,1]x[-1,1]
		  out.x = MathUtils.random(-1f, 1f);
		  out.y = MathUtils.random(-1f, 1f);
		  // Test if it's inside the unit sphere - if not, try again.
		} while (out.len2() > 1f || out.isZero());
			out.nor(); // Normalize
			return out;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
