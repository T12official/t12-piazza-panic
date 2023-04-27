package com.team13.piazzapanic;

import Ingredients.*;
import Sprites.*;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class StationTests {
    public World world = new World(new Vector2(0, 0), true);
    public TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
    public TiledMap map = mapLoader.load("Kitchen.tmx");
    Bun bun = new Bun(0, 3, 8);
    Lettuce lettuce = new Lettuce(2, 0, 0);
    Onion onion = new Onion(2,0, 0);
    Steak steak = new Steak(2,3, 8);
    Tomato tomato = new Tomato(2,0, 0);

    @Test
    public void checkBunsStationHasIngredient() {
        BunsStation bunsStation = new BunsStation(world, map, new BodyDef(), new Rectangle());
        assertEquals("Will be work if BunStation has Bun ingredient",
                bunsStation.getIngredient(), bun);
    }
    @Test
    public void checkLettuceStationHasIngredient() {
        LettuceStation tile = new LettuceStation(world, map, new BodyDef(), new Rectangle());
        assertEquals("Will be work if LettuceStation has Lettuce ingredient",
                tile.getIngredient(), lettuce);
    }

    @Test
    public  void checkOnionStationHasIngredient(){
        OnionStation tile = new OnionStation(world, map, new BodyDef(), new Rectangle());
        assertEquals("Will be work if OnionStation has Onion ingredient",
                tile.getIngredient(), onion);
    }

    @Test
    public void checkSteakStationHasIngredient(){
        SteakStation tile = new SteakStation(world, map, new BodyDef(), new Rectangle());
        assertEquals("Will be work if SteakStation has Steak ingredient",
                tile.getIngredient(), steak);
    }

    @Test
    public void checkTomatoStationHasIngredient(){
        TomatoStation tile = new TomatoStation(world, map, new BodyDef(), new Rectangle());
        assertEquals("Will be work if TomatoStation has Tomato ingredient",
                tile.getIngredient(), tomato);
    }
}