package com.shenko.assteroids;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
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
	
	public List<Asteroid> Asteroids;
	
	public AssteroidsGameScreen(AssteroidsGame inGame)
	{
		Game = inGame;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		Shape = new ShapeRenderer();
		
		Camera = new OrthographicCamera();
		Camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 1);
		
		Asteroids = new ArrayList<Asteroid>();
		
		// DEMO
		for (int i=0; i < 3; i++)
		{
			Asteroids.add( 
					new AsteroidLarge(
						this,
						ESize.LARGE, 
						new Vector2( 
								(float)(Math.random(0, 800)),
								(float)(Math.random(0, 600))
						),
						new Vector2( 
								(float)(Math.random(-2,2)), 
								(float)(Math.random(-2,2))	
						)
					));
		}
	}

	@Override
	public void render(float delta) {
		
		for (Asteroid a : Asteroids)
		{
			a.Update(delta);
		}
		
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        Camera.update();
        
		Shape.setProjectionMatrix(Camera.combined);
		Shape.setColor(1,1,1,1);
		
		for (Asteroid a : Asteroids)
		{
			a.Render(Shape);
		}
		
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
