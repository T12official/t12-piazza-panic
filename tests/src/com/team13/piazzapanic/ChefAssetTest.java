package com.team13.piazzapanic;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;
@RunWith(GdxTestRunner.class)
public class ChefAssetTest {
    @Test
    public void testChefHoldingBunsAssetExists(){
        assertTrue("This test will only pass when Chef_holding_buns.png asset exists.", Gdx.files.internal("Chef/Chef_holding_buns.png").exists());
    }
    @Test
    public void testChefHoldingBunsToastedAssetExists(){
        assertTrue("This will only pass when Chef_holding_buns_toasted.png asset exists", Gdx.files.internal("Chef/Chef_holding_buns_toasted.png").exists());
    }
    @Test
    public void testChefHoldingBurgerAssetExists(){
        assertTrue("This will only pass when Chef_holding_burger.png asset exists", Gdx.files.internal("Chef/Chef_holding_burger.png").exists());
    }
    @Test
    public void testChefHoldingChoppedLettuceAssetExists(){
        assertTrue("This will only pass when Chef_holding_chopped_lettuce.png asset exists", Gdx.files.internal("Chef/Chef_holding_chopped_lettuce.png").exists());
    }
    @Test
    public void testChefHoldingChoppedOnionAssetExists(){
        assertTrue("This will only pass when Chef_holding_chopped_onion.png asset exists", Gdx.files.internal("Chef/Chef_holding_chopped_onion.png").exists());
    }
    @Test
    public void testChefHoldingChoppedTomatoAssetExists(){
        assertTrue("This will only pass when Chef_holding_chopped_tomato.png asset exists", Gdx.files.internal("Chef/Chef_holding_chopped_tomato.png").exists());
    }
    @Test
    public void testChefHoldingFrontAssetExists(){
        assertTrue("This will only pass when Chef_holding_front.png asset exists", Gdx.files.internal("Chef/Chef_holding_front.png").exists());
    }
    @Test
    public void testChefHoldingLettuceAssetExists(){
        assertTrue("This will only pass when Chef_holding_lettuce.png asset exists", Gdx.files.internal("Chef/Chef_holding_lettuce.png").exists());
    }
    @Test
    public void testChefHoldingMeatAssetExists(){
        assertTrue("This will only pass when Chef_holding_meat.png asset exists", Gdx.files.internal("Chef/Chef_holding_meat.png").exists());
    }
    @Test
    public void testChefHoldingOnionAssetExists(){
        assertTrue("This will only pass when Chef_holding_onion.png asset exists", Gdx.files.internal("Chef/Chef_holding_onion.png").exists());
    }
    @Test
    public void testChefHoldingPattyAssetExists(){
        assertTrue("This will only pass when Chef_holding_patty.png asset exists", Gdx.files.internal("Chef/Chef_holding_patty.png").exists());
    }
    @Test
    public void testChefHoldingSaladAssetExists(){
        assertTrue("This will only pass when Chef_holding_salad.png asset exists", Gdx.files.internal("Chef/Chef_holding_salad.png").exists());
    }
    @Test
    public void testChefHoldingTomatoAssetExists(){
        assertTrue("This will only pass when Chef_holding_tomato.png asset exists", Gdx.files.internal("Chef/Chef_holding_tomato.png").exists());
    }
    @Test
    public void testChefNormalAssetExists(){
        assertTrue("This will only pass when Chef_normal.png asset exists", Gdx.files.internal("Chef/Chef_normal.png").exists());
    }
    @Test
    public void testChefIdentifierAssetExists(){
        assertTrue("This will only pass when chefIdentifier.png asset exists", Gdx.files.internal("Chef/chefIdentifier.png").exists());
    }
}

