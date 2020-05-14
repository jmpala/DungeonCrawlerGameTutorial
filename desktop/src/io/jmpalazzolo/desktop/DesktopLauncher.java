package io.jmpalazzolo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.jmpalazzolo.Constants;
import io.jmpalazzolo.Game;

public class DesktopLauncher {

	// START HERE, like always the all mighty "public static void main"
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// Added to the config of the App some values
		config.width = Constants.WIDTH; // Sets the Width of the screen
		config.height = Constants.HEIGHT; // Sets the Height of the screen
		config.title = Constants.TITLE; // Sets the String to show as title

		// The application launches the class Game.class with its own configuration
		new LwjglApplication(new Game(), config);
	}
}
