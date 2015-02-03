package com.shenko.assteroids;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;

public class AssteroidsGame extends Game {
	
	@Override
	public void create () {
		setScreen(new AssteroidsGameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
