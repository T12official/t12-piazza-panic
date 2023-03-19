package Sprites;

import Ingredients.Cheese;
import Ingredients.Ingredient;
import Ingredients.Steak;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class cheeseStation extends  InteractiveTileObject {

    public cheeseStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
    }
    public Ingredient getIngredient(){
        return new Cheese(2,0, 0);
    }
}
