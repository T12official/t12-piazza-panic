package com.team13.piazzapanic;
import java.util.Random;

public class endlessMode extends PlayScreen{
    /**
     * the endlessMode extends the player class as it implements the same game
     *
     * The endlessKode class overrides the updateOrder function to make it that the game will never rtun of out orders and complete the game
     * it does this by setting the orderComplete property on orders to false allowing them to be doie again

     */
    public endlessMode(MainGame game) {
        super(game);
    }

    @Override
    public void updateOrder(){
        if(scenarioComplete==Boolean.TRUE) {


            hud.updateScore(Boolean.TRUE, (6 - ordersArray.size()) * 35);
            hud.updateOrder(Boolean.TRUE, 0);
            return;
        }
        if(ordersArray.size() != 0) {
            int ord = randInt(0,ordersArray.size() - 1);
            ordersArray.get(ord).orderComplete = false;
            if (ordersArray.get(ord).orderComplete) {

                hud.updateScore(Boolean.FALSE, ord * 35);
                //ordersArray.remove(0);
                hud.updateOrder(Boolean.FALSE, ord);
                return;
            }
            ordersArray.get(0).create(trayX, trayY, game.batch);
        }
    }

    public static int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        //
        // In particular, do NOT do 'Random rand = new Random()' here or you
        // will get not very good / not very random results.
        Random randoooo = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = randoooo.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
