package io.jmpalazzolo.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class Motion extends Component {
    public Vector2 position;
    public Direction direction;
    public AnimationTypes currentAnimationType;

    public static enum AnimationTypes {
        IDLE,
        RUN,
    }

    public static enum Direction {
        RIGHT,
        LEFT
    }

    public Motion() {
        currentAnimationType = AnimationTypes.IDLE;
        direction = Direction.RIGHT;
    }

    public Motion(int x, int y) {
        position = new Vector2(x, y);
    }
}
