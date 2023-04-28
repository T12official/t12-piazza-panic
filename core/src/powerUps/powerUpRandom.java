package powerUps;

import Sprites.Chef;
import Sprites.OrderTimer;
import java.util.Random;

public class powerUpRandom extends powerUpGeneric{
    private Random randomGenerator = new Random();
    @Override
    public void improveChef(Chef chefToModify, OrderTimer orderTimer){
        int randNumber = randomGenerator.nextInt(4);
        switch(randNumber){
            case 1:
                chefToModify.setRunSpeedModifier(1.5F);
                break;
            case 2:
                chefToModify.setCookingSpeedModifier(2);
                break;
            case 3:
                orderTimer.stopTimer((1));
                break;
            case 0:
                chefToModify.add1Life();
                break;

        }

        //
    }
}
