package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Cheese extends Ingredient {
    public Cheese(float prepareTime, float cookTime, float burnTime) {
        super(prepareTime, cookTime, burnTime);
        super.tex = new ArrayList<>();
        super.tex.add(new Texture("Food/Onion.png"));
        super.tex.add(new Texture("Food/Chopped_onion.png"));
    }
}
