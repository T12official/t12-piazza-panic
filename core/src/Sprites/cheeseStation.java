package Sprites;

import Ingredients.Cheese;
import Ingredients.Ingredient;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class cheeseStation extends InteractiveTileObject {

    /**
     * cheeseStation is a class that extends from InteractieTileObject that is used to represent a location from which the player can pick up cheese.
     * The station uses getIngredient() to show the deliver an Ingrideient.
     */

    public cheeseStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
    }

    public Ingredient getIngredient() {
        return new Cheese(2, 0, 0);
    }
}
