package io.jmpalazzolo.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.jmpalazzolo.Constants;

public class Physics extends Component {
    public Body body;
    public Constants.State state;

    public Vector2 acceleration;
    public Vector2 gravity;

    public Physics() {
        state = Constants.State.PENDING;
    }
}
