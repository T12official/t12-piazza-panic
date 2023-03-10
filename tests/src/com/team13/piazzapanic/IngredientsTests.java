package com.team13.piazzapanic;
import Ingredients.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class IngredientsTests {
    Bun bun = new Bun(2, 0);
    Lettuce lettuce = new Lettuce(2,0);
    Onion onion = new Onion(2,0);
    Steak steak = new Steak(2,3);
    Tomato tomato = new Tomato(2,0);
    @Test
    public void TestBunPrepare(){
        bun.setPrepared();
        assertTrue(bun.isPrepared());
    }
}
