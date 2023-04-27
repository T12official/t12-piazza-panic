package powerUps;

import Sprites.Chef;
import Sprites.OrderTimer;

/**
 * This class extends powerUpGeneric. It implements a way to regain an extra life
 */

public class addLife extends powerUpGeneric{
    @Override
    public void improveChef(Chef chefToModify , OrderTimer orderTimer){chefToModify.add1Life();}
}