package com.team13.piazzapanic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team13.piazzapanic.HUD;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
@RunWith(GdxTestRunner.class)
public class MoneyTest {
    public World world = new World(new Vector2(0, 0), true);
    public TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
    public TiledMap map = mapLoader.load("Kitchen.tmx");
    public MainGame game = new MainGame();


    @Test
    public void StartingMoneyTest(){
        SpriteBatch spriteBatch = new SpriteBatch();
        HUD hud = new HUD(spriteBatch);
        int money = hud.getScore();
        assertEquals("This test will only pass if the starting money is equal to 0",
                0, money);

}

}
