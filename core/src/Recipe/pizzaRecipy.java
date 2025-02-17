package Recipe;

import Ingredients.Cheese;
import Ingredients.Tomato;
import Ingredients.pizzaDough;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class pizzaRecipy extends Recipe {
    public pizzaRecipy() {
        super.ingredients = new ArrayList<>();
        ingredients.add(new pizzaDough(0, 0, 0));
        ingredients.add(new Cheese(0, 0, 0));
        ingredients.add(new Tomato(0, 0, 0));
        completedImg = new Texture("Food/pizzaDone.png");

    }

    @Override
    public boolean equals(Object obj) {
        return ((Recipe) obj).getClass().equals(this.getClass());
    }

}
