package com.shenko.assteroids.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.shenko.assteroids.AssteroidsGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Assteroids!";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new AssteroidsGame(), config);
	}
}
