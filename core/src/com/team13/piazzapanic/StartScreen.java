package com.team13.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.*;
import Sprites.orderBar;


/**
 * This class implements the `Screen` interface and represents the start screen of the game.
 */
public class StartScreen implements Screen {
    private final MainGame game;
    private final Texture backgroundImage;
    private final Sprite backgroundSprite;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private Stage stage;
    private Slider slider;
    private final TextButton button2;
    private final TextButton MEDIUM;
    private  final TextButton HARD;
    private final TextButton loadSave;
    public Double diff = 5.0;

    /**
     * Constructor for StartScreen.
     *
     * @param game the game object.
     */
    public StartScreen(MainGame game) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        button2 = getButton("easy");
        MEDIUM = getButton("medium");
        HARD = getButton("hard");
        loadSave = getButton("Load");
        MEDIUM.setPosition(50, 0);
        HARD.setPosition(130, 0);
        loadSave.setPosition(0,30);
        stage.addActor(button2);
        stage.addActor(MEDIUM);
        stage.addActor(HARD);
        stage.addActor(loadSave);
        this.game = game;
        backgroundImage = new Texture("startImage.png");
        backgroundSprite = new Sprite(backgroundImage);

    }

    private TextButton getButton(final String message) {
        Skin skin = new Skin();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        skin.add("default", new BitmapFont());
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        TextButton button = new TextButton(message, skin);

        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {

                System.out.println("Clicked! Is checked: ");
                if (message == "easy"){
                    diff = 0.025;

                }
                if (message == "medium"){
                    diff = 0.05;

                }
                if (message == "hard"){
                    diff = 0.07;
                }
                if (message == "Load"){
                    game.playScreen.onStartLoadGame();
                    game.playScreen.idleGametimer = TimeUtils.millis();
                    game.setGameScreen();
                }
                //game.goToGameOver();
            }
        });
        return button;


    }

    /**
     * Method called when the screen is shown.
     * Initializes the sprite and camera position.
     */
    @Override
    public void show() {
        backgroundSprite.setSize(MainGame.V_WIDTH, MainGame.V_HEIGHT);
        backgroundSprite.setPosition(0, 0);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    /**
     * Method to render the screen.
     * Clears the screen and draws the background sprite.
     *
     * @param delta the time in seconds since the last frame.
     */
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        backgroundSprite.draw(game.batch);
        //button2.draw(game.batch,1f);

        //MEDIUM.draw(game.batch,1f);
        //HARD.draw(game.batch,1f);
        //orderBar a  = new  orderBar(30,30,50,5, Color.RED);
        //a.draw(game.batch, 1);
        game.batch.end();
        stage.draw();
    }

    /**
     * Method called when the screen is resized.
     * Updates the viewport and camera position.
     *
     * @param width the new screen width.
     * @param height the new screen height.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    /**
     * Dispose method that is called when the screen is no longer used.
     * It is used to free up resources and memory that the screen was using.
     */
    @Override
    public void dispose() {
        backgroundImage.dispose();
    }

    public void reActivateInput(){
        Gdx.input.setInputProcessor(stage);
    }
}
