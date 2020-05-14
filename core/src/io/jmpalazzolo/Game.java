package io.jmpalazzolo;

import com.artemis.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.jmpalazzolo.managers.GameStateManager;
import io.jmpalazzolo.systems.PhysicsSystem;
import io.jmpalazzolo.systems.PlayerInputSystem;
import io.jmpalazzolo.systems.RenderSystem;
import io.jmpalazzolo.systems.SoundSystem;

public class Game extends ApplicationAdapter {

	// Artemis world, is a container, were all the entities, systems and components live in peace and harmony
	private World artemisWorld;

	//TODO: Description
	private com.badlogic.gdx.physics.box2d.World box2DWorld;

	private GameStateManager gsm;

	private OrthographicCamera orthographicCamera;

	private AssetManager assetManager;

	@Override
	public void create () {

		// Artemis Configuration and loading
		WorldConfiguration configuration = new WorldConfigurationBuilder()
				.dependsOn(FluidEntityPlugin.class)
				.with(new PlayerInputSystem(), new RenderSystem(), new SoundSystem(), new PhysicsSystem())
				.build();

		// We add some variables to be injected later through @Wire
		// Also the box2DWorld is given a gravity Vector and a doSleep of True, so un-active Bodies don't use processing power
		// (AKA: program runs faster), but there are some simulations that need to process all the bodies that are contained on the world
		box2DWorld = new com.badlogic.gdx.physics.box2d.World(Constants.BOX2DWORLD_GRAVITY,true);
		configuration.register("box2DWorld",box2DWorld);
		orthographicCamera = new OrthographicCamera();
		configuration.register("orthographicCamera",orthographicCamera);
		assetManager = new AssetManager();
		assetManager.load("knight/greg_knight.atlas", TextureAtlas.class);
		assetManager.load("music/ndungeon.mp3", Music.class);
		assetManager.load("sounds/walk.wav", Sound.class);
		assetManager.finishLoading();
		configuration.register("assetManager",assetManager);

		artemisWorld = new World(configuration);

		// Init of gsm and Set Play screen
		gsm = new GameStateManager(artemisWorld, box2DWorld, orthographicCamera, assetManager);
		gsm.setState(GameStateManager.States.PLAY);

		// Fetch and play world music
		Music gameMusic = assetManager.get("music/ndungeon.mp3", Music.class);
		gameMusic.setLooping(true);
		gameMusic.setVolume(1f);
		gameMusic.play();
	}

	public void update() {
		gsm.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void render () {
		update();

		gsm.render();
	}
	
	@Override
	public void dispose () {
		//TODO: dispose not implemented!!
	}
}
