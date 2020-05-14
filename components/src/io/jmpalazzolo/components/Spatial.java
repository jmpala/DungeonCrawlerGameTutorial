package io.jmpalazzolo.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class Spatial extends Component {
    public Texture texture;

    public Map<Motion.AnimationTypes, Animation<TextureRegion>> entityAnimations;

    public Spatial() {
        entityAnimations = new HashMap<>();
    }

    public Spatial(Texture texture) {
        this.texture = texture;
    }
}
