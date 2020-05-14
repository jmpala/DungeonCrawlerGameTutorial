package io.jmpalazzolo.managers;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.jmpalazzolo.states.GameState;
import io.jmpalazzolo.states.PlayState;

import java.util.Stack;

/**
 * This class manages the application different states, showing, hiding and eliminating the corresponding screens
 */
public class GameStateManager {

    private GameStateManager gsm;

    private Stack<GameState> gameStates;

    private World artemisWorld;

    private com.badlogic.gdx.physics.box2d.World box2DWorld;

    private OrthographicCamera orthographicCamera;

    private AssetManager assetManager;

    public enum States {
        MENU,
        PLAY,
        LOSE,
        WIN
    }

    public GameStateManager(World artemisWorld, com.badlogic.gdx.physics.box2d.World box2DWorld, OrthographicCamera orthographicCamera, AssetManager assetManager) {
        gameStates = new Stack<>();
        this.artemisWorld = artemisWorld;
        this.box2DWorld = box2DWorld;
        this.orthographicCamera = orthographicCamera;
        this.assetManager = assetManager;
    }

    /**
     * Push a state into the Stack
     * @param gameState
     */
    public void addState(GameState gameState) {
        gameStates.push(gameState);
    }

    /**
     * Pops when size() > 0 and Push a state into the Stack
     * @param gameState
     */
    public void UpdateState(GameState gameState) {
        if(gameStates.size() > 0) {
            gameStates.pop();
        }
        gameStates.push(gameState);
    }

    /**
     * Renders current screen
     */
    public void render() {
        gameStates.peek().render();
    }

    /**
     * Executes update method of current screen
     * @param dt - Delta Time
     */
    public void update(float dt) {
        gameStates.peek().update(dt);
    }

    /**
     * Change the state of the game, to go to another screen
     * @param state - Enum of GameStateManager.States
     * @return
     */
    //TODO: See if it better to manage the screen change inside this method or returning the state
    public void setState(States state) {
        switch (state) {
            case MENU:
                //TODO: Create Menu state screen
                break;
            case PLAY:
                UpdateState(new PlayState(this, artemisWorld, box2DWorld, orthographicCamera, assetManager));
                break;
            case LOSE:
                //TODO: Create Lose state screen
                break;
            case WIN:
                //TODO: Create Win state screen
                break;
            default:
                Gdx.app.error("ERROR","Screen does not exist, state: " + state, new RuntimeException());
                break;
        }
    }

}
