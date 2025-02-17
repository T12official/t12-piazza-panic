package Sprites;

import Ingredients.*;
import Recipe.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.team13.piazzapanic.MainGame;
import com.team13.piazzapanic.Playable;

import java.util.Objects;

/**
 * Chef class extends {@link Sprite} and represents a chef in the game.
 * It has fields for the world it exists in, a Box2D body, the initial X and Y
 * positions, a wait timer, collision flag, various textures for different skins,
 * state (UP, DOWN, LEFT, RIGHT), skin needed, fixture of what it is touching, ingredient
 * and recipe in hand, control flag, circle sprite, chef notification X, Y, width and height,
 * and completed dish station.
 */

public class Chef extends Sprite implements InputProcessor {
    private final float initialX;
    private final float initialY;
    private final Texture normalChef;
    private final Texture bunsChef;
    private final Texture bunsToastedChef;
    private final Texture burgerChef;
    private final Texture lettuceChef;
    private final Texture onionChef;
    private final Texture tomatoChef;
    private final Texture cheeseChef;
    private final Texture spudsChef;
    private final Texture choppedLettuceChef;
    private final Texture choppedOnionChef;
    private final Texture choppedTomatoChef;
    private final Texture pattyChef;
    private final Texture completedBurgerChef;
    private final Texture meatChef;
    private final Texture rawDoughChef;
    private final Texture saladChef;
    private final Texture newDough;
    private final Texture doneDough;
    private final Texture pizzaChef;
    private final Texture jacketChef;
    private final Sprite circleSprite;
    private final Playable level;
    public World world;
    public Body b2body;
    public double cookingSpeedModifier = 1;
    public float runSpeedModifier = 1F;
    public boolean active = false;
    public Vector2 startVector;
    public boolean chefOnChefCollision;
    public State currentState;
    public boolean isCooking = false;
    public int nextOrderAppearTime;
    public Recipe previousInHandRecipe;
    private float yVelocity = 0;
    private float xVelocity = 0;
    private float waitTimer;
    private float putDownWaitTimer;
    private TextureRegion currentSkin;
    private Texture skinNeeded;
    private Fixture whatTouching;
    private Ingredient inHandsIng;
    private Recipe inHandsRecipe;
    private Boolean userControlChef;
    private float notificationX;
    private float notificationY;
    private float notificationWidth;
    private float notificationHeight;
    private CompletedDishStation completedStation;

    /**
     * Chef class constructor that initializes all the fields
     *
     * @param baseLevel the parent of the world the chef exists in
     * @param startX    starting X position
     * @param startY    starting Y position
     */

    public Chef(Playable baseLevel, float startX, float startY) {
        level = baseLevel;
        world = level.getWorld();
        initialX = startX / MainGame.PPM;
        initialY = startY / MainGame.PPM;
        jacketChef = new Texture("Chef/Chef_holding_jacket.png");
        normalChef = new Texture("Chef/Chef_normal.png");
        pizzaChef = new Texture("Chef/Chef_holding_pizza.png");
        doneDough = new Texture("Chef/Chef_holding_doughCooked.png");
        rawDoughChef = new Texture("Chef/Chef_holding_dough.png");
        bunsChef = new Texture("Chef/Chef_holding_buns.png");
        bunsToastedChef = new Texture("Chef/Chef_holding_buns_toasted.png");
        burgerChef = new Texture("Chef/Chef_holding_burger.png");
        lettuceChef = new Texture("Chef/Chef_holding_lettuce.png");
        onionChef = new Texture("Chef/Chef_holding_onion.png");
        tomatoChef = new Texture("Chef/Chef_holding_tomato.png");
        choppedLettuceChef = new Texture("Chef/Chef_holding_chopped_lettuce.png");
        choppedOnionChef = new Texture("Chef/Chef_holding_chopped_onion.png");
        choppedTomatoChef = new Texture("Chef/Chef_holding_chopped_tomato.png");
        pattyChef = new Texture("Chef/Chef_holding_patty.png");
        completedBurgerChef = new Texture("Chef/Chef_holding_front.png");
        meatChef = new Texture("Chef/Chef_holding_meat.png");
        saladChef = new Texture("Chef/Chef_holding_salad.png");
        spudsChef = new Texture("Chef/Chef_holding_potato.png");
        cheeseChef = new Texture("Chef/Chef_holding_cheese.png");
        newDough = new Texture("Chef/Chef_holding_rawdough.png");


        skinNeeded = normalChef;

        currentState = State.DOWN;

        defineChef();

        float chefWidth = 13 / MainGame.PPM;
        float chefHeight = 20 / MainGame.PPM;
        setBounds(0, 0, chefWidth, chefHeight);
        chefOnChefCollision = false;
        waitTimer = 0;
        putDownWaitTimer = 0;
        startVector = new Vector2(0, 0);
        whatTouching = null;
        inHandsIng = null;
        inHandsRecipe = null;
        userControlChef = true;
        Texture circleTexture = new Texture("Chef/chefIdentifier.png");
        circleSprite = new Sprite(circleTexture);
        nextOrderAppearTime = 3;
        completedStation = null;
    }

    public float getyVelocity() {
        return yVelocity;
    }

    public float getxVelocity() {
        return xVelocity;
    }

    /**
     * Update the position and region of the chef and set the notification position based on the chef's current state.
     *
     * @param dt The delta time.
     */
    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        currentSkin = getSkin(dt);
        setRegion(currentSkin);
        switch (currentState) {
            case UP:
                if (this.inHandsIng == null && this.inHandsRecipe == null) {
                    notificationX = b2body.getPosition().x - (1.75f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (7.7f / MainGame.PPM);
                } else {
                    notificationX = b2body.getPosition().x - (0.67f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (7.2f / MainGame.PPM);
                }
                break;
            case DOWN:
                if (this.inHandsIng == null && this.inHandsRecipe == null) {
                    notificationX = b2body.getPosition().x + (0.95f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (5.015f / MainGame.PPM);
                } else {
                    notificationX = b2body.getPosition().x + (0.55f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (5.3f / MainGame.PPM);
                }
                break;
            case LEFT:
                if (this.inHandsIng == null && this.inHandsRecipe == null) {
                    notificationX = b2body.getPosition().x;
                    notificationY = b2body.getPosition().y - (5.015f / MainGame.PPM);
                } else {
                    notificationX = b2body.getPosition().x - (1.92f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (4.6f / MainGame.PPM);
                }
                break;
            case RIGHT:
                if (this.inHandsIng == null && this.inHandsRecipe == null) {
                    notificationX = b2body.getPosition().x + (0.5f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (5.015f / MainGame.PPM);
                } else {
                    notificationX = b2body.getPosition().x + (0.17f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (4.63f / MainGame.PPM);
                }
                break;
        }


        if (!userControlChef && chefOnChefCollision) {
            waitTimer += dt;
            b2body.setLinearVelocity(new Vector2(startVector.x * -1, startVector.y * -1));
            if (waitTimer > 0.3f) {
                b2body.setLinearVelocity(new Vector2(0, 0));
                chefOnChefCollision = false;
                userControlChef = true;
                waitTimer = 0;
                if (inHandsIng != null) {
                    setChefSkin(inHandsIng);
                }
            }
        } else if (!userControlChef && getInHandsIng().prepareTime > 0) {
            waitTimer += dt;
            if (waitTimer > inHandsIng.prepareTime) {
                inHandsIng.prepareTime = 0;
                inHandsIng.setPrepared();
                userControlChef = true;
                waitTimer = 0;
                setChefSkin(inHandsIng);
            }
        } else if (isCooking && !chefOnChefCollision && getInHandsIng().isPrepared() && (inHandsIng.cookTime > 0 || inHandsIng.getBurnTime() > 0)) {
            waitTimer += dt * cookingSpeedModifier;
            //System.out.println(inHandsIng.cookTime);
            if (!userControlChef && waitTimer > inHandsIng.cookTime) {
                inHandsIng.cookTime = 0;
                inHandsIng.setCooked();
                userControlChef = true;
                //waitTimer = 0;

            }
            if (waitTimer > inHandsIng.getBurnTime()) {
                waitTimer = 0;
                userControlChef = true;
                inHandsIng.setBurned();
                isCooking = false;
                this.setInHandsRecipe(null);
                this.setInHandsIng(null);
                this.setChefSkin(null);
            }
        }
    }

    /**
     * This method sets the bounds for the notification based on the given direction.
     *
     * @param direction - A string representing the direction of the notification.
     *                  Can be "Left", "Right", "Up", or "Down".
     */

    public void notificationSetBounds(String direction) {
        switch (direction) {
            case "Left":
            case "Right":
                notificationWidth = 1.5f / MainGame.PPM;
                notificationHeight = 1.5f / MainGame.PPM;
                break;
            case "Up":
                notificationWidth = 4 / MainGame.PPM;
                notificationHeight = 4 / MainGame.PPM;
                break;
            case "Down":
                notificationWidth = 2 / MainGame.PPM;
                notificationHeight = 2 / MainGame.PPM;
                break;
        }
    }

    /**
     * Draws a notification to help the user understand what chef they are controlling.
     * The notification is a sprite that looks like at "C" on the controlled chef.
     *
     * @param batch The sprite batch that the notification should be drawn with.
     */
    public void drawNotification(SpriteBatch batch) {
        if (this.getUserControlChef()) {
            circleSprite.setBounds(notificationX, notificationY, notificationWidth, notificationHeight);
            circleSprite.draw(batch);
        }
    }

    /**
     * Get the texture region for the current state of the player.
     *
     * @param dt the time difference between this and the last frame
     * @return the texture region for the player's current state
     */

    private TextureRegion getSkin(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case UP:
                region = new TextureRegion(skinNeeded, 0, 0, 33, 46);
                break;
            case DOWN:
                region = new TextureRegion(skinNeeded, 33, 0, 33, 46);
                break;
            case LEFT:
                region = new TextureRegion(skinNeeded, 64, 0, 33, 46);
                break;
            case RIGHT:
                region = new TextureRegion(skinNeeded, 96, 0, 33, 46);
                break;
            default:
                region = currentSkin;
        }
        return region;
    }

    /**
     * Returns the current state of the player based on the controlled chefs velocity.
     *
     * @return current state of the player - UP, DOWN, LEFT, or RIGHT
     */
    public State getState() {
        if (b2body.getLinearVelocity().y > 0)
            return State.UP;
        if (b2body.getLinearVelocity().y < 0)
            return State.DOWN;
        if (b2body.getLinearVelocity().x > 0)
            return State.RIGHT;
        if (b2body.getLinearVelocity().x < 0)
            return State.LEFT;
        else
            return currentState;
    }

    /**
     * Define the body and fixture of the chef object.
     * <p>
     * This method creates a dynamic body definition and sets its position with the `initialX` and `initialY`
     * variables, then creates the body in the physics world. A fixture definition is also created and a
     * circle shape is set with a radius of `4.5f / MainGame.PPM` and a position shifted by `(0.5f / MainGame.PPM)`
     * in the x-axis and `-(5.5f / MainGame.PPM)` in the y-axis. The created fixture is then set as the user data
     * of the chef object.
     */

    public void defineChef() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(initialX, initialY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();


        shape.setRadius(4.5f / MainGame.PPM);
        shape.setPosition(new Vector2(shape.getPosition().x + (0.5f / MainGame.PPM), shape.getPosition().y - (5.5f / MainGame.PPM)));


        fdef.shape = shape;
        //fdef.restitution = 0f;
        b2body.createFixture(fdef).setUserData(this);
    }

    /**
     * Method to set the skin of the chef character based on the item the chef is holding.
     *
     * @param item the item that chef is holding
     *             <p>
     *             The skin is set based on the following cases:
     *             - if item is null, then the skin is set to normalChef
     *             - if item is a Lettuce, then the skin is set to
     *             - choppedLettuceChef if the lettuce is prepared
     *             - lettuceChef if the lettuce is not prepared
     *             - if item is a Steak, then the skin is set to
     *             - burgerChef if the steak is prepared and cooked
     *             - pattyChef if the steak is prepared but not cooked
     *             - meatChef if the steak is not prepared
     *             - if item is an Onion, then the skin is set to
     *             - choppedOnionChef if the onion is prepared
     *             - onionChef if the onion is not prepared
     *             - if item is a Tomato, then the skin is set to
     *             - choppedTomatoChef if the tomato is prepared
     *             - tomatoChef if the tomato is not prepared
     *             - if item is a Bun, then the skin is set to
     *             - bunsToastedChef if the bun is cooked
     *             - bunsChef if the bun is not cooked
     *             - if item is a BurgerRecipe, then the skin is set to completedBurgerChef
     *             - if item is a SaladRecipe, then the skin is set to saladChef
     */

    public void setChefSkin(Object item) {
        if (item == null) {
            skinNeeded = normalChef;
        } else if (item instanceof Lettuce) {
            if (inHandsIng.isPrepared()) {
                skinNeeded = choppedLettuceChef;
            } else {
                skinNeeded = lettuceChef;
            }
        } else if (item instanceof Steak) {
            if (inHandsIng.isPrepared() && inHandsIng.isCooked()) {
                skinNeeded = burgerChef;
            } else if (inHandsIng.isPrepared()) {
                skinNeeded = pattyChef;
            } else {
                skinNeeded = meatChef;
            }
        } else if (item instanceof Onion) {
            if (inHandsIng.isPrepared()) {
                skinNeeded = choppedOnionChef;
            } else {
                skinNeeded = onionChef;
            }
        } else if (item instanceof Tomato) {
            if (inHandsIng.isPrepared()) {
                skinNeeded = choppedTomatoChef;
            } else {
                skinNeeded = tomatoChef;
            }
        } else if (item instanceof Bun) {
            if (inHandsIng.isCooked()) {
                skinNeeded = bunsToastedChef;
            } else {
                skinNeeded = bunsChef;
            }
        } else if (item instanceof BurgerRecipe) {
            skinNeeded = completedBurgerChef;
        } else if (item instanceof SaladRecipe) {
            skinNeeded = saladChef;
        } else if (item instanceof pizzaRecipy) {
            skinNeeded = pizzaChef;
        } else if (item instanceof jacketPotato) {
            skinNeeded = jacketChef;
        } else if (item instanceof Potatoes) {
            skinNeeded = spudsChef;
        } else if (item instanceof Cheese) {
            skinNeeded = cheeseChef;
            System.out.println("apples");
        } else if (item instanceof pizzaDough) {
            if (inHandsIng.isPrepared() && inHandsIng.isCooked()) {
                skinNeeded = doneDough;
            } else if (inHandsIng.isPrepared()) {
                skinNeeded = rawDoughChef;
            } else {
                skinNeeded = newDough;
            }

        }
    }

    /**
     * Method to display the ingredient on the specific interactive tile objects (ChoppingBoard/Pan)
     *
     * @param batch the SpriteBatch used to render the texture.
     */

    public void displayIngStatic(SpriteBatch batch) {
        Gdx.app.log("", inHandsIng.toString());
        if (whatTouching != null && !chefOnChefCollision) {
            InteractiveTileObject tile = (InteractiveTileObject) whatTouching.getUserData();
            if (tile instanceof ChoppingBoard) {
                ChoppingBoard tileNew = (ChoppingBoard) tile;
                inHandsIng.create(tileNew.getX() - (0.5f / MainGame.PPM), tileNew.getY() - (0.2f / MainGame.PPM), batch);
                setChefSkin(null);
            } else if (tile instanceof Pan) {
                Pan tileNew = (Pan) tile;
                inHandsIng.create(tileNew.getX(), tileNew.getY() - (0.01f / MainGame.PPM), batch);
                setChefSkin(null);
            }
        }
    }

    /**
     * The method creates an instance of the recipe and sets its position on the completed station coordinates.
     * The method also implements a timer for each ingredient which gets removed from the screen after a certain amount of time.
     *
     * @param batch The batch used for drawing the sprite on the screen
     */

    public void displayIngDynamic(SpriteBatch batch) {
        putDownWaitTimer += 1 / 60f;
        previousInHandRecipe.create(completedStation.getX(), completedStation.getY() - (0.01f / MainGame.PPM), batch);
        if (putDownWaitTimer > nextOrderAppearTime) {
            previousInHandRecipe = null;
            putDownWaitTimer = 0;
        }
    }

    /**
     * This method updates the state of the chef when it is in a collision with another chef.
     * The method sets the userControlChef to false, meaning the user cannot control the chef while it's in collision.
     * It also sets the chefOnChefCollision to true, indicating that the chef is in collision with another chef.
     * Finally, it calls the setStartVector method to update the position of the chef.
     */
    public void chefsColliding() {
        b2body.setLinearVelocity(0f, 0f);
        //userControlChef = false;
        //chefOnChefCollision = true;
        //setStartVector();
    }

    /**
     * Set the starting velocity vector of the chef
     * when the chef collides with another chef
     */
    public void setStartVector() {
        startVector = new Vector2(b2body.getLinearVelocity().x, b2body.getLinearVelocity().y);
    }

    /**
     * Get the fixture that the chef is touching
     *
     * @return the fixture that the chef is touching
     */
    public Fixture getTouchingTile() {
        if (whatTouching == null) {
            return null;
        } else {
            return whatTouching;
        }
    }

    /**
     * Set the touching tile fixture
     *
     * @param obj fixture that the chef is touching
     */
    public void setTouchingTile(Fixture obj) {
        this.whatTouching = obj;
    }

    /**
     * Get the ingredient that the chef is holding
     *
     * @return the ingredient that the chef is holding
     */
    public Ingredient getInHandsIng() {
        return inHandsIng;
    }

    /**
     * Set the ingredient that the chef is holding
     *
     * @param ing the ingredient that the chef is holding
     */
    public void setInHandsIng(Ingredient ing) {
        inHandsIng = ing;
        inHandsRecipe = null;
    }

    /**
     * Get the recipe that the chef is holding
     *
     * @return the recipe that the chef is holding
     */
    public Recipe getInHandsRecipe() {
        return inHandsRecipe;
    }

    /**
     * Set the recipe that the chef is holding
     *
     * @param recipe the recipe that the chef is holding
     */
    public void setInHandsRecipe(Recipe recipe) {
        inHandsRecipe = recipe;
        inHandsIng = null;
    }

    /**
     * Returns a boolean value indicating whether the chef is under user control.
     * If not specified, returns false.
     *
     * @return userControlChef The boolean value indicating chef's control.
     */
    public boolean getUserControlChef() {
        return userControlChef || false;
    }

    /**
     * Set the chef's control by the user
     *
     * @param value whether the chef is controlled by the user
     */
    public void setUserControlChef(boolean value) {
        userControlChef = value;

    }

    /**
     * Drops the given ingredient on a plate station.
     *
     * @param station The plate station to drop the ingredient on.
     * @param ing     The ingredient to be dropped.
     */

    public void dropItemOn(InteractiveTileObject station, Ingredient ing) {
        if (station instanceof PlateStation) {
            ((PlateStation) station).dropItem(ing);
        }
        setInHandsRecipe(null);
    }

    /**
     * Drops the in-hand recipe on a completed dish station and saves the previous in-hand recipe.
     *
     * @param station The completed dish station to drop the recipe on.
     */
    public void dropItemOn(InteractiveTileObject station) {
        if (station instanceof CompletedDishStation) {
            previousInHandRecipe = getInHandsRecipe();
            completedStation = (CompletedDishStation) station;
        }
        setInHandsRecipe(null);
    }

    /**
     * Picks up an item from a plate station and sets it as in-hand ingredient or recipe.
     *
     * @param station The plate station to pick up the item from.
     */
    public void pickUpItemFrom(InteractiveTileObject station) {
        if (station instanceof PlateStation) {
            PlateStation pStation = (PlateStation) station;
            Object item = pStation.pickUpItem();
            if (item instanceof Ingredient) {
                setInHandsIng((Ingredient) item);
                setChefSkin(item);
            } else if (item instanceof Recipe) {
                setInHandsRecipe(((Recipe) item));
                setChefSkin(item);
            }
        }
    }

    public float getNotificationY() {
        return notificationY;
    }

    public void setNotificationY(float notificationY) {
        this.notificationY = notificationY;

    }

    public float getNotificationX() {
        return notificationX;
    }

    public void setNotificationX(float notificationX) {
        this.notificationX = notificationX;
    }

    public Body getB2body() {
        return b2body;
    }

    public void setCookingSpeedModifier(double cookingSpeedModifier) {
        this.cookingSpeedModifier = cookingSpeedModifier;
        System.out.println("Cooking speed increase");
    }

    public void handleSprite(InteractiveTileObject tile, String tileName) {
        switch (tileName) {
            case "Sprites.TomatoStation":
                TomatoStation tomatoTile = (TomatoStation) tile;
                setInHandsIng(tomatoTile.getIngredient());
                setChefSkin(getInHandsIng());
                break;
            case "Sprites.BunsStation":
                BunsStation bunTile = (BunsStation) tile;
                setInHandsIng(bunTile.getIngredient());
                setChefSkin(getInHandsIng());
                break;
            case "Sprites.OnionStation":
                OnionStation onionTile = (OnionStation) tile;
                setInHandsIng(onionTile.getIngredient());
                setChefSkin(getInHandsIng());
                break;
            case "Sprites.SteakStation":
                SteakStation steakTile = (SteakStation) tile;
                setInHandsIng(steakTile.getIngredient());
                setChefSkin(getInHandsIng());
                break;
            case "Sprites.LettuceStation":
                LettuceStation lettuceTile = (LettuceStation) tile;
                setInHandsIng(lettuceTile.getIngredient());
                setChefSkin(getInHandsIng());
                break;
            case "Sprites.potatoesStation":
                System.out.println("giving myself a spud");
                potatoesStation pots = (potatoesStation) tile;
                setInHandsIng(pots.getIngredient());
                setChefSkin(getInHandsIng());
                break;
            case "Sprites.cheeseStation":
                System.out.println("giving myself cheese");
                cheeseStation cheese = (cheeseStation) tile;
                setInHandsIng(cheese.getIngredient());
                setChefSkin(getInHandsIng());
                break;
            case "Sprites.pizzaDoughStation":
                System.out.println("giving myself pizza");
                pizzaDoughStation dough = (pizzaDoughStation) tile;
                setInHandsIng(dough.getIngredient());
                setChefSkin(getInHandsIng());
            case "Sprites.PlateStation":
                if (level.getPlateStation().getPlate().size() > 0 || level.getPlateStation().getCompletedRecipe() != null) {
                    pickUpItemFrom(tile);

                }

        }
    }

    @Override
    public boolean keyDown(int keycode) {
        level.resetIdleTimer();
        switch (keycode) {
            case Input.Keys.R:
                rest();
                break;
            case Input.Keys.W:
                yVelocity += 0.5f * runSpeedModifier;
                break;
            case Input.Keys.S:
                yVelocity -= 0.5f * runSpeedModifier;
                break;
            case Input.Keys.A:
                xVelocity -= 0.5f * runSpeedModifier;
                break;
            case Input.Keys.D:
                xVelocity += 0.5f * runSpeedModifier;
                break;
        }
        b2body.setLinearVelocity(xVelocity, yVelocity);
        if (b2body.getLinearVelocity().x > 0) {
            notificationSetBounds("Right");
        }
        if (b2body.getLinearVelocity().x < 0) {
            notificationSetBounds("Left");
        }
        if (b2body.getLinearVelocity().y > 0) {
            notificationSetBounds("Up");
        }
        if (b2body.getLinearVelocity().y < 0) {
            notificationSetBounds("Down");
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.S:
                yVelocity = 0;
                break;
            case Input.Keys.A:
            case Input.Keys.D:
                xVelocity = 0;
                break;
        }
        b2body.setLinearVelocity(xVelocity, yVelocity);
        return true;
    }

    public void rest() {
        active = false;
        xVelocity = 0;
        yVelocity = 0;
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

    public void setRunSpeedModifier(float v) {
        runSpeedModifier = v;
        System.out.println("Speed Up");

    }


    public enum State {UP, DOWN, LEFT, RIGHT}

}



