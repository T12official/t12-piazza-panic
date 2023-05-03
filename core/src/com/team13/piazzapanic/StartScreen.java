package com.team13.piazzapanic;

import Tools.PlayScreenButton;
import Tools.StartScreenButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * This class implements the `Screen` interface and represents the start screen of the game.
 */
public class StartScreen implements Screen {
    private final MainGame game;
    private final Texture backgroundImage;
    private final Sprite backgroundSprite;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final StartScreenButton EASY;
    private final StartScreenButton MEDIUM;
    private final StartScreenButton HARD;
    private final StartScreenButton loadSave;
    private final StartScreenButton gamemode1;
    private final StartScreenButton gamemode2;
    public double diff = MainGame.EASY_DIFFICULTY;
    private Slider slider;

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
        EASY = new StartScreenButton("Easy", PlayScreenButton.Functionality.EASY, this);
        MEDIUM = new StartScreenButton("Normal", PlayScreenButton.Functionality.NORMAL, this);
        HARD = new StartScreenButton("Hard", PlayScreenButton.Functionality.HARD, this);
        loadSave = new StartScreenButton("Load", PlayScreenButton.Functionality.LOAD, this);
        gamemode1 = new StartScreenButton("Scenario", PlayScreenButton.Functionality.SCENARIO, this);
        gamemode2 = new StartScreenButton("Endless", PlayScreenButton.Functionality.ENDLESS, this);
        MEDIUM.setPosition(50, 0);
        HARD.setPosition(130, 0);
        loadSave.setPosition(0, 30);
        gamemode1.setPosition(40, 30);
        gamemode2.setPosition(110, 30);
        stage.addActor(EASY.getButton());
        stage.addActor(MEDIUM.getButton());
        stage.addActor(HARD.getButton());
        stage.addActor(loadSave.getButton());
        stage.addActor(gamemode1.getButton());
        stage.addActor(gamemode2.getButton());
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
            public void clicked(InputEvent event, float x, float y) {
                if (message == "easy") {
                    diff = MainGame.EASY_DIFFICULTY;

                }
                if (message == "medium") {
                    diff = MainGame.MEDIUM_DIFFICULTY;

                }
                if (message == "hard") {
                    diff = MainGame.HARD_DIFFICULTY;
                }
                if (message == "Load") {
                    game.playScreen.onStartLoadGame();
                    game.playScreen.idleGameTimer = TimeUtils.millis();
                    game.isPlayScreen = true;
                }
                if (message == "Scenario") {
                    game.isPlayScreen = true;
                }
                if (message == "Endless") {
                    game.isPlayScreen = false;
                    game.isEndless = true;
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

        GameOverScreen.configBatch(camera, game, backgroundSprite);
        stage.draw();
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Method called when the screen is resized.
     * Updates the viewport and camera position.
     *
     * @param width  the new screen width.
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

    public void reActivateInput() {
        Gdx.input.setInputProcessor(stage);
    }

    public void setDiff(double diff) {
        this.diff = diff;
    }

    public void setPlayScreen() {
        game.isEndless = false;
        game.isPlayScreen = true;
    }

    public void setEndlessMode() {
        game.isPlayScreen = false;
        game.isEndless = true;
    }

    public void loadGame() {
        game.playScreen.onStartLoadGame();
        game.playScreen.idleGameTimer = TimeUtils.millis();
        game.isPlayScreen = true;
    }
}
