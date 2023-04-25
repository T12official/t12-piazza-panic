package com.team13.piazzapanic;

import Ingredients.Ingredient;
import Recipe.Recipe;
import Sprites.*;
import Recipe.Order;
import Tools.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import powerUps.cookingSpeedBoost;
import powerUps.speedUpCooking;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


/**
 * The PlayScreen class is responsible for displaying the game to the user and handling the user's interactions.
 * The PlayScreen class implements the Screen interface which is part of the LibGDX framework.
 *
 * The PlayScreen class contains several important fields, including the game instance, stage instance, viewport instance,
 * and several other helper classes and variables. The game instance is used to access the global game configuration and
 * to switch between screens. The stage instance is used to display the graphics and handle user interactions, while the
 * viewport instance is used to manage the scaling and resizing of the game window.
 *
 * The PlayScreen class also contains several methods for initializing and updating the game state, including the
 * constructor, show(), render(), update(), and dispose() methods. The constructor sets up the stage, viewport, and
 * other helper classes and variables. The show() method is called when the PlayScreen becomes the active screen. The
 * render() method is called repeatedly to render the game graphics and update the game state. The update() method is
 * called to update the game state and handle user inputs. The dispose() method is called when the PlayScreen is no longer
 * needed and is used to clean up resources and prevent memory leaks.
 */


public class PlayScreen implements Screen {

    public final MainGame game;
    private boolean loadMyGame = false;
    public double difficultyScore;
    private Label messageLabel;
    private final OrthographicCamera gamecam;
    private final Viewport gameport;
    public final HUD hud;
    private final TextButton button;
    private final TextButton button2;
    private final TextButton buttonPans;
    private final TextButton saveGame;

    public OrderTimer orderTimer;

    public boolean isActiveOrder = false;
    private GameOverScreen gameover;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public boolean dispose = false;

    private World world;
    private Chef chef1;
    private Chef chef2;
    private Chef chef3;
    public long idleGametimer;
    private Chef controlledChef;
    private kitchenChangerAPI kitchenEdit;
    public cookingSpeedBoost toKill;

    public ArrayList<Order> ordersArray;

    public PlateStation plateStation;
    private PlayScreen selfRef = this;


    public Boolean scenarioComplete;
    public Boolean createdOrder;

    public static float trayX;
    public static float trayY;

    private float timeSeconds = 0f;
    private long shopMessageTimer = 0;
    private boolean messageUp = false;
    private long spawnNewPowerUpTimer;
    public ArrayList<cookingSpeedBoost> powerUpArray;

    private float timeSecondsCount = 0f;
    private boolean activateShop = false;
    private int addictionPanCount = 0;
    private int additionChopCount = 0;
    public cookingSpeedBoost powerUp;
    private int timeToNewPower = 22000;
    public int numberOfOrders = 5;
    private Integer orderNum = 1;

    /**
     * PlayScreen constructor initializes the game instance, sets initial conditions for scenarioComplete and createdOrder,
     * creates and initializes game camera and viewport,
     * creates and initializes HUD and orders hud, loads and initializes the map,
     * creates and initializes world, creates and initializes chefs and sets them, sets contact listener for world, and initializes ordersArray.
     * @param game The MainGame instance that the PlayScreen will be a part of.
     */


    public PlayScreen(MainGame game){
        spawnNewPowerUpTimer = TimeUtils.millis();
        kitchenEdit = new kitchenChangerAPI();
        powerUpArray = new ArrayList<>(); //The powerUpArray is an array containing all the powerups that need to be rendered. new powerups can be added to this array

        kitchenEdit.readFile();
        resetIdleTimer();
        this.game = game;
        gameover = new GameOverScreen(game);

        scenarioComplete = Boolean.FALSE;
        createdOrder = Boolean.FALSE;
        gamecam = new OrthographicCamera();
        // FitViewport to maintain aspect ratio whilst scaling to screen size
        gameport = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gamecam);
        // create HUD for score & time
        hud = new HUD(game.batch);
        // create orders hud
        Orders orders = new Orders(game.batch);
        // create map
        TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        map = mapLoader.load("Kitchen.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);
        button = (TextButton) getButton("shop");
        button2 = (TextButton) getButton("chop");
        buttonPans = (TextButton) getButton("pan");
        saveGame = (TextButton) getButton("save game");
        world = new World(new Vector2(0,0), true);
        new B2WorldCreator(world, map, this);
        powerUp  = new cookingSpeedBoost(this.world,new TextureRegion( new  Texture("powerUps/powerUpCoin.png")), 126,85);
        powerUp.setPowerUp(new speedUpCooking());
        chef1 = new Chef(this.world, 31.5F,65);
        chef2 = new Chef(this.world, 128,65);
        chef3 = new Chef(this.world, 128, 88);


        controlledChef = chef1;
        world.setContactListener(new WorldContactListener(world, this));
        controlledChef.notificationSetBounds("Down");

        ordersArray = new ArrayList<>();
        addToHud("welcome");
        messageLabel.remove();


        orderTimer =  new OrderTimer();
        orderTimer.setDifficulty(difficultyScore);
    }

    public void onStartLoadGame(){
        loadMyGame = true;
    }

    public void resetIdleTimer(){
        idleGametimer = TimeUtils.millis();
    }

    @Override
    public void show(){

    }


    /**
     * The handleInput method is responsible for handling the input events of the game such as movement and interaction with objects.
     *
     * It checks if the 'R' key is just pressed and both chefs have the user control, if so,
     * it switches the control between the two chefs.
     *
     * If the controlled chef does not have the user control,
     * the method checks if chef1 or chef2 have the user control and sets the control to that chef.
     *
     * If the controlled chef has the user control,
     * it checks if the 'W', 'A', 'S', or 'D' keys are pressed and sets the velocity of the chef accordingly.
     *
     * If the 'E' key is just pressed and the chef is touching a tile,
     * it checks the type of tile and sets the chef's in-hands ingredient accordingly.
     *
     * The method also sets the direction of the chef based on its linear velocity.
     *
     * @param dt is the time delta between the current and previous frame.
     */

    public void handleInput(float dt){

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)){
            //example load game
            gameSaveTool.loadMyGame(this);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.V)){
            //example save game
            gameSaveTool.saveMyGame(this);
        }

        if ((Gdx.input.isKeyJustPressed(Input.Keys.R) &&
                ((chef1.getUserControlChef() &&
                chef2.getUserControlChef()) || (chef1.getUserControlChef() && chef3.getUserControlChef()) || (chef2.getUserControlChef() && chef3.getUserControlChef())) )) {


            if (controlledChef.equals(chef1)) {
                resetIdleTimer();
                controlledChef.b2body.setLinearVelocity(0, 0);
                if (chef2.getUserControlChef()) {
                    controlledChef = chef2;
                    if (chef2.isCooking) {
                        chef2.isCooking = false;
                        chef2.setChefSkin(chef2.getInHandsIng());
                    }
                }
                else {

                    if (chef3.getUserControlChef()) {
                        controlledChef = chef3;
                        if (chef3.isCooking) {
                            chef3.isCooking = false;
                            chef3.setChefSkin(chef3.getInHandsIng());
                        }
                    }

                }
            }
            else if (controlledChef.equals(chef2)){
                resetIdleTimer();
                controlledChef.b2body.setLinearVelocity(0, 0);
                controlledChef = chef3;
                if (chef3.getUserControlChef()) {
                    if (chef3.isCooking) {
                        chef3.isCooking = false;
                        chef3.setChefSkin(chef3.getInHandsIng());
                    }
                }
                else {

                    if (chef1.getUserControlChef()) {
                        controlledChef = chef1;
                        if (chef1.isCooking) {
                            chef1.isCooking = false;
                            chef1.setChefSkin(chef1.getInHandsIng());
                        }
                    }

                }

            }
            else {
                resetIdleTimer();
                controlledChef.b2body.setLinearVelocity(0, 0);
                controlledChef = chef1;
                if (chef1.getUserControlChef()) {
                    if (chef1.isCooking) {
                        chef1.isCooking = false;
                        chef1.setChefSkin(chef1.getInHandsIng());
                    }
                }
                else {

                    if (chef3.getUserControlChef()) {
                        controlledChef = chef3;
                        if (chef3.isCooking) {
                            chef3.isCooking = false;
                            chef3.setChefSkin(chef3.getInHandsIng());
                        }
                    }

                }
            }
        }
        if (!controlledChef.getUserControlChef()){
            if (chef1.getUserControlChef()){
                controlledChef.b2body.setLinearVelocity(0, 0);
                controlledChef = chef1;
            } else if(chef2.getUserControlChef()) {
                controlledChef.b2body.setLinearVelocity(0, 0);
                controlledChef = chef2;
            }else if(chef3.getUserControlChef()) {
                controlledChef.b2body.setLinearVelocity(0, 0);
                controlledChef = chef3;
            }

        }
        if (controlledChef.getUserControlChef()) {
                float xVelocity = 0;
                float yVelocity = 0;

                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    resetIdleTimer();
                    yVelocity += 0.5f;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    resetIdleTimer();
                    xVelocity -= 0.5f;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    resetIdleTimer();
                    yVelocity -= 0.5f;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    resetIdleTimer();
                    xVelocity += 0.5f;
                }
                controlledChef.b2body.setLinearVelocity(xVelocity, yVelocity);
            }
            else {
                controlledChef.b2body.setLinearVelocity(0, 0);
            }
        if (controlledChef.b2body.getLinearVelocity().x > 0){
            controlledChef.notificationSetBounds("Right");
        }
        if (controlledChef.b2body.getLinearVelocity().x < 0){
            controlledChef.notificationSetBounds("Left");
        }
        if (controlledChef.b2body.getLinearVelocity().y > 0){
            controlledChef.notificationSetBounds("Up");
        }
        if (controlledChef.b2body.getLinearVelocity().y < 0){
            controlledChef.notificationSetBounds("Down");
        }


        if(Gdx.input.isKeyJustPressed(Input.Keys.E)){

            resetIdleTimer();
                if(controlledChef.getTouchingTile() != null){
                    InteractiveTileObject tile = (InteractiveTileObject) controlledChef.getTouchingTile().getUserData();
                    String tileName = tile.getClass().getName();
                    System.out.println(tileName + " apples aaa");
                    if (controlledChef.getInHandsIng() == null && controlledChef.getInHandsRecipe() == null) {
                        switch (tileName) {
                            case "Sprites.TomatoStation":
                                TomatoStation tomatoTile = (TomatoStation) tile;
                                controlledChef.setInHandsIng(tomatoTile.getIngredient());
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.BunsStation":
                                BunsStation bunTile = (BunsStation) tile;
                                controlledChef.setInHandsIng(bunTile.getIngredient());
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.OnionStation":
                                OnionStation onionTile = (OnionStation) tile;
                                controlledChef.setInHandsIng(onionTile.getIngredient());
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.SteakStation":
                                SteakStation steakTile = (SteakStation) tile;
                                controlledChef.setInHandsIng(steakTile.getIngredient());
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.LettuceStation":
                                LettuceStation lettuceTile = (LettuceStation) tile;
                                controlledChef.setInHandsIng(lettuceTile.getIngredient());
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.potatoesStation":
                                System.out.println("giving myself a spud");
                                potatoesStation pots = (potatoesStation) tile;
                                controlledChef.setInHandsIng(pots.getIngredient());
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.cheeseStation":
                                System.out.println("giving myself cheese");
                                cheeseStation cheese = (cheeseStation) tile;
                                controlledChef.setInHandsIng(cheese.getIngredient());
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.pizzaDoughStation":
                                System.out.println("giving myself pizza");
                                pizzaDoughStation dough = (pizzaDoughStation) tile;
                                controlledChef.setInHandsIng(dough.getIngredient());
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                            case "Sprites.PlateStation":
                                if(plateStation.getPlate().size() > 0 || plateStation.getCompletedRecipe() != null){
                                    controlledChef.pickUpItemFrom(tile);

                                }

                        }
                    } else {
                        switch (tileName) {
                            case "Sprites.Bin":
                                System.out.println("giving myself a spud");
                                controlledChef.setInHandsRecipe(null);
                                controlledChef.setInHandsIng(null);
                                controlledChef.setChefSkin(null);
                                break;

                            case "Sprites.ChoppingBoard":
                                if(controlledChef.getInHandsIng() != null){
                                    if(controlledChef.getInHandsIng().prepareTime > 0){
                                        controlledChef.setUserControlChef(false);
                                    }
                                }
                               break;
                            case "Sprites.PlateStation":
                                if (controlledChef.getInHandsRecipe() == null){
                                controlledChef.dropItemOn(tile, controlledChef.getInHandsIng());
                                controlledChef.setChefSkin(null);
                            }
                                break;
                            case "Sprites.Pan":
                                if(controlledChef.getInHandsIng() != null) {
                                    if (controlledChef.getInHandsIng().isPrepared() && controlledChef.getInHandsIng().cookTime > 0){
                                        controlledChef.setUserControlChef(false);
                                        controlledChef.isCooking = true;
                                    }
                                }

                                break;
                            case "Sprites.CompletedDishStation":

                                if (controlledChef.getInHandsRecipe() != null){
                                    if(controlledChef.getInHandsRecipe() == ordersArray.get(0).recipe){
                                        //TODO UPDATE CHANGE LOG FOR THIS
                                        if (orderTimer.getOrderTime() == 0){
                                            hud.decrementReps();
                                            if (hud.getRepPoints() == 0){System.out.println("game over"); game.goToGameOver();}
                                        }
                                        controlledChef.dropItemOn(tile);
                                        ordersArray.get(0).orderComplete = true;
                                        controlledChef.setChefSkin(null);
                                        if(ordersArray.size()==1){
                                            scenarioComplete = Boolean.TRUE;
                                            game.goToGameOver();
                                        }
                                    }
                                }
                                break;

                        }
                    }

                }
            }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            System.out.println("do stuff!!!!!!!!!!!!!!!");
            resetIdleTimer();
            //Some stuff
        }
        }

    public void destroyPowerup(World world, cookingSpeedBoost toKill){
        world.destroyBody(toKill.getBody());
        toKill.dispose();
    }

    /**
     * The update method updates the game elements, such as camera and characters,
     * based on a specified time interval "dt".
     * @param dt time interval for the update
     */

    public void update(float dt){
        if (loadMyGame) {
            gameSaveTool.loadMyGame(this);
            reRender("apple");
            //TODO add kitchenload
            loadMyGame = false;
        }

        if (dispose){
            destroyPowerup(world, toKill);
            world.destroyBody(powerUp.getBody());
            powerUp.getTexture().dispose();
            //powerUp.setTexture(null);
            powerUp.dispose();
            dispose = false;
        }

        handleInput(dt);

        gamecam.update();
        renderer.setView(gamecam);
        chef1.update(dt);
        chef2.update(dt);
        chef3.update(dt);
        powerUp.update(dt);
        world.step(1/60f, 6, 2);
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
        Texture jacket_recipy = new Texture("Food/jacketPotato.png");
        Texture pizza_recipe = new Texture("Food/pizza_recipe.png");
        Order order;

        for(int i = 0; i<numberOfOrders; i++){
            if(randomNum==1) {
                order = new Order(PlateStation.burgerRecipe, burger_recipe);
            }
            else if(randomNum==2) {
                order = new Order(PlateStation.saladRecipe, salad_recipe);
            }
            else if (randomNum==3){
                order = new Order(PlateStation.mypizzaRecipy, pizza_recipe);
            }
            else {
                order = new Order(PlateStation.jacketPotatoRec, jacket_recipy );
            }
            ordersArray.add(order);
            randomNum = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        }
        hud.updateOrder(Boolean.FALSE, 1);
    }

    /**
     * Updates the orders as they are completed, or if the game scenario has been completed.
     */
    public void updateOrder(){
        if(scenarioComplete==Boolean.TRUE) {
            hud.updateScore(Boolean.TRUE, (numberOfOrders + 1 - ordersArray.size()) * 35);
            hud.updateOrder(Boolean.TRUE, 0);
            return;
        }
        if(ordersArray.size() != 0) {
            if (ordersArray.get(0).orderComplete) {
                orderNum ++;
                isActiveOrder = true;
                orderTimer.setOrderTime(1);
                hud.updateScore(Boolean.FALSE, (numberOfOrders + 1 - ordersArray.size()) * 35);
                ordersArray.remove(0);
                hud.updateOrder(Boolean.FALSE, orderNum);
                return;
            }
            ordersArray.get(0).create(trayX, trayY, game.batch);
        }
    }

    /**

     The render method updates the screen by calling the update method with the given delta time, and rendering the graphics of the game.

     It updates the HUD time, clears the screen, and renders the renderer and the hud.

     Additionally, it checks the state of the game and draws the ingredients, completed recipes, and notifications on the screen.

     @param delta The time in seconds since the last frame.
     */

    @Override


    public void render(float delta){
        update(delta);

        if (TimeUtils.timeSinceMillis(idleGametimer) > 20000){
            game.goToIdle();
        }

        if (TimeUtils.timeSinceMillis(spawnNewPowerUpTimer) > timeToNewPower){
            // Ths if statement controls when a new powerup will spawn. they spawn a regular intervals defined by a timer
            cookingSpeedBoost newPower =  new cookingSpeedBoost(this.world,new TextureRegion( new  Texture("powerUps/powerUpCoin.png")), 0.4f,0.4f);
            newPower.setPowerUp(new speedUpCooking());
            powerUpArray.add(newPower);
            spawnNewPowerUpTimer = TimeUtils.millis();

        }

        if (messageUp && TimeUtils.timeSinceMillis(shopMessageTimer) > 5000){
            messageLabel.remove();
            messageUp = false;
        }

        //Execute handleEvent each 1 second
        timeSeconds +=Gdx.graphics.getRawDeltaTime();
        timeSecondsCount += Gdx.graphics.getDeltaTime();

        if(Math.round(timeSecondsCount) == 5 && createdOrder == Boolean.FALSE){
            createdOrder = Boolean.TRUE;
            createOrder();
        }
        float period = 1f;
        if(timeSeconds > period) {
            timeSeconds -= period;
            hud.updateTime(scenarioComplete);
        }

        Gdx.gl.glClear(1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        Gdx.input.setInputProcessor(hud.stage);

        hud.stage.addActor(button);
        if (activateShop){
            //This lays out the buttons of the shop
            saveGame.setX(50);
            saveGame.setY(40);
            hud.stage.addActor(saveGame);

            button2.setX(50);
            hud.stage.addActor(button2);
            buttonPans.setX(50);
            buttonPans.setY(20);
            hud.stage.addActor(buttonPans);
        }
        else {
            buttonPans.remove();
            button2.remove();
            saveGame.remove();
        }

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();


        updateOrder();
        //powerUp.getBody().setTransform(new Vector2(0,0),30);
        //powerUp.render(game.batch);
        for (int i = 0 ; i < powerUpArray.size(); i ++){
            //The powerUpArray is an array containing all the powerups that need to be rendered. new powerups can be added to this array
            powerUpArray.get(i).render(game.batch);
        }
        chef1.draw(game.batch);
        chef2.draw(game.batch);
        chef3.draw(game.batch);
        //System.out.println(chef1.getX());
       // System.out.println(chef1.getY());


        controlledChef.drawNotification(game.batch);
        if (isActiveOrder){
            orderTimer.render(hud, game);
        }
        if (plateStation.getPlate().size() > 0){
            for(Object ing : plateStation.getPlate()){
                Ingredient ingNew = (Ingredient) ing;
                ingNew.create(plateStation.getX(), plateStation.getY(),game.batch);
            }
        } else if (plateStation.getCompletedRecipe() != null){
            Recipe recipeNew = plateStation.getCompletedRecipe();
            recipeNew.create(plateStation.getX(), plateStation.getY(), game.batch);
        }
        if (!chef1.getUserControlChef() || chef1.isCooking) {
            if (chef1.getTouchingTile() != null && chef1.getInHandsIng() != null){
                if (chef1.getTouchingTile().getUserData() instanceof InteractiveTileObject){
                    chef1.displayIngStatic(game.batch);
                }
            }
        }
        if (!chef2.getUserControlChef() || chef1.isCooking) {
            if (chef2.getTouchingTile() != null && chef2.getInHandsIng() != null) {
                if (chef2.getTouchingTile().getUserData() instanceof InteractiveTileObject) {
                    chef2.displayIngStatic(game.batch);
                }
            }
        }
        if (!chef3.getUserControlChef() || chef3.isCooking) {
            if (chef3.getTouchingTile() != null && chef3.getInHandsIng() != null){
                if (chef3.getTouchingTile().getUserData() instanceof InteractiveTileObject){
                    chef3.displayIngStatic(game.batch);
                }
            }
        }
        if (chef1.previousInHandRecipe != null){
            chef1.displayIngDynamic(game.batch);
        }
        if (chef2.previousInHandRecipe != null){
            chef2.displayIngDynamic(game.batch);
        }
        if (chef3.previousInHandRecipe != null){
            chef3.displayIngDynamic(game.batch);
        }
        game.batch.end();
    }

    /**

     This returns a button with application listener.

     When button is pressed it will call the function specified in the clicked function

     @param message The message you want to display on the button
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
            public void clicked (InputEvent event, float x, float y) {

                switch(message){
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
                        }
                        else {
                            messageLabel.remove();
                            addToHud("lack of money or at max chopping boards");
                            messageUp = true;
                            shopMessageTimer = TimeUtils.millis();
                        }



                        break;
                    case "pan":

                        System.out.println("buying pans");
                        if (addictionPanCount < 3 && hud.getScore() >= 5) {
                            hud.purchase(5);
                            kitchenEdit.editCVSFile(9, 5 - addictionPanCount, "9");
                            addictionPanCount++;
                            messageLabel.remove();
                            addToHud("bought pans");
                            messageUp = true;
                            shopMessageTimer = TimeUtils.millis();
                            reRender();
                        }
                        else {
                            messageLabel.remove();
                            addToHud("lack of money or at max pans");
                            messageUp = true;
                            shopMessageTimer = TimeUtils.millis();
                        }
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

    /**
     * reRender is used to reload the kitchen onto the screen so any changes to the kitchen can be displaced on the screen
     */

    private void reRender(){ //loads a kitchen from the tempory file for the current instance of the game
        TmxMapLoader mapLoader = new TmxMapLoader(new LocalFileHandleResolver());
        map = mapLoader.load("KitchenTemp.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        //gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);
        new B2WorldCreator(world, map, this);

    }
    private void reRender(String inFile){ //loads a kitchen from perinant storage on game load

        FileHandle tmxFile = Gdx.files.internal("kitchenSave.txt");
        String tmxContents = tmxFile.readString();

        FileHandle saveFile = Gdx.files.local("kitchenTemp.tmx");
        saveFile.writeString(tmxContents, false);

        TmxMapLoader mapLoader = new TmxMapLoader(new LocalFileHandleResolver());
        map = mapLoader.load("KitchenTemp.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        //gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);
        new B2WorldCreator(world, map, this);
        saveFile = null;
        tmxFile = null;

    }

    @Override
    public void resize(int width, int height){
        gameport.update(width, height);
    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){
        
    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){
        map.dispose();
        renderer.dispose();
        world.dispose();
        hud.dispose();
    }

    public Chef getChef1() {
        return chef1;
    }

    public Chef getChef2() {
        return chef2;
    }

    public Chef getChef3() {
        return chef3;
    }

    public HUD getHud() {
        return hud;
    }

    /**
     * The add to Hud function is used to displace text towards the bottom of the screen. This can be used to provide the plyer visual feedback
     * as to things theye have done in the game world like buying a chopping board
     * @param Text - the message to be displayed
     */

    public void addToHud(String Text){


        Label.LabelStyle labelStyle = new Label.LabelStyle(hud.font, Color.WHITE);
        messageLabel = new Label(Text, labelStyle);
        //messageLabel.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        messageLabel.setPosition(50,0);
        //messageLabel.setAlignment(Align.center);

        hud.stage.addActor(messageLabel);

        // Fade out the label over 2 seconds
    }
    public void removeLabel(){
        messageLabel.remove();
    }

    public void setDifficultyScore(double difficultyScore) {
        orderTimer.setDifficulty(difficultyScore);
        this.difficultyScore = difficultyScore;
    }
}
