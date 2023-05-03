package com.team13.piazzapanic;

import Sprites.PlateStation;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.World;

public interface Playable extends Screen {
    World getWorld();

    void resetIdleTimer();

    PlateStation getPlateStation();
}
