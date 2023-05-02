package com.team13.piazzapanic;


import Sprites.Chef;
import com.badlogic.gdx.*;
import com.team13.piazzapanic.PlayScreen;
import com.team13.piazzapanic.MainGame;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import jdk.internal.foreign.PlatformLayouts;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)

public class ChefTest extends ApplicationAdapter implements InputProcessor {

    public float runSpeedModifier = 1F;
    private float yVelocity = 0;
    private float xVelocity = 0;
    public World world = new World(new Vector2(0, 0), true);
    public TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
    public TiledMap map = mapLoader.load("Kitchen.tmx");
    public MainGame game = new MainGame();
    public boolean active = false;
    public ArrayList<Chef> chefList;
    public int currentChef = 0;
    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();

    public Chef getChef1() {
        return chefList.get(0);
    }

    public Chef getChef2() {
        return chefList.get(1);
    }

    public Chef getChef3() {
        return chefList.get(2);
    }
    private Chef chef1;
    private Chef chef2;
    private Chef chef3;
    private Chef controlledChef;


    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.R:
                rest();
                break;
            case Input.Keys.D:
                xVelocity += 0.5f * runSpeedModifier;
                break;
            case Input.Keys.A:
                xVelocity -= 0.5f * runSpeedModifier;
                break;
            case Input.Keys.W:
                yVelocity += 0.5f * runSpeedModifier;
                break;
            case Input.Keys.S:
                yVelocity -= 0.5f * runSpeedModifier;
                break;
        }
        return true;
    }
    public Chef getChef(){
        return chefList.get(currentChef%chefList.size());
    }

    public Chef switchChef(){
        getChef().rest();
        currentChef += 1;
        getChef().active = true;
        InputProcessor[] cars = {getChef()};
        inputMultiplexer.setProcessors(cars);
        return getChef();
    }

    private void rest() {
        active = false;
        xVelocity = 0;
        yVelocity = 0;
    }


    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.D:
            case Input.Keys.A:
                yVelocity = 0;
                break;
            case Input.Keys.W:
            case Input.Keys.S:
                xVelocity = 0;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


    @Test
    public void MoveChefRightTest() {
        MainGame maingame = new MainGame();
        PlayScreen playscreen = new PlayScreen(maingame);
        Chef chef = new Chef(playscreen, 0, 0);

        Gdx.input.setInputProcessor(chef);
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.keyDown);
        event.setKeyCode(Input.Keys.D);
        chef.keyDown(Input.Keys.D);

        assertEquals("This test will only pass if, after pressing 'D', the chef moves to the right",
                0.5f, chef.getxVelocity(), 0.001f);


    }

    @Test
    public void MoveChefLeftTest() {

        MainGame maingame = new MainGame();
        PlayScreen playscreen = new PlayScreen(maingame);
        Chef chef = new Chef(playscreen, 0, 0);

        Gdx.input.setInputProcessor(chef);
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.keyDown);
        event.setKeyCode(Input.Keys.A);
        chef.keyDown(Input.Keys.A);

        assertEquals("This test will only pass if, after pressing 'A', the chef moves to the right",
                -0.5f, chef.getxVelocity(), 0.001f);
    }

    @Test
    public void MoveChefUpTest() {

        MainGame maingame = new MainGame();
        PlayScreen playscreen = new PlayScreen(maingame);
        Chef chef = new Chef(playscreen, 0, 0);

        Gdx.input.setInputProcessor(chef);
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.keyDown);
        event.setKeyCode(Input.Keys.W);
        chef.keyDown(Input.Keys.W);

        assertEquals("This test will only pass if, after pressing 'W', the chef moves up",
                0.5f, chef.getyVelocity(), 0.001f);
    }

    @Test
    public void MoveChefDownTest() {

        MainGame maingame = new MainGame();
        PlayScreen playscreen = new PlayScreen(maingame);
        Chef chef = new Chef(playscreen, 0, 0);

        Gdx.input.setInputProcessor(chef);
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.keyDown);
        event.setKeyCode(Input.Keys.S);
        chef.keyDown(Input.Keys.S);


        assertEquals("This test will only pass if, after pressing 'S', the chef moves down",
                -0.5f, chef.getyVelocity(), 0.001f);
    }

    @Test
    public void SwitchChefTest(){

        MainGame maingame = new MainGame();
        PlayScreen playscreen = new PlayScreen(maingame);
        Chef chef = new Chef(playscreen, 0, 0);

        chefList = new ArrayList<>();
        chefList.add(new Chef(playscreen, 0, 0));
        chefList.add(new Chef(playscreen, 50, 50));
        chefList.add(new Chef(playscreen, 100, 100));
        getChef().active = true;
        chef1 = getChef1();
        chef2 = getChef2();
        chef3 = getChef3();
        controlledChef = getChef();

        Gdx.input.setInputProcessor(controlledChef);
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.keyDown);
        event.setKeyCode(Input.Keys.R);
        controlledChef.keyDown(Input.Keys.R);

        assertEquals("This test will only pass if chef is switched successfully after pressing 'R'",
                1, currentChef);

    }
}

