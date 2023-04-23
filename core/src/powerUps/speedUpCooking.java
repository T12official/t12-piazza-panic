package powerUps;

import Sprites.Chef;

/**
 * This class extends powerUpGeneric. It implements a way to incease the speed at which a chef will cook food
 */

public class speedUpCooking extends powerUpGeneric{
    @Override
    public void improveChef(Chef chefToModify){
        chefToModify.setCookingSpeedModifier(2);
    }
}
