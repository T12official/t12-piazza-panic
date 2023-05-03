package com.team13.piazzapanic;

import Sprites.OrderTimer;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class OrderTimerTest {

    @Test
    public void OrderDecrementEasyTimerTest() {
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
    public void OrderDecrementMediumTimerTest() {
        OrderTimer orderTimer = new OrderTimer();
        orderTimer.setDifficulty(MainGame.MEDIUM_DIFFICULTY);
        float timeBefore = orderTimer.getOrderTime();
        float dt = 0.01f;
        orderTimer.update(dt);
        assertEquals("will pass if orderTime has updated by correct amount medium difficulty",
                1 - dt / MainGame.MEDIUM_DIFFICULTY, orderTimer.getOrderTime(), 0.0001);
    }

    @Test
    public void OrderDecrementHardTimerTest() {
        OrderTimer orderTimer = new OrderTimer();
        orderTimer.setDifficulty(MainGame.HARD_DIFFICULTY);
        float timeBefore = orderTimer.getOrderTime();
        float dt = 0.01f;
        orderTimer.update(dt);
        assertEquals("will pass if orderTime has updated by correct amount in hard difficulty",
                1 - dt / MainGame.HARD_DIFFICULTY, orderTimer.getOrderTime(), 0.0001);
    }
}
