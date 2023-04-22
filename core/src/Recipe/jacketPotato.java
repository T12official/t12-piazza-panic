package Recipe;

import Ingredients.Cheese;
import Ingredients.Potatoes;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class jacketPotato extends Recipe {

    /**

     The jacketPotato class is a subclass of the Recipe class and represents a jacket potato dish in the kitchen game.
     It holds an ArrayList of ingredients needed to make a jacket potato and a Texture of the completed dish image.
     The ingredients in the ArrayList consist of a {@link Ingredients.Potatoes} and a {@link Ingredients.Cheese}.
     */

    public  jacketPotato(){
        super.ingredients = new ArrayList<>();
        ingredients.add(new Potatoes(0,0,0));
        ingredients.add(new Cheese(0,0,0));
        completedImg = new Texture("Food/Salad.png");



    }

    @Override
    public boolean equals(Object obj) {
        return ((Recipe) obj).getClass().equals(this.getClass());
    }

}
