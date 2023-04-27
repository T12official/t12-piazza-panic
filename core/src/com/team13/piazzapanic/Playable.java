package com.team13.piazzapanic;

import Recipe.Order;
import Sprites.PlateStation;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public interface Playable extends Screen {
    public World getWorld();

    public void resetIdleTimer();

    public PlateStation getPlateStation();
}
