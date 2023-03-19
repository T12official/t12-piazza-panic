package Recipe;

import Ingredients.Cheese;
import Ingredients.Potatoes;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class jacketPotato extends Recipe {
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
