package io.jmpalazzolo.systems;

import com.artemis.E;
import com.artemis.FluidIteratingSystem;
import com.artemis.annotations.All;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.Map;

@All({io.jmpalazzolo.components.Sound.class})
public class SoundSystem extends FluidIteratingSystem {

    private Sound sound;
    private Map<io.jmpalazzolo.components.Sound.Sounds, Sound> soundsMap;
    private float timePast;

    public SoundSystem() {
        timePast = 0;
    }

    @Override
    protected void process(E e) {

        switch (e.soundSoundToPlay()) {
            case NONE:
                break;
            case WALK:
                if(timePast > 1f/3f) {
                    soundsMap = e.soundSoundsMap();
                    sound = soundsMap.get(io.jmpalazzolo.components.Sound.Sounds.WALK);
                    sound.play(0.5f);
                    timePast = 0;
                }

                break;
            default:
                Gdx.app.error("ERROR", "Sound does not exist", new RuntimeException());
        }
        timePast += Gdx.graphics.getDeltaTime();
    }
}
