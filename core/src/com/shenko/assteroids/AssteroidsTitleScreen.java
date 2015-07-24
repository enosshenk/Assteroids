package com.shenko.assteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class AssteroidsTitleScreen implements Screen {
	
	// A screen to draw the game title and wait for player input to start the game
	
	private AssteroidsGame Game;

	private OrthographicCamera Camera;
	
	private ShapeRenderer Renderer;
	private TextRenderer Text;
	
	private float TitleScale;		// Current scale of the title, will be lerped down
	private float TitleAlpha;		// Current alpha of the title, will be lerped up quickly for a little fade in
	
	private boolean SpaceDown;
	
	private float[] Starfield;		// Some random points for stars in the background
	
	public AssteroidsTitleScreen(AssteroidsGame inGame)
	{
		Game = inGame;
	}
	
	@Override
	public void show() {
		
		Renderer = new ShapeRenderer();
		Text = new TextRenderer();
		
		Camera = new OrthographicCamera();
		Camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 1);		
		
		TitleScale = 50f;
		TitleAlpha = 0f;
		
		SpaceDown = false;
		
		Starfield = new float[40];
		for (int i=0; i<40; i++)
		{
			Starfield[i] = MathUtils.random(0, Gdx.graphics.getWidth());
		}
	}

	@Override
	public void render(float delta) {
		
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        Camera.update();
        
		Renderer.setProjectionMatrix(Camera.combined);
		Renderer.setColor(1,1,1,1);	
		
		Renderer.begin(ShapeType.Line);
		for(int i=0; i<Starfield.length/2; i += 2)
		{
			// Draw star points, with a random size so they flicker
			Renderer.circle(Starfield[i], Starfield[i+1], MathUtils.random(0.5f, 2f));
		}
		Renderer.end();
		
		// Draw the title text with the scaling and alpha
		Text.DrawTextCentered(Renderer, "ASSTEROIDS", new Vector2( (float)(Gdx.graphics.getWidth() / 2), (float)(Gdx.graphics.getHeight() * 0.5) ), TitleScale);		
	
		if (TitleScale > 10)
		{
			// LERP DAT SHIT
			TitleScale = MathUtils.lerp(TitleScale, 10, TitleAlpha);
		}
		else
		{
			TitleScale = 10;
			Text.DrawTextCentered(Renderer, "PRESS SPACE TO BEGIN", new Vector2( (float)(Gdx.graphics.getWidth() / 2), (float)(Gdx.graphics.getHeight() * 0.2) ), 5);
			Text.DrawTextCentered(Renderer, "SHENKO HEAVY INDUSTRIES", new Vector2( (float)(Gdx.graphics.getWidth() / 2), (float)(Gdx.graphics.getHeight() * 0.1) ), 3);
		}
		TitleAlpha += delta / 5;
		
		SpaceDown = Gdx.input.isKeyPressed(Keys.SPACE);
		
		if (SpaceDown)
		{
			Game.NewGame();
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
