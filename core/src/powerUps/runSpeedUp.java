package powerUps;

import Sprites.Chef;

public class runSpeedUp extends powerUpGeneric{
    @Override
    public void improveChef(Chef chefToModify){
        chefToModify.setRunSpeedModifier(1.5F);
        //
    }
}
