package Tools;

import Sprites.Chef;
import Sprites.InteractiveTileObject;
import Sprites.OrderTimer;
import com.badlogic.gdx.physics.box2d.*;
import com.team13.piazzapanic.HUD;
import com.team13.piazzapanic.PlayScreen;
import powerUps.cookingSpeedBoost;

/**
 * WorldContactListener class is an implementation of the ContactListener interface.
 * It helps to manage the interactions between objects in the world.
 */
public class WorldContactListener implements ContactListener {
    OrderTimer timer;
    World myWorld;
    PlayScreen aa;
    HUD hud;

    public WorldContactListener(World world, PlayScreen a, OrderTimer timer, HUD hud) {
        this.myWorld = world;
        this.aa = a;
        this.timer = timer;
        this.hud = hud;
    }

    /**
     * The beginContact method is called when two fixtures start to collide.
     *
     * @param contact - The contact that has been made between two fixtures.
     */
    @Override
    public void beginContact(Contact contact) {
        // get the two fixtures that have collided
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // if either of the fixtures is the Chef object
        if (fixA.getUserData() instanceof Chef || fixB.getUserData() instanceof Chef) {
            // determine which fixture is the Chef
            Fixture chefFixt = fixA.getUserData() instanceof Chef ? fixA : fixB;
            Fixture objectFixt = fixA.getUserData() instanceof Chef ? fixB : fixA;

            // if both fixtures are Chef objects
            if (fixA.getUserData() instanceof Chef && fixB.getUserData() instanceof Chef) {
                // call the chefsColliding method on both Chef objects
                Chef chef = ((Chef) fixA.getUserData());
                Chef chefb = ((Chef) fixB.getUserData());
                chef.chefsColliding();
                chefb.chefsColliding();
            }
            // if the object fixture is an InteractiveTileObject
            else if (objectFixt.getUserData() != null && objectFixt.getUserData() instanceof InteractiveTileObject) {
                // set the InteractiveTileObject for the Chef
                ((Chef) chefFixt.getUserData()).setTouchingTile(objectFixt);
            }
        }

        if (fixA.getUserData() instanceof Chef && fixB.getUserData() instanceof cookingSpeedBoost || (fixB.getUserData() instanceof Chef && fixA.getUserData() instanceof cookingSpeedBoost)) {
            System.out.println("contact between chef and cookingSpeedBoost");
            if (fixA.getUserData() instanceof Chef) {
                cookingSpeedBoost a = (cookingSpeedBoost) fixB.getUserData();
                a.getPowerUp().improveChef((Chef) fixA.getUserData(), (timer), (hud));
                try {
                    aa.dispose = true;
                    aa.toKill = a;
                    aa.toKill.textureRegion.getTexture().dispose();
                    aa.toKill.textureRegion = null;
                    //a.body = null;
                    //a.powerUp = null;m
                    aa.powerUpArray.remove(0);
                    //myWorld.destroyBody(a.getBody());
                    //a.dispose();
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }
            } else {

                cookingSpeedBoost a = (cookingSpeedBoost) fixA.getUserData();
                a.getPowerUp().improveChef((Chef) fixB.getUserData(), (timer), (hud));
                try {
                    aa.dispose = true;
                    aa.toKill = a;
                    aa.toKill.textureRegion.getTexture().dispose();
                    aa.toKill.textureRegion = null;
                    //a.body = null;
                    //a.powerUp = null;
                    aa.powerUpArray.remove(0);

                    //myWorld.destroyBody(a.getBody());


                    //a.dispose();
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }
            }
        }
    }

    /**
     * The endContact method is called when two fixtures stop colliding.
     *
     * @param contact - The contact that has been made between two fixtures.
     */
    @Override
    public void endContact(Contact contact) {
        // get the two fixtures that have collided
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // if either of the fixtures is the Chef object
        if (fixA.getUserData() instanceof Chef || fixB.getUserData() instanceof Chef) {
            // determine which fixture is the Chef
            Fixture chefFixt = fixA.getUserData() instanceof Chef ? fixA : fixB;
            Fixture objectFixt = fixA.getUserData() instanceof Chef ? fixB : fixA;

            // if the object fixture is an InteractiveTileObject
            if (objectFixt.getUserData() != null && objectFixt.getUserData() instanceof InteractiveTileObject) {
                // remove the InteractiveTileObject for the Chef
                ((Chef) chefFixt.getUserData()).setTouchingTile(null);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub

    }
}
