package powerUps;

import Sprites.Chef;
import Sprites.OrderTimer;

public class runSpeedUp extends powerUpGeneric{
    @Override
    public void improveChef(Chef chefToModify, OrderTimer orderTimer){
        chefToModify.setRunSpeedModifier(1.5F);
        //
    }
}
