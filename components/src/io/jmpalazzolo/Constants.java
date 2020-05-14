package io.jmpalazzolo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    // Config variables for the LibGDX Game.class
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final String TITLE = "Basic Artemis Game";

    // Config variables for Camera
    public static final int CAMERA_ZOOM = 5;
    public static final int CAMERA_WIDTH = 1920 / CAMERA_ZOOM;
    public static final int CAMERA_HEIGHT = 1080 / CAMERA_ZOOM;

    // Utility position variables
    public static final int PIXELS_PER_CELL = 16;
    public static final int ROWS = 25;
    public static final int COLUMNS= 25;
    public static final float MAP_MIDDLE_SCREEN_X = (PIXELS_PER_CELL * COLUMNS) / 2;
    public static final float MAP_MIDDLE_SCREEN_y = (PIXELS_PER_CELL * ROWS) / 2;

    public static final int MIDDLE_SCREEN_X = Gdx.graphics.getWidth() / 2;
    public static final int MIDDLE_SCREEN_y = Gdx.graphics.getHeight() / 2;

    // Utility physics variables
    public static final float X_GRAVITY = 0;
    public static final float Y_GRAVITY = 0;
    public static final Vector2 BOX2DWORLD_GRAVITY = new Vector2(X_GRAVITY, Y_GRAVITY);
    public static final float TIME_STEP = 1/60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;
    public static final float PLAYER_MOVEMENT_SPEED = 700;

    public enum State {
        CREATED,
        PENDING
    }
}
