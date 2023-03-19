package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

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
