package com.team13.piazzapanic;

import com.badlogic.gdx.Screen;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class GameModeTests {
    MainGame game = new MainGame();
    @Test
    public void StartScreenTest(){
        Screen s = game.getScreen();
        assertEquals(s, game.startScreen);
    }

}
