package powerUps;

import Sprites.Chef;
import Sprites.OrderTimer;
import java.util.Random;
import com.team13.piazzapanic.HUD;

public class powerUpRandom extends powerUpGeneric{
    private Random randomGenerator = new Random();
    @Override
    public void improveChef(Chef chefToModify, OrderTimer orderTimer, HUD hud){
        int randNumber = randomGenerator.nextInt(5);
        switch(randNumber){
            case 1:
                hud.updatePowerUp(1);
                chefToModify.setRunSpeedModifier(1.5F);
                break;
            case 2:
                hud.updatePowerUp(2);
                chefToModify.setCookingSpeedModifier(2);
                break;
            case 3:
                hud.updatePowerUp(3);
                orderTimer.stopTimer((1));
                break;
            case 4:
                hud.updatePowerUp(4);
                hud.addRep();
                break;
            case 0:
                hud.updatePowerUp(0);
                hud.doublePoints();
                break;

        }

        //
    }
}
