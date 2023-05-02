package com.team13.piazzapanic;

import Tools.PlayScreenButton;
import Tools.PlayScreenButton.Functionality;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ButtonsTests {
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

   /* @Test
    public void testButtonClickable(){
        PlayScreen playscreen = new PlayScreen(new MainGame());
        PlayScreenButton button = new PlayScreenButton("Pan", Functionality.PAN, playscreen);
        final boolean[] click = {false};
        TextButton textbutton = button.getButton();
        textbutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                click[0] = true;
                System.out.println("CLICK!!!!");
            }
        });

        textbutton.getClickListener().clicked(new InputEvent(), 0, 0);

        assertTrue(click[0]);
    }*/

}
