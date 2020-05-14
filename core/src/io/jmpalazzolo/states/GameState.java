package io.jmpalazzolo.states;

import io.jmpalazzolo.managers.GameStateManager;

public abstract class GameState {

    protected GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void render();

    public abstract void update(float dt);
}
