package powerUps;

import Sprites.Chef;
import Sprites.OrderTimer;

/**
 * This class extends powerUpGeneric. It adds time to the timer
 */

public class addOrderTimer extends powerUpGeneric{
    @Override
    public void improveChef(Chef chefToModify , OrderTimer orderTimer) {orderTimer.stopTimer((1));}
}
