package com.team13.piazzapanic;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;
@RunWith(GdxTestRunner.class)
public class FoodAssetTests {
    @Test
    public void testBurgerAssetExists(){
        assertTrue("This test will only pass when Burger.png asset exists.", Gdx.files.internal("Food/Burger.png").exists());
    }
    @Test
    public void testBurgerBunsAssetExists(){
        assertTrue("This test will only pass when Burger_buns.png asset exists.", Gdx.files.internal("Food/Burger_buns.png").exists());
    }
    @Test
    public void testBurgerRecipeAssetExists(){
        assertTrue("This test will only pass when burger_recipe.png asset exists.", Gdx.files.internal("Food/burger_recipe.png").exists());//Maybe add to change log? (24/02/2023)
    }
    @Test
    public void testChoppedLettuceAssetExists(){
        assertTrue("This test will only pass when Chopped_lettuce.png asset exists", Gdx.files.internal("Food/Chopped_lettuce.png").exists());//Maybe add to change log? (24/02/2023)
    }
    @Test
    public void testChoppedOnionAssetExists(){
        assertTrue("This test will only pass when Chopped_onion.png asset exists", Gdx.files.internal("Food/Chopped_onion.png").exists());//Maybe add to change log? (24/02/2023)
    }
    @Test
    public void testChoppedTomatoAssetExists(){
        assertTrue("This test will only pass when Chopped_tomato.png asset exists", Gdx.files.internal("Food/Chopped_tomato.png").exists());//Maybe add to change log? (24/02/2023)
    }
    @Test
    public void testCookedPattyAssetExists(){
        assertTrue("This test will only pass when Cooked_patty.png asset exists", Gdx.files.internal("Food/Cooked_patty.png").exists());//Maybe add to change log? (24/02/2023)
    }
    @Test
    public void testLettuceAssetExists(){
        assertTrue("This test will only pass when Lettuce.png asset exists", Gdx.files.internal("Food/Lettuce.png").exists());//Maybe add to change log? (24/02/2023)
    }
    @Test
    public void testMeatAssetExists(){
        assertTrue("This test will only pass when Meat.png asset exists", Gdx.files.internal("Food/Meat.png").exists());//Maybe add to change log? (24/02/2023)
    }
    @Test
    public void testOnionAssetExists(){
        assertTrue("This test will only pass when Onion.png asset exists", Gdx.files.internal("Food/Onion.png").exists());//Maybe add to change log? (24/02/2023)
    }
    @Test
    public void testPattyAssetExists(){
        assertTrue("This test will only pass when Patty.png asset exists", Gdx.files.internal("Food/Patty.png").exists());//Maybe add to change log? (24/02/2023)
    }
    @Test
    public void testSaladAssetExists(){
        assertTrue("This test will only pass when Salad.png asset exists", Gdx.files.internal("Food/Salad.png").exists());//Maybe add to change log? (24/02/2023)
    }
    @Test
    public void testSaladRecipeExists(){
        assertTrue("This test will only pass when salad_recipe.png asset exists", Gdx.files.internal("Food/salad_recipe.png").exists());//Maybe add to change log? (24/02/2023)
    }
    @Test
    public void testToasterBurgerBunsAssetExists(){
        assertTrue("This test will only pass when Toasted_burger_buns.png asset exists", Gdx.files.internal("Food/Toasted_burger_buns.png").exists());//Maybe add to change log? (24/02/2023)
    }
    @Test
    public void testTomatoAssetExists(){
        assertTrue("This test will only pass when Tomato.png asset exists", Gdx.files.internal("Food/Tomato.png").exists());//Maybe add to change log? (24/02/2023)
    }
}

