package powerUps;

import Sprites.Chef;
import Sprites.OrderTimer;

/**
 * This class extends powerUpGeneric. It implements a way to incease the speed at which a chef will cook food
 */

public class speedUpCooking extends powerUpGeneric{
    @Override
    public void improveChef(Chef chefToModify, OrderTimer orderTimer){
        chefToModify.setCookingSpeedModifier(2);
    }
}
