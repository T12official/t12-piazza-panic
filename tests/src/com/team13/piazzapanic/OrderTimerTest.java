package com.team13.piazzapanic;

import Ingredients.*;
import Recipe.*;
import Sprites.Chef;
import Sprites.CompletedDishStation;
import Sprites.OrderTimer;
import Sprites.PlateStation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.org.apache.xpath.internal.axes.OneStepIterator;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class OrderTimerTest {

    @Test
    public void OrderDecrementEasyTimerTest(){
        OrderTimer orderTimer = new OrderTimer();
        orderTimer.setDifficulty(MainGame.EASY_DIFFICULTY);
        float timeBefore = orderTimer.getOrderTime();
        float dt = 0.01f;
        orderTimer.update(dt);
        assertEquals("will pass if orderTime has updated by correct amount in east difficulty",
                1 - dt / MainGame.EASY_DIFFICULTY, orderTimer.getOrderTime(), 0.0001);
        assertEquals("will pass if orderTime has updated by correct amount in east difficulty",
                1 - dt / MainGame.MEDIUM_DIFFICULTY, orderTimer.getOrderTime(), 0.0001);
    }

    @Test
    public void OrderDecrementMediumTimerTest(){
        OrderTimer orderTimer = new OrderTimer();
        orderTimer.setDifficulty(MainGame.MEDIUM_DIFFICULTY);
        float timeBefore = orderTimer.getOrderTime();
        float dt = 0.01f;
        orderTimer.update(dt);
        assertEquals("will pass if orderTime has updated by correct amount medium difficulty",
                1 - dt / MainGame.MEDIUM_DIFFICULTY, orderTimer.getOrderTime(), 0.0001);
        assertNotEquals("will pass if orderTime has updated by correct amt in medium difficulty",
                1 - dt / MainGame.EASY_DIFFICULTY, orderTimer.getOrderTime(), 0.0001);
    }

    @Test
    public void OrderDecrementHardTimerTest(){
        OrderTimer orderTimer = new OrderTimer();
        orderTimer.setDifficulty(MainGame.HARD_DIFFICULTY);
        float timeBefore = orderTimer.getOrderTime();
        float dt = 0.01f;
        orderTimer.update(dt);
        assertEquals("will pass if orderTime has updated by correct amount in hard difficulty",
                    1 - dt / MainGame.HARD_DIFFICULTY, orderTimer.getOrderTime(), 0.0001);
        assertNotEquals("will pass if orderTime has updated by correct amount hard difficulty",
                1 - dt / MainGame.EASY_DIFFICULTY, orderTimer.getOrderTime(), 0.0001);
    }
}
