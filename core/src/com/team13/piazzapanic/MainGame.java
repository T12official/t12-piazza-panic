package com.team13.piazzapanic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game {

    /**
     * MainGame class is the central class of the game that creates and manages the two screens, PlayScreen and StartScreen.
     * <p>
     * Class Members:
     * V_WIDTH (int): Width of the view.
     * V_HEIGHT (int): Height of the view.
     * TILE_SIZE (int): Size of the tile.
     * PPM (float): Pixels per meter.
     * batch (SpriteBatch): Instance of SpriteBatch.
     * isPlayScreen (bool): Flag indicating whether the PlayScreen is displayed or not.
     * playScreen (PlayScreen): Instance of PlayScreen.
     * startScreen (StartScreen): Instance of StartScreen.
     * <p>
     * Methods:
     * __init__: Initializes the MainGame class.
     * create: Creates the instances of StartScreen and PlayScreen and initializes the SpriteBatch instance.
     * render: Renders the StartScreen or PlayScreen based on the value of isPlayScreen flag.
     * dispose: Releases resources used by the MainGame class.
     */
    public static final int V_WIDTH = 160;
    public static final int V_HEIGHT = 160;
    public static final int TILE_SIZE = 16;

    public static final float PPM = 100;
    public static final double EASY_DIFFICULTY = 150d;
    public static final double MEDIUM_DIFFICULTY = 100d;
    public static final double HARD_DIFFICULTY = 75d;
    public SpriteBatch batch;
    public boolean isPlayScreen;
    public PlayScreen playScreen;
    public StartScreen startScreen;
    public boolean isStartScreen = true;
    public boolean isEndless = false;
    private IdleScreen idleScreen;
    private GameOverScreen gameoverScreen;
    private EndlessScreen endlessScreen;
    private boolean isGameOver = false;
    private boolean isIdleScreen = false;
    public MainGame() {
        isPlayScreen = false;
    }

    public SpriteBatch getBatch() {
        if (batch == null) {
            return new SpriteBatch();
        } else {
            return batch;
        }
    }

    @Override
    public void create() {
        batch = getBatch();
        startScreen = new StartScreen(this);
        playScreen = new PlayScreen(this);

        gameoverScreen = new GameOverScreen(this);
        idleScreen = new IdleScreen(this);
        endlessScreen = new EndlessScreen(this);

    }

    @Override
    public void render() {
        super.render();
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            isStartScreen = !isStartScreen;
        }
        Screen currentScreen = getScreen();
        setScreen(currentScreen);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

    public void goToGameOver() {
        this.isGameOver = true;
    }

    public void goToIdle() {
        isPlayScreen = false;
        isEndless = false;
        isIdleScreen = true;

    }

    public void disableIdle() {
        isIdleScreen = false;
        playScreen.resetIdleTimer();
    }

    public void setGameScreen() {
        setScreen(getScreen());
    }

    @Override
    public Screen getScreen() {
        if (isStartScreen) {
            return startScreen;
        } else if (isEndless) {
            endlessScreen.setDifficultyScore(startScreen.diff);
            return endlessScreen;
        } else if (isGameOver) {
            return gameoverScreen;
        } else if (isIdleScreen) {
            return idleScreen;
        }
        playScreen.setDifficultyScore(startScreen.diff);
        return playScreen;
    }
}
