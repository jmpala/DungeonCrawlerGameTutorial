package io.jmpalazzolo.systems;

import com.artemis.*;
import com.artemis.annotations.All;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import io.jmpalazzolo.Constants;
import io.jmpalazzolo.components.Motion;
import io.jmpalazzolo.components.Physics;
import io.jmpalazzolo.components.PlayerInput;

@All({Motion.class, PlayerInput.class, Physics.class})
public class PlayerInputSystem extends FluidIteratingSystem {

    private float delta;

    @Override
    protected void process(E e) {
//        Gdx.app.log("LOAD", "PlayerInputSystem");
        delta = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            e.physicsBody().applyLinearImpulse(new Vector2(0,Constants.PLAYER_MOVEMENT_SPEED * delta), e.physicsBody().getPosition(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            e.physicsBody().applyLinearImpulse(new Vector2(0, -(Constants.PLAYER_MOVEMENT_SPEED * delta)), e.physicsBody().getPosition(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            e.motionDirection(Motion.Direction.LEFT);
            e.physicsBody().applyLinearImpulse(new Vector2( -(Constants.PLAYER_MOVEMENT_SPEED * delta), 0), e.physicsBody().getPosition(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            e.motionDirection(Motion.Direction.RIGHT);
            e.physicsBody().applyLinearImpulse(new Vector2( Constants.PLAYER_MOVEMENT_SPEED * delta, 0), e.physicsBody().getPosition(),true);
        }

        if( !(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) && e.physicsState() == Constants.State.CREATED) {
            e.physicsBody().setLinearVelocity(0f,0f);
        }
    }
}
