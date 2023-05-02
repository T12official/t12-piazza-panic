package com.team13.piazzapanic;

import Tools.PlayScreenButton;
import Tools.StartScreenButton;
import Tools.PlayScreenButton.Functionality;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertNotNull;

@RunWith(GdxTestRunner.class)
public class PlayScreenButtonTests {
    @Test
    public void testShopButtonExists(){
        PlayScreen playscreen = new PlayScreen(new MainGame());
        PlayScreenButton button = new PlayScreenButton("Shop", Functionality.SHOP, playscreen);
        assertNotNull(button.getButton());
    }
    @Test
    public void testChopButtonExists(){
        PlayScreen playscreen = new PlayScreen(new MainGame());
        PlayScreenButton button = new PlayScreenButton("Chop", Functionality.CHOP, playscreen);
        assertNotNull(button.getButton());
    }
    @Test
    public void testPanButtonExists() {
        PlayScreen playscreen = new PlayScreen(new MainGame());
        PlayScreenButton button = new PlayScreenButton("Pan", Functionality.PAN, playscreen);
        assertNotNull(button.getButton());
    }

    @Test
    public void testSaveButtonExists() {
        PlayScreen playscreen = new PlayScreen(new MainGame());
        PlayScreenButton button = new PlayScreenButton("Pan", Functionality.SAVEGAME, playscreen);
        assertNotNull(button.getButton());
    }


}
