package io.jmpalazzolo.components;

import com.artemis.Component;

import java.util.HashMap;
import java.util.Map;

public class Sound extends Component {
    public Sounds soundToPlay;
    public Map<Sounds, com.badlogic.gdx.audio.Sound> soundsMap;

    public enum Sounds {
        NONE,
        WALK,
    }

    public Sound() {
        soundsMap = new HashMap<>();
        soundToPlay = Sounds.NONE;
    }
}
