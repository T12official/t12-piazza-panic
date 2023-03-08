package com.team13.piazzapanic;

import Ingredients.*;
import Recipe.BurgerRecipe;
import Recipe.SaladRecipe;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
@RunWith(GdxTestRunner.class)
public class RecipeTests {
    public World world = new World(new Vector2(0, 0), true);
    public TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
    public TiledMap map = mapLoader.load("Kitchen.tmx");

    @Test
    public void MakeSaladRecipeTest(){
        PlateStation plate = new PlateStation(world, map, new BodyDef(), new Rectangle());
        CompletedDishStation completedDishStation = new CompletedDishStation(world, map, new BodyDef(), new Rectangle());
        Chef chef = new Chef(world, 0, 0);
        Onion onion = new Onion(0, 0);
        Tomato tomato = new Tomato(0, 0);
        Lettuce lettuce = new Lettuce(0, 0);

        onion.setPrepared();
        tomato.setPrepared();
        lettuce.setPrepared();

        plate.dropItem(onion);
        plate.dropItem(tomato);
        plate.dropItem(lettuce);

        chef.setInHandsRecipe(plate.getCompletedRecipe());


        assertEquals("This will only pass if salad contains correct ingredients",
                    new SaladRecipe(), chef.getInHandsRecipe());
    }

    @Test
    public void MakeBurgerRecipeTest(){
        PlateStation plate = new PlateStation(world, map, new BodyDef(), new Rectangle());
        CompletedDishStation completedDishStation = new CompletedDishStation(world, map, new BodyDef(), new Rectangle());
        Chef chef = new Chef(world, 0, 0);
        Steak steak = new Steak(0, 0);
        Bun bun = new Bun(0, 0);
        steak.setPrepared();
        steak.setCooked();
        bun.setCooked();

        plate.dropItem(steak);
        plate.dropItem(bun);

        chef.setInHandsRecipe(plate.getCompletedRecipe());


        assertEquals("This will only pass if salad contains correct ingredients",
                new BurgerRecipe(), chef.getInHandsRecipe());
    }
}
