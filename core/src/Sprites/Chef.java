package Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.team13.piazzapanic.MainGame;
public class Chef extends Sprite {
    public World world;
    public Body b2body;

    private float initialX;
    private float initialY;


    public Vector2 startVector;
    private float chefCollisionTimer;
    public boolean chefCollision;
    private float chefWidth;
    private float chefHeight;
    private Texture normalChef;
    private Texture bunsChef;
    private Texture bunsToastedChef;
    private Texture burgerChef;
    private Texture lettuceChef;
    private Texture onionChef;
    private Texture tomatoChef;
    private Texture choppedLettuceChef;
    private Texture choppedOnionChef;
    private Texture choppedTomatoChef;
    private Texture pattyChef;
    private Texture completedBurgerChef;
    private Texture meatChef;
    public enum State {UP, DOWN, LEFT, RIGHT}
    public State currentState;
    private TextureRegion currentSkin;

    private Texture skinNeeded;


    //public Ingredient carrying;
    public Chef(World world, float startX, float startY) {
        initialX = startX;
        initialY = startY;

        normalChef = new Texture("Chef/Chef_normal.png");
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

        skinNeeded = normalChef;

        this.world = world;
        currentState = State.DOWN;

        defineChef();

        chefWidth =  13/MainGame.PPM;
        chefHeight =  20/MainGame.PPM;
        setBounds(0,0,chefWidth, chefHeight);
        chefCollision = false;
        chefCollisionTimer = 0;
        startVector = new Vector2(0,0);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        currentSkin= getSkin(dt);
        setRegion(currentSkin);

        if(chefCollision) {
            chefCollisionTimer += dt;
            b2body.setLinearVelocity(new Vector2(startVector.x * -1, startVector.y * -1));
            if (chefCollisionTimer > 0.3f) {
                chefCollision = false;
                chefCollisionTimer = 0;
            }
        }
    }

    private TextureRegion getSkin(float dt) {
        currentState = getState();

        TextureRegion region;
        switch(currentState) {
            case UP:
                region = new TextureRegion(skinNeeded, 0, 0, 33, 46);
                break;
            case DOWN:
                region = new TextureRegion(skinNeeded, 32, 0, 33, 46);
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

    public State getState() {
        if(b2body.getLinearVelocity().y > 0)
            return State.UP;
        if(b2body.getLinearVelocity().y < 0)
            return State.DOWN;
        if(b2body.getLinearVelocity().x > 0)
            return State.RIGHT;
        if(b2body.getLinearVelocity().x < 0)
            return State.LEFT;
        else
            return currentState;
    }

    public void defineChef(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(initialX / MainGame.PPM,initialY / MainGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        chefWidth =  3/MainGame.PPM;
        chefHeight =  6/MainGame.PPM;
        shape.setAsBox(chefWidth, chefHeight);

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void chefsColliding(){
        chefCollision = true;
        setStartVector();
    }

    public void setStartVector() {
        startVector = new Vector2(b2body.getLinearVelocity().x,b2body.getLinearVelocity().y);

    }
}
