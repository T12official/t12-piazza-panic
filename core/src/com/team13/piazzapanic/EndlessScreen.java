package com.team13.piazzapanic;

import java.util.Random;

public class EndlessScreen extends PlayScreen{
    /**
     * the endlessScreen extends the player class as it implements the same game
     *
     * The endlessKode class overrides the updateOrder function to make it that the game will never out of out orders and complete the game
     * it does this by setting the orderComplete property on orders to false allowing them to be doie again

     */

    public EndlessScreen(MainGame game) {
        super(game);
        this.numberOfOrders = 10;
    }

    @Override
    public void updateOrder(){
        if (ordersArray.size() <= 1){
            createOrder();
        }
        super.updateOrder();
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
