package com.team13.piazzapanic;

public class EndlessScreen extends PlayScreen {
    /**
     * the EndlessScreen extends the player class as it implements the same game.
     * <p>
     * This overrides the updateOrder function to make it that the game will never out of out orders and complete the game.
     * It does this by setting the orderComplete property on orders to false, allowing them to be redone.
     */

    public EndlessScreen(MainGame game) {
        super(game);
        this.numberOfOrders = 10;
    }

    @Override
    public void updateOrder() {
        if (ordersArray.size() <= 1) {
            createOrder();
        }
        super.updateOrder();
    }
}
