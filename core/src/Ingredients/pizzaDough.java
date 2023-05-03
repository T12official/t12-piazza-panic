package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/**
 * The pizzaDough class represents a specific type of ingredient in the game, specifically pizza dough.
 * It extends the {@link Ingredient} class and has a preparation time and cooking time.
 * The pizzaDough class sets up an ArrayList of textures for its different skins.
 */
public class pizzaDough extends Ingredient {
    public pizzaDough(float prepareTime, float cookTime, float burnTime) {
        super(prepareTime, cookTime, burnTime);
        super.tex = new ArrayList<>();
        super.tex.add(new Texture("Food/Meat.png"));
        super.tex.add(new Texture("Food/Patty.png"));
        super.tex.add(new Texture("Food/Cooked_patty.png"));
        super.tex.add(new Texture("Food/burned_patty.png"));
    }
}
