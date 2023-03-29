package powerUps;

import Sprites.Chef;

public class speedUpCooking extends powerUpGeneric{
    @Override
    public void improveChef(Chef chefToModify){
        chefToModify.setCookingSpeedModifier(2);
    }
}
