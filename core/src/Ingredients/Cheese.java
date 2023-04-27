package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
/**
 * The Cheese class represents a specific type of ingredient in the game, specifically cheese.
 * It extends the {@link Ingredient} class and has a preparation time and cooking time.
 * The cheese class sets up an ArrayList of textures for its different skins.
 */
public class Cheese extends Ingredient {
    public Cheese(float prepareTime, float cookTime, float burnTime) {
        super(prepareTime, cookTime, burnTime);
        super.tex = new ArrayList<>();
        super.tex.add(new Texture("Food/Onion.png"));
        super.tex.add(new Texture("Food/Chopped_onion.png"));
    }
}
