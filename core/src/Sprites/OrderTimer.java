package Sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.team13.piazzapanic.HUD;
import com.team13.piazzapanic.MainGame;

/**
 * The order bar class is a class that extends actor. This is a class that creates a bar that decreases over time to represent a time limit to the player
 */

public class OrderTimer extends Actor {

    /**
     * @param texture - represents the red background of the timer bar
     * @param texture2 - represents the green portion of the bar that will decreasde over time
     * @param percentage - the percentage of the bar that is filed with green
     */

    private Texture texture;
    private Texture texture2;
    private float percentage = 1f;
    private float orderTime = 1;
    private float x = 105;
    private float y = 120;
    private float width = 50;
    private float height = 5;
    private Color color = Color.RED;
    private double difficulty = 10;

    public OrderTimer(){
        setColor(color);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        makeTexture((int)width, (int)height, color);
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    private void makeTexture(int width, int height, Color color){
        /**
         * This function uses a input width and height in order to create the required textures to render a order bar
         * It will use the global class variable percetnage to decide how wide texture2 should be
         */
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0,0,width,height);
        texture = new Texture(pixmap);
        pixmap.dispose();


        Pixmap pixmap2 = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap2.setColor(Color.GREEN);
        pixmap2.fillRectangle(0,0,(int)( width * percentage) ,height);
        texture2 = new Texture(pixmap2);
        pixmap2.dispose();
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        /**
        draws the sprite
         */
        Color color = getColor();
        //batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        Color newCol = Color.GREEN;
        //batch.setColor(newCol.r, newCol.g, newCol.b, color.a * parentAlpha);
         if (percentage > 0) {
             batch.draw(texture2, getX(), getY(), getWidth() * percentage, getHeight());
         }

    }

    public void setPercentage(float percent){
        percentage = percent;
    }

    public float getPercentage() {
        return percentage;
    }

    public float getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(float orderTime) {
        this.orderTime = orderTime;
    }

    public void render(HUD hud, MainGame game){
        hud.stage.addActor(this);
        draw(game.batch, 1);
    }

    /**
     * This if statement is used to track how long a player still has left to complete an order
     * orderTimer is used to keep track of this and is a variable that will hold a value between 1 and 0
     * where 1 is full time and 0 is out of time
     * @param dt
     */
    public void update(float dt) {
        if (orderTime > 0){
            orderTime -= dt / difficulty;
        }
        else {
            orderTime = 0;
        }
        setPercentage(orderTime);
    }

    public void stopTimer(float x) {
        // The timer will stop for x seconds.
        // The timer will not decrease during this time.
        setOrderTime(1);
        System.out.println("Added order time");

    }
}

