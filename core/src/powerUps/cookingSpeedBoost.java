package powerUps;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.team13.piazzapanic.MainGame;

/**
 * cookingSpeedBoost is a badly named class. This class is used to render a token into the game screen that represents a power up collectable. It is
 * also  responsible for adding the power up token to the world so that it can be seen in the collsion detection system in worldConstactListener.
 */

public class cookingSpeedBoost extends Sprite {

    public TextureRegion textureRegion;
    public Body body;
    public powerUpGeneric powerUp;

    public cookingSpeedBoost(World world, TextureRegion textureRegion, float x, float y) {
        /**
         * This construction creates a collidable body and stores it to the class gloabal varibale body. It then ceeates the textures of the coin
         * Finally, it sets the X, Y location of the coin
         *
         * @param world - this is a reference to instance of world used in the game.Adding the body of this to the world allows for collision to
         *              be detected in worldContactListener
         * @param textureRegion - the textureRegion allows the input of what texture should be used to render the coin. This can allow for coins of different
         *                      powers to have different appearences, that make them distinguishable. (currently only one texture has ben made so all coins look the same)
         *
         * @param x - the x pos to render the coin
         * @param y - the y pos to render the coin
         */

        super(textureRegion);
        this.textureRegion = textureRegion;
        float chefWidth = 20 / MainGame.PPM;
        float chefHeight = 20 / MainGame.PPM;
        setBounds(x, y, chefWidth, chefHeight);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4.5f / MainGame.PPM);
        shape.setPosition(new Vector2(shape.getPosition().x + (0.5f / MainGame.PPM), shape.getPosition().y - (5.5f / MainGame.PPM)));
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public void render(SpriteBatch batch) {
        /**
         * Used to render the sprite itself
         */
        setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        draw(batch);

    }

    // Getter and setter methods for the body
    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void dispose() {
        getTexture().dispose(); // Dispose the texture used by the sprite
    }

    public void onContractCreated() {
        System.out.println("please resolve the contact");
    }

    public powerUpGeneric getPowerUp() {
        return powerUp;
    }

    public void setPowerUp(powerUpGeneric powerUp) {
        /**
         * This is a VERY IMPORTANT SETTER. This setter allows the developer to attatch a specific power up to a coin after it has been created.
         * if this setter is not used a powerUp token will not do anything
         */
        this.powerUp = powerUp;


    }

    public void DestroyBody() {
        for (Fixture a : body.getFixtureList()) {
            body.destroyFixture(a);
        }
    }


}
