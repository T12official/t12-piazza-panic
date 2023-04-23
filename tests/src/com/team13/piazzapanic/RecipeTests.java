package com.team13.piazzapanic;

import Ingredients.*;
import Recipe.*;
import Sprites.Chef;
import Sprites.CompletedDishStation;
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
public class RecipeTests {
    public World world = new World(new Vector2(0, 0), true);
    public TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
    public TiledMap map = mapLoader.load("Kitchen.tmx");
    public MainGame game = new MainGame();

    @Test
    public void MakeSaladRecipeTest(){
        PlateStation plate = new PlateStation(world, map, new BodyDef(), new Rectangle());
        Onion onion = new Onion(0, 0,0);
        Tomato tomato = new Tomato(0, 0,0);
        Lettuce lettuce = new Lettuce(0, 0,0);

        onion.setPrepared();
        tomato.setPrepared();
        lettuce.setPrepared();

        plate.dropItem(onion);
        plate.dropItem(tomato);
        plate.dropItem(lettuce);

        assertEquals("This will only pass if salad contains correct ingredients",
                    new SaladRecipe(), plate.getCompletedRecipe());
    }

    @Test
    public void IncorrectSaladRecipeTest(){
        PlateStation plate = new PlateStation(world, map, new BodyDef(), new Rectangle());
        Onion onion = new Onion(0, 0,0);
        Tomato tomato = new Tomato(0, 0,0);
        Lettuce lettuce = new Lettuce(0, 0,0);

        plate.dropItem(onion);
        plate.dropItem(tomato);
        plate.dropItem(lettuce);

        assertNull("This will only pass if salad contains correct ingredients",
                plate.getCompletedRecipe());
    }

    @Test
    public void MakeBurgerRecipeTest(){
        PlateStation plate = new PlateStation(world, map, new BodyDef(), new Rectangle());
        Steak steak = new Steak(0, 0, 0);
        Bun bun = new Bun(0, 0, 0);
        steak.setPrepared();
        steak.setCooked();
        bun.setCooked();

        plate.dropItem(steak);
        plate.dropItem(bun);

        assertEquals("This will only pass if burger contains correct ingredients",
                new BurgerRecipe(), plate.getCompletedRecipe());
    }
    @Test
    public void IncorrectBurgerRecipeTest(){
        PlateStation plate = new PlateStation(world, map, new BodyDef(), new Rectangle());
        Steak steak = new Steak(0, 0, 0);
        Bun bun = new Bun(0, 0, 0);

        plate.dropItem(steak);
        plate.dropItem(bun);

        assertNull("This will only pass if burger contains incorrect ingredients",
               plate.getCompletedRecipe());
    }
    @Test
    public void MakeJacketPotatoTest(){
        PlateStation plate = new PlateStation(world, map, new BodyDef(), new Rectangle());
        Potatoes potatoes = new Potatoes(0, 0, 0);
        Cheese cheese = new Cheese(0,0,0);

        potatoes.setPrepared();
        cheese.setPrepared();

        plate.dropItem(potatoes);
        plate.dropItem(cheese);

        assertEquals("This will only pass if burger contains correct ingredients",
                new jacketPotato(), plate.getCompletedRecipe());

    }

    @Test
    public void IncorrectJacketPotatoTest(){
        PlateStation plate = new PlateStation(world, map, new BodyDef(), new Rectangle());
        Potatoes potatoes = new Potatoes(0, 0, 0);
        Cheese cheese = new Cheese(0,0,0);
        Lettuce lettuce = new Lettuce(0, 0,0);

        plate.dropItem(potatoes);
        plate.dropItem(lettuce);

        assertNull("This will only pass if jacket potato contains incorrect ingredients",
                plate.getCompletedRecipe());
    }

    @Test
    public void MakePizzaTest(){
        PlateStation plate = new PlateStation(world, map, new BodyDef(), new Rectangle());
        pizzaDough pd = new pizzaDough(0,0,0);
        Cheese cheese = new Cheese(0,0,0);
        Tomato tomato = new Tomato(0,0,0);

        pd.setPrepared();
        cheese.setPrepared();
        tomato.setPrepared();

        plate.dropItem(pd);
        plate.dropItem(tomato);
        plate.dropItem(cheese);

        Recipe r = plate.getCompletedRecipe();
        System.out.println(r);
        //assertEquals(new pizzaRecipy(), plate.getCompletedRecipe());
    }
}
