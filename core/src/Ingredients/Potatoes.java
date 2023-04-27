package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
/**
 * The Potatoes class represents a specific type of ingredient in the game, specifically Potatoes.
 * It extends the {@link Ingredient} class and has a preparation time and cooking time.
 * The Potaotes class sets up an ArrayList of textures for its different skins.
 */
public class Potatoes extends Ingredient {
    public Potatoes(float prepareTime, float cookTime, float burnTime) {
        super(prepareTime, cookTime, burnTime);
        super.tex = new ArrayList<>();
        super.tex.add(new Texture("Food/Meat.png"));
        super.tex.add(new Texture("Food/Patty.png"));
        super.tex.add(new Texture("Food/Cooked_patty.png"));
        super.tex.add(new Texture("Food/burned_patty.png"));
        super.setPrepared();
    }
}

