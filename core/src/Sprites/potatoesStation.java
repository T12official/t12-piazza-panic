package Sprites;

import Ingredients.Ingredient;
import Ingredients.Potatoes;
import Ingredients.Steak;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * PotatoesStation is a class that extends from InteractieTileObject that is used to represent a location from which the player cna pick up potatoes
 * The station uses getIngredient() to show the deliver an Ingrideient.
 */
public class potatoesStation extends  InteractiveTileObject {
    public potatoesStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
    }
    public Ingredient getIngredient(){
        return new Potatoes(0,5, 11);
    }
}
