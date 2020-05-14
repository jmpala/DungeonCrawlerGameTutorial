package io.jmpalazzolo.systems;

import com.artemis.E;
import com.artemis.FluidIteratingSystem;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.jmpalazzolo.components.Motion;
import io.jmpalazzolo.components.Spatial;
import org.reflections.vfs.Vfs;

import java.util.Map;

@All({Spatial.class, Motion.class})
public class RenderSystem extends FluidIteratingSystem {

    private SpriteBatch batch;
    private float timeRendering;
    private TextureRegion currentFrame;
    private  Map<Motion.AnimationTypes, Animation<TextureRegion>> animationMap;

    @Wire(name = "orthographicCamera")
    private OrthographicCamera orthographicCamera;

    @Override
    protected void initialize() {
        super.initialize();

        timeRendering = 0f;
        batch = new SpriteBatch();
    }

    @Override
    protected void begin() {
        batch.begin();
    }

    @Override
    protected void process(E e) {
//        Gdx.app.log("LOAD", "RenderSystem");
        timeRendering += Gdx.graphics.getDeltaTime();

        if(e.hasPlayerInput()){
            orthographicCamera.position.set(e.getMotion().position.x, e.getMotion().position.y, 0);
            orthographicCamera.update();
        }

        animationMap = e.spatialEntityAnimations();
        if(e.motionCurrentAnimationType() == Motion.AnimationTypes.RUN) {
            currentFrame = animationMap.get(Motion.AnimationTypes.RUN).getKeyFrame(timeRendering);
        } else if (e.motionCurrentAnimationType() == Motion.AnimationTypes.IDLE) {
            currentFrame = animationMap.get(Motion.AnimationTypes.IDLE).getKeyFrame(timeRendering);
        }


        // Corrects graphic facing direction, upon where the entity is going (Motion.Direction.LEFT or Motion.Direction.RIGHT)
        if (e.motionDirection() ==  Motion.Direction.LEFT) {
            batch.draw(currentFrame, e.getMotion().position.x + currentFrame.getRegionWidth(), e.getMotion().position.y, -(currentFrame.getRegionWidth()), currentFrame.getRegionHeight());
        } else {
            batch.draw(currentFrame, e.getMotion().position.x , e.getMotion().position.y);
        }

        batch.setProjectionMatrix(orthographicCamera.combined);
}

    @Override
    protected void end() {
        batch.end();
    }


}
