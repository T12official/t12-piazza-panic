package powerUps;
import Sprites.Chef;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.team13.piazzapanic.MainGame;

public class cookingSpeedBoost extends Sprite {

    private World world;
    private TextureRegion textureRegion;
    private Body body;

    public cookingSpeedBoost(World world, TextureRegion textureRegion, float x, float y) {
        super(textureRegion);
        this.world = world;
        this.textureRegion = textureRegion;
        float chefWidth = 13 / MainGame.PPM;
        float chefHeight = 20 / MainGame.PPM;
        setBounds(0, 0, chefWidth, chefHeight);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0.4f,0.6f);
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

    public void onContractCreated(){
        System.out.println("please resolve the contact");
    }


}