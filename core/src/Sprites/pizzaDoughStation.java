package Sprites;

import Ingredients.Cheese;
import Ingredients.Ingredient;
import Ingredients.pizzaDough;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
/**
 * pizzaDoughStation is a class that extends from InteractieTileObject that is used to represent a location from which the player cna pick up pizza dough
 * The station uses getIngredient() to show the deliver an Ingrideient.
 */
public class pizzaDoughStation extends InteractiveTileObject{

    public pizzaDoughStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
    }
    public Ingredient getIngredient(){
        return new pizzaDough(2,4, 9);
    }
}
