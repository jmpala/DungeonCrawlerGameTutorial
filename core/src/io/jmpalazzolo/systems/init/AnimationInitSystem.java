package io.jmpalazzolo.systems.init;

import com.artemis.E;
import com.artemis.FluidIteratingSystem;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.jmpalazzolo.components.Spatial;

@All(Spatial.class)
public class AnimationInitSystem extends FluidIteratingSystem {

    @Wire(name = "assetManager")
    private AssetManager assetManager;

    @Override
    protected void process(E e) {

    }

}
