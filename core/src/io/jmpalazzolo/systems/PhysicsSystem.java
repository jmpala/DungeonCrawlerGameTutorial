package io.jmpalazzolo.systems;

import com.artemis.E;
import com.artemis.FluidIteratingSystem;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.jmpalazzolo.Constants;
import io.jmpalazzolo.components.Motion;
import io.jmpalazzolo.components.Physics;
import io.jmpalazzolo.components.Sound;
import io.jmpalazzolo.components.Spatial;

@All({Physics.class, Motion.class})
public class PhysicsSystem extends FluidIteratingSystem {

    @Wire(name = "box2DWorld")
    private World box2DWorld;

    //TODO: see if is better to make a switch that interacts with the Physics states like (pending / created / deleted)
    @Override
    protected void process(E e) {
//        Gdx.app.log("LOAD", "PhysicsSystem");

        Body body;

        switch (e.physicsState()) {
            case PENDING:

                BodyDef bdef = new BodyDef();
                bdef.position.set(e.motionPosition());
                bdef.type = BodyDef.BodyType.DynamicBody; //TODO: see if other objects need other types of bodies and how to set it
                bdef.linearDamping = 1.0f;

                body = box2DWorld.createBody(bdef);

                FixtureDef fdef = new FixtureDef();
                CircleShape shape = new CircleShape();
                shape.setRadius(7); //TODO: Set an option to define oder shapes depending on the entity
                fdef.shape = shape;

                body.createFixture(fdef);

                e.physicsState(Constants.State.CREATED);
                e.physicsBody(body);

                break;
            case CREATED:
                body = e.physicsBody();
                e.motionPosition(new Vector2(body.getPosition().x - (e.spatialTexture().getWidth() / 2), body.getPosition().y - (e.spatialTexture().getHeight() / 2)));

                if(body.getLinearVelocity().x == 0f && body.getLinearVelocity().y == 0f) {
                    e.motionCurrentAnimationType(Motion.AnimationTypes.IDLE);
                    e.soundSoundToPlay(Sound.Sounds.NONE);
                } else {
                    e.motionCurrentAnimationType(Motion.AnimationTypes.RUN);
                    e.soundSoundToPlay(Sound.Sounds.WALK);
                }

                break;
            default:
                Gdx.app.error("PhysicsSystem","STATE NOT DEFINED: " + e.physicsState(), new RuntimeException());
        }

    }
}
;