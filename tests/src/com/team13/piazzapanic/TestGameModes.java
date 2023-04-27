package com.team13.piazzapanic;

import Tools.PlayScreenButton;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(GdxTestRunner.class)

public class TestGameModes {
    MainGame game = new MainGame();
    PlayScreen playScreen = new PlayScreen(game);

    @Test
    public void ScenarioModeTest(){
        game.isPlayScreen = true;
        System.out.println(game.getScreen().getClass());
    }
}
