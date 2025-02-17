package com.team13.piazzapanic;

import Ingredients.Ingredient;
import Recipe.Order;
import Recipe.Recipe;
import Sprites.Chef;
import Sprites.InteractiveTileObject;
import Sprites.OrderTimer;
import Sprites.PlateStation;
import Tools.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import powerUps.cookingSpeedBoost;
import powerUps.powerUpRandom;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The PlayScreen class is responsible for displaying the game to the user and handling the user's interactions.
 * The PlayScreen class implements the Screen interface which is part of the LibGDX framework.
 * <p>
 * The PlayScreen class contains several important fields, including the game instance, stage instance, viewport instance,
 * and several other helper classes and variables. The game instance is used to access the global game configuration and
 * to switch between screens. The stage instance is used to display the graphics and handle user interactions, while the
 * viewport instance is used to manage the scaling and resizing of the game window.
 * <p>
 * The PlayScreen class also contains several methods for initializing and updating the game state, including the
 * constructor, show(), render(), update(), and dispose() methods. The constructor sets up the stage, viewport, and
 * other helper classes and variables. The show() method is called when the PlayScreen becomes the active screen. The
 * render() method is called repeatedly to render the game graphics and update the game state. The update() method is
 * called to update the game state and handle user inputs. The dispose() method is called when the PlayScreen is no longer
 * needed and is used to clean up resources and prevent memory leaks.
 */

public class PlayScreen implements Playable {

    public static float trayX;
    public static float trayY;
    public final MainGame game;
    private final OrthographicCamera gameCam;
    private final Viewport gamePort;
    private final TextButton button;
    private final TextButton button2;
    private final TextButton buttonPans;
    private final TextButton saveGame;
    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private final World world;
    private final Chef chef1;
    private final Chef chef2;
    private final Chef chef3;
    private final kitchenChangerAPI kitchenEdit;
    private final PlayScreen selfRef = this;
    public double difficultyScore;
    public HUD hud;
    public OrderTimer orderTimer = new OrderTimer();
    public boolean isActiveOrder = false;
    public boolean dispose = false;
    public ArrayList<Chef> chefList;
    public int currentChef = 1;
    public long idleGameTimer;
    public cookingSpeedBoost toKill;
    public ArrayList<Order> ordersArray;
    public PlateStation plateStation;
    public Boolean scenarioComplete;
    public Boolean createdOrder;
    public ArrayList<cookingSpeedBoost> powerUpArray;
    public cookingSpeedBoost powerUp;
    public int numberOfOrders = 5;
    private Stage stage;
    private boolean loadMyGame = false;
    private Label messageLabel;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Chef controlledChef;
    private float timeSeconds = 0f;
    private long shopMessageTimer = 0;
    private boolean messageUp = false;
    private long spawnNewPowerUpTimer;
    private float timeSecondsCount = 0f;
    private boolean activateShop = false;
    private int addictionPanCount = 0;
    private int additionChopCount = 0;
    private Integer orderNum = 1;
    /**
     * PlayScreen constructor initializes the game instance, sets initial conditions for scenarioComplete and createdOrder,
     * creates and initializes game camera and viewport,
     * creates and initializes HUD and orders hud, loads and initializes the map,
     * creates and initializes world, creates and initializes chefs and sets them, sets contact listener for world, and initializes ordersArray.
     *
     * @param game The MainGame instance that the PlayScreen will be a part of.
     */

    public PlayScreen(MainGame game) {
        spawnNewPowerUpTimer = TimeUtils.millis();
        kitchenEdit = new kitchenChangerAPI();
        chefList = new ArrayList<>();
        powerUpArray = new ArrayList<>(); //The powerUpArray is an array containing all the power-ups that need to be rendered. new powe-rups can be added to this array
        kitchenEdit.readFile();
        resetIdleTimer();
        this.game = game;
        new GameOverScreen(game);

        scenarioComplete = false;
        createdOrder = false;
        gameCam = new OrthographicCamera();
        // FitViewport to maintain aspect ratio whilst scaling to screen size
        gamePort = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gameCam);

        // create map
        TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        map = mapLoader.load("Kitchen.tmx");

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        Gdx.input.setInputProcessor(stage);
        PlayScreenButton shopButton = new PlayScreenButton("shop", PlayScreenButton.Functionality.SHOP, this);
        PlayScreenButton chopButton = new PlayScreenButton("chop", PlayScreenButton.Functionality.CHOP, this);
        PlayScreenButton panButton = new PlayScreenButton("pan", PlayScreenButton.Functionality.PAN, this);
        PlayScreenButton saveButton = new PlayScreenButton("save", PlayScreenButton.Functionality.PAN, this);
        button = shopButton.getButton();
        button2 = chopButton.getButton();
        buttonPans = panButton.getButton();
        saveGame = saveButton.getButton();

        world = new World(new Vector2(0, 0), true);
        new B2WorldCreator(world, map, this);
        powerUp = new cookingSpeedBoost(this.world, new TextureRegion(new Texture("powerUps/powerUpCoin.png")), 126, 85);
        powerUp.setPowerUp(new powerUpRandom());
        chefList.add(new Chef(this, 31.5F, 65));
        chefList.add(new Chef(this, 128, 65));
        chefList.add(new Chef(this, 128, 88));
        getChef().active = true;
        chef1 = getChef1();
        chef2 = getChef2();
        chef3 = getChef3();
        System.out.println(chefList.toString());

        controlledChef = getChef();
        controlledChef.notificationSetBounds("Down");
        ordersArray = new ArrayList<>();
        orderTimer.setDifficulty(difficultyScore);
    }

    public HUD getHUD() {
        if (hud == null) {
            return new HUD(game.getBatch());
        }
        return hud;
    }

    public void onStartLoadGame() {
        loadMyGame = true;
    }

    public void resetIdleTimer() {
        idleGameTimer = TimeUtils.millis();
    }

    @Override
    public PlateStation getPlateStation() {
        return plateStation;
    }

    public Chef getChef() {
        return chefList.get(currentChef % chefList.size());
    }

    public World getWorld() {
        return world;
    }

    public void switchChef() {
        getChef().rest();
        currentChef += 1;
        getChef().active = true;
        if (getChef().isCooking) {
            getChef().isCooking = false;
            getChef().setChefSkin(getChef().getInHandsIng());
        }

        InputProcessor[] cars = {getChef()};
        inputMultiplexer.setProcessors(cars);
        getChef();
    }

    @Override
    public void show() {
        hud = getHUD();
        stage = new Stage(gamePort, game.getBatch());
        InputProcessor[] cars = {getChef(), hud.stage};
        inputMultiplexer.setProcessors(cars);
        // inputMultiplexer.getProcessors();
        Gdx.input.setInputProcessor(inputMultiplexer);
        new Orders(game.getBatch());
        hud.stage.addActor(orderTimer);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        world.setContactListener(new WorldContactListener(world, this, orderTimer, hud));
        addToHud("welcome");
        messageLabel.remove();
    }


    /**
     * The handleInput method is responsible for handling the input events of the game such as movement and interaction with objects.
     * <p>
     * It checks if the 'R' key is just pressed and both chefs have the user control, if so,
     * it switches the control between the two chefs.
     * <p>
     * If the controlled chef does not have the user control,
     * the method checks if chef1 or chef2 have the user control and sets the control to that chef.
     * <p>
     * If the controlled chef has the user control,
     * it checks if the 'W', 'A', 'S', or 'D' keys are pressed and sets the velocity of the chef accordingly.
     * <p>
     * If the 'E' key is just pressed and the chef is touching a tile,
     * it checks the type of tile and sets the chef's in-hands ingredient accordingly.
     * <p>
     * The method also sets the direction of the chef based on its linear velocity.
     *
     * @param dt is the time delta between the current and previous frame.
     */

    public void handleInput(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            gameSaveTool.loadMyGame(this);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            gameSaveTool.saveMyGame(this);
        }
        controlledChef = getChef();
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            resetIdleTimer();
            if (controlledChef.getTouchingTile() != null) {
                InteractiveTileObject tile = (InteractiveTileObject) controlledChef.getTouchingTile().getUserData();
                String tileName = tile.getClass().getName();
                System.out.println(tileName + " apples aaa");
                if (controlledChef.getInHandsIng() == null && controlledChef.getInHandsRecipe() == null) {
                    controlledChef.handleSprite(tile, tileName);
                } else {
                    switch (tileName) {
                        case "Sprites.Bin":
                            System.out.println("giving myself a spud");
                            controlledChef.setInHandsRecipe(null);
                            controlledChef.setInHandsIng(null);
                            controlledChef.setChefSkin(null);
                            break;

                        case "Sprites.ChoppingBoard":
                            if (controlledChef.getInHandsIng() != null) {
                                if (controlledChef.getInHandsIng().prepareTime > 0) {
                                    controlledChef.setUserControlChef(false);
                                }
                            }
                            break;
                        case "Sprites.PlateStation":
                            if (controlledChef.getInHandsRecipe() == null) {
                                controlledChef.dropItemOn(tile, controlledChef.getInHandsIng());
                                controlledChef.setChefSkin(null);
                            }
                            break;
                        case "Sprites.Pan":
                            if (controlledChef.getInHandsIng() != null) {
                                if (controlledChef.getInHandsIng().isPrepared() && controlledChef.getInHandsIng().cookTime > 0) {
                                    controlledChef.setUserControlChef(false);
                                    controlledChef.isCooking = true;
                                }
                            }

                            break;
                        case "Sprites.CompletedDishStation":
                            if (controlledChef.getInHandsRecipe() != null) {
                                if (controlledChef.getInHandsRecipe() == ordersArray.get(0).recipe) {
                                    //TODO UPDATE CHANGE LOG FOR THIS
                                    if (orderTimer.getOrderTime() == 0) {
                                        hud.decrementReps();
                                        if (hud.getRepPoints() == 0) {
                                            System.out.println("game over");
                                            game.goToGameOver();
                                        }
                                    }
                                    controlledChef.dropItemOn(tile);
                                    ordersArray.get(0).orderComplete = true;
                                    controlledChef.setChefSkin(null);
                                    if (ordersArray.size() == 1) {
                                        scenarioComplete = true;
                                        //game.goToGameOver();
                                    }
                                }
                            }
                            break;
                    }
                }
            }

        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            resetIdleTimer();
        }

    }

    public void destroyPowerup(World world, cookingSpeedBoost toKill) {
        world.destroyBody(toKill.getBody());
        toKill.dispose();
    }

    /**
     * The update method updates the game elements, such as camera and characters,
     * based on a specified time interval "dt".
     *
     * @param dt time interval for the update
     */
    public void update(float dt) {

        if (loadMyGame) {
            gameSaveTool.loadMyGame(this);
            reRender("apple");
            //TODO add kitchenload
            loadMyGame = false;
        }

        if (dispose) {
            destroyPowerup(world, toKill);
            world.destroyBody(powerUp.getBody());
            powerUp.getTexture().dispose();
            //powerUp.setTexture(null);
            powerUp.dispose();
            dispose = false;
        }

        handleInput(dt);

        gameCam.update();
        renderer.setView(gameCam);
        for (Chef chef : chefList) {
            chef.update(dt);
        }
        powerUp.update(dt);
        world.step(1 / 60f, 6, 2);
        orderTimer.update(dt);

    }

    /**
     * Creates the orders randomly and adds to an array, updates the HUD.
     */
    public void createOrder() {
        System.out.println("I am an oeder");
        isActiveOrder = true;
        orderTimer.setOrderTime(1);

        int randomNum = ThreadLocalRandom.current().nextInt(1, 5);
        Texture burger_recipe = new Texture("Food/burger_recipe.png");
        Texture salad_recipe = new Texture("Food/salad_recipe.png");
        Texture jacket_recipe = new Texture("Food/jacketPotato.png");
        Texture pizza_recipe = new Texture("Food/pizza_recipe.png");
        Order order;

        for (int i = 0; i < numberOfOrders; i++) {
            if (randomNum == 1) {
                order = new Order(PlateStation.burgerRecipe, burger_recipe);
            } else if (randomNum == 2) {
                order = new Order(PlateStation.saladRecipe, salad_recipe);
            } else if (randomNum == 3) {
                order = new Order(PlateStation.mypizzaRecipy, pizza_recipe);
            } else {
                order = new Order(PlateStation.jacketPotatoRec, jacket_recipe);
            }
            ordersArray.add(order);
            randomNum = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        }
        hud.updateOrder(false, orderNum);
    }

    /**
     * Updates the orders as they are completed, or if the game scenario has been completed.
     */
    public void updateOrder() {
        if (scenarioComplete) {
            hud.updateScore(true, (numberOfOrders + 1 - ordersArray.size()) * 35, orderTimer.getOrderTime());
            hud.updateOrder(true, orderNum);
            return;
        }
        if (ordersArray.size() != 0) {
            if (ordersArray.get(0).orderComplete) {
                orderNum++;
                isActiveOrder = true;
                orderTimer.setOrderTime(1);
                hud.updateScore(false, (numberOfOrders + 1 - ordersArray.size()) * 35, orderTimer.getOrderTime());
                ordersArray.remove(0);
                hud.updateOrder(false, orderNum);
                return;
            }
            ordersArray.get(0).create(trayX, trayY, game.getBatch());
        }
    }

    /**
     * The render method updates the screen by calling the update method with the given delta time, and rendering the graphics of the game.
     * <p>
     * It updates the HUD time, clears the screen, and renders the renderer and the hud.
     * <p>
     * Additionally, it checks the state of the game and draws the ingredients, completed recipes, and notifications on the screen.
     *
     * @param delta The time in seconds since the last frame.
     */

    @Override


    public void render(float delta) {
        update(delta);

        if (!getChef().active || !getChef().getUserControlChef()) {
            switchChef();
        }

        if (TimeUtils.timeSinceMillis(idleGameTimer) > 20000) {
            game.goToIdle();
        }

        int timeToNewPower = 22000;
        if (TimeUtils.timeSinceMillis(spawnNewPowerUpTimer) > timeToNewPower) {
            // Ths if statement controls when a new powerup will spawn. they spawn a regular intervals defined by a timer
            cookingSpeedBoost newPower = new cookingSpeedBoost(this.world, new TextureRegion(new Texture("powerUps/powerUpCoin.png")), 0.3f, 0.3f);
            newPower.setPowerUp(new powerUpRandom());
            powerUpArray.add(newPower);
            spawnNewPowerUpTimer = TimeUtils.millis();
        }

        if (messageUp && TimeUtils.timeSinceMillis(shopMessageTimer) > 5000) {
            messageLabel.remove();
            messageUp = false;
        }

        //Execute handleEvent each 1 second
        timeSeconds += Gdx.graphics.getRawDeltaTime();
        timeSecondsCount += Gdx.graphics.getDeltaTime();

        if (Math.round(timeSecondsCount) == 5 && !createdOrder) {
            createdOrder = true;
            createOrder();
        }
        float period = 1f;
        if (timeSeconds > period) {
            timeSeconds -= period;
            ///Chef movement stuff needed for testing

            float chef2X = chef2.getX();
            float chef2Y = chef2.getY();

            String xFormatted = String.format("%.4g", chef2X);
            String yFormatted = String.format("%.3g", chef2Y);

            System.out.println(yFormatted);
            hud.updateTime(scenarioComplete);
        }

        Gdx.gl.glClear(1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        game.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        Gdx.input.setInputProcessor(hud.stage);

        hud.stage.addActor(button);
        if (activateShop) {
            //This lays out the buttons of the shop
            saveGame.setX(50);
            saveGame.setY(40);
            hud.stage.addActor(saveGame);

            button2.setX(50);
            hud.stage.addActor(button2);
            buttonPans.setX(50);
            buttonPans.setY(20);
            hud.stage.addActor(buttonPans);
        } else {
            buttonPans.remove();
            button2.remove();
            saveGame.remove();
        }

        game.getBatch().setProjectionMatrix(gameCam.combined);
        game.getBatch().begin();

        updateOrder();
        //powerUp.getBody().setTransform(new Vector2(0,0),30);
        //powerUp.render(game.getBatch());
        for (int i = 0; i < powerUpArray.size(); i++) {
            //The powerUpArray is an array containing all the powerups that need to be rendered. new powerups can be added to this array
            powerUpArray.get(i).render(game.getBatch());
        }
        for (Chef chef : chefList) {
            chef.draw(game.getBatch());
        }
        //System.out.println(chef1.getX());
        // System.out.println(chef1.getY());

        getChef().drawNotification(game.getBatch());
        if (isActiveOrder) {
            orderTimer.render(hud, game);
        }
        if (plateStation.getPlate().size() > 0) {
            for (Object ing : plateStation.getPlate()) {
                Ingredient ingNew = (Ingredient) ing;
                ingNew.create(plateStation.getX(), plateStation.getY(), game.getBatch());
            }
        } else if (plateStation.getCompletedRecipe() != null) {
            Recipe recipeNew = plateStation.getCompletedRecipe();
            recipeNew.create(plateStation.getX(), plateStation.getY(), game.getBatch());
        }
        for (Chef chef : chefList) {
            if (!chef.getUserControlChef() || chef.isCooking) {
                if (chef.getTouchingTile() != null && chef.getInHandsIng() != null) {
                    if (chef.getTouchingTile().getUserData() instanceof InteractiveTileObject) {
                        chef.displayIngStatic(game.getBatch());
                    }
                }
            }
            if (chef.previousInHandRecipe != null) {
                chef.displayIngDynamic(game.getBatch());
            }
        }
        game.getBatch().end();
    }

    /**
     * This returns a button with application listener.
     * <p>
     * When button is pressed it will call the function specified in the clicked function
     *
     * @param message The message you want to display on the button
     */
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

                switch (message) {
                    case "shop":
                        System.out.println("shopping");
                        activateShop = !activateShop;
                        break;
                    case "chop":
                        if (additionChopCount < 4 && hud.getScore() >= 5) {
                            hud.purchase(5);

                            System.out.println("buying chopping baords");
                            kitchenEdit.editCVSFile(2, 4 + additionChopCount, "2");
                            additionChopCount++;
                            messageLabel.remove();
                            addToHud("bought chopping board");
                            messageUp = true;
                            shopMessageTimer = TimeUtils.millis();
                            reRender();
                        } else {
                            messageLabel.remove();
                            addToHud("lack of money or at max chopping boards");
                            messageUp = true;
                            shopMessageTimer = TimeUtils.millis();
                        }

                        break;
                    case "pan":

                        System.out.println("buying pans");
                        panCheck();
                        break;
                    case "save game":
                        gameSaveTool.saveMyGame(selfRef);
                        break;

                }

                System.out.println("Clicked! Is checked: ");
                //game.goToGameOver();
            }
        });
        return button;


    }

    public void panCheck() {
        if (addictionPanCount < 3 && hud.getScore() >= 5) {
            hud.purchase(5);
            kitchenEdit.editCVSFile(9, 5 - addictionPanCount, "9");
            addictionPanCount++;
            messageLabel.remove();
            addToHud("bought pans");
            messageUp = true;
            shopMessageTimer = TimeUtils.millis();
            reRender();
        } else {
            messageLabel.remove();
            addToHud("lack of money or at max pans");
            messageUp = true;
            shopMessageTimer = TimeUtils.millis();
        }
    }

    /**
     * reRender is used to reload the kitchen onto the screen so any changes to the kitchen can be displaced on the screen
     */

    private void reRender() { // loads a kitchen from the temporary file for the current instance of the game
        TmxMapLoader mapLoader = new TmxMapLoader(new LocalFileHandleResolver());
        map = mapLoader.load("KitchenTemp.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        new B2WorldCreator(world, map, this);
    }

    private void reRender(String inFile) { //loads a kitchen from perinant storage on game load

        FileHandle tmxFile = Gdx.files.internal("kitchenSave.txt");
        if (tmxFile.exists()) {
            String tmxContents = tmxFile.readString();
            FileHandle saveFile = Gdx.files.local("kitchenTemp.tmx");
            saveFile.writeString(tmxContents, false);
            saveFile = null;
        }

        TmxMapLoader mapLoader = new TmxMapLoader(new LocalFileHandleResolver());
        map = mapLoader.load("KitchenTemp.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        new B2WorldCreator(world, map, this);
        tmxFile = null;

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        hud.dispose();
    }

    public Chef getChef1() {
        return chefList.get(0);
    }

    public Chef getChef2() {
        return chefList.get(1);
    }

    public Chef getChef3() {
        return chefList.get(2);
    }

    public HUD getHud() {
        return hud;
    }

    /**
     * The add to Hud function is used to displace text towards the bottom of the screen. This can be used to provide the plyer visual feedback
     * as to things theye have done in the game world like buying a chopping board
     *
     * @param Text - the message to be displayed
     */

    public void addToHud(String Text) {

        Label.LabelStyle labelStyle = new Label.LabelStyle(hud.font, Color.WHITE);
        messageLabel = new Label(Text, labelStyle);
        //messageLabel.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        messageLabel.setPosition(50, 0);
        //messageLabel.setAlignment(Align.center);

        hud.stage.addActor(messageLabel);

        // Fade out the label over 2 seconds
    }

    public void setDifficultyScore(double difficultyScore) {
        orderTimer.setDifficulty(difficultyScore);
        this.difficultyScore = difficultyScore;
    }

    public void toggleShop() {
        this.activateShop = !activateShop;
    }

    public void buyChoppingBoard() {
        if (additionChopCount < 4 && hud.getScore() >= 5) {
            hud.purchase(5);
            System.out.println("buying chopping baords");
            kitchenEdit.editCVSFile(2, 4 + additionChopCount, "2");
            additionChopCount++;
            messageLabel.remove();
            addToHud("bought chopping board");
            messageUp = true;
            shopMessageTimer = TimeUtils.millis();
            reRender();
        }
    }

    public void buyPan() {
        System.out.println("buying pans");
        panCheck();
    }
}

