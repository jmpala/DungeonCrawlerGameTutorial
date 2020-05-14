package io.jmpalazzolo.states;

import com.artemis.E;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.jmpalazzolo.Constants;
import io.jmpalazzolo.components.Motion;
import io.jmpalazzolo.managers.GameStateManager;

import java.util.HashMap;

public class PlayState extends GameState {

    private com.badlogic.gdx.physics.box2d.World box2DWorld;

    private World artemisWorld;

    // Renders Box2D physic boundaries for testing purposes
    private Box2DDebugRenderer box2DDebugRenderer;

    private Viewport viewport;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    private OrthographicCamera orthographicCamera;

    private AssetManager assetManager;

    private float accumulator = 0;

    public PlayState(GameStateManager gsm, World artemisWorld, com.badlogic.gdx.physics.box2d.World box2DWorld, OrthographicCamera orthographicCamera, AssetManager assetManager) {
        super(gsm);
        this.artemisWorld = artemisWorld;
        this.orthographicCamera = orthographicCamera;
        this.box2DWorld = box2DWorld;
        this.assetManager = assetManager;

        tiledMap = new TmxMapLoader().load("map/map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        this.orthographicCamera.setToOrtho(false, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
        this.orthographicCamera.position.set(new Vector2(Constants.MAP_MIDDLE_SCREEN_X, Constants.MAP_MIDDLE_SCREEN_y), 0);
        viewport = new FitViewport(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT, orthographicCamera);

        box2DDebugRenderer = new Box2DDebugRenderer();

        setMapBoundaries();
        loadEntities();
    }

    @Override
    public void update(float dt) {
        artemisWorld.setDelta(dt);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.setView(orthographicCamera);
        tiledMapRenderer.render();

        artemisWorld.process();

        box2DDebugRenderer.render(box2DWorld, orthographicCamera.combined);// Only for debugging purposes

        orthographicCamera.update();

        //TODO: Explain what step does
        doPhysicsStep(Gdx.graphics.getDeltaTime());
    }

    private void loadEntities() {

        HashMap<Motion.AnimationTypes, Animation<TextureRegion>> entityAnimationsMap = new HashMap<>();

        // Loading idle animation
        TextureAtlas textureAtlas = assetManager.get("knight/greg_knight.atlas", TextureAtlas.class);
        Array<TextureAtlas.AtlasRegion> atlasRegions = textureAtlas.findRegions("knight_f_idle_anim");
        entityAnimationsMap.put(Motion.AnimationTypes.IDLE, new Animation(1f/6f, atlasRegions, Animation.PlayMode.LOOP));

        // Loading run animation
        atlasRegions = textureAtlas.findRegions("knight_f_run_anim");
        entityAnimationsMap.put(Motion.AnimationTypes.RUN, new Animation(1f/6f, atlasRegions, Animation.PlayMode.LOOP));

        HashMap<io.jmpalazzolo.components.Sound.Sounds, Sound> entitySoundsMap = new HashMap<>();

        // Loading sound
        Sound sound = assetManager.get("sounds/walk.wav", Sound.class);
        entitySoundsMap.put(io.jmpalazzolo.components.Sound.Sounds.WALK,sound);

        // Example of fluid entity
        E.E(artemisWorld.create())
                .spatialTexture(new Texture(Gdx.files.internal("Player/knight/knight_f_idle_anim_f3.png")))
                .motionPosition(new Vector2(Constants.MAP_MIDDLE_SCREEN_X, Constants.MAP_MIDDLE_SCREEN_y))
                .motionDirection(Motion.Direction.RIGHT)
                .spatialEntityAnimations(entityAnimationsMap)
                .soundSoundsMap(entitySoundsMap)
                .physics()
                .playerInput()
                .entity();

        // Example of non fluid entity

//        artemisWorld.edit(player)
//                .add(new Motion(Constants.MIDDLE_SCREEN_X, Constants.MIDDLE_SCREEN_y))
//                .add(new Spatial(new Texture(Gdx.files.internal("Player/knight/knight_f_idle_anim_f3.png"))))
//                .add(new PlayerInput());
    }

    private void setMapBoundaries() {
        Body body;
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();

        for (MapObject mapObject: tiledMap.getLayers().get("bounds").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

            body = box2DWorld.createBody(bodyDef);

            shape.setAsBox((rect.getWidth() / 2), (rect.getHeight() / 2));
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
    }

    private void doPhysicsStep(float deltaTime){
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= Constants.TIME_STEP) {
            box2DWorld.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
            accumulator -= Constants.TIME_STEP;
        }
    }
}
