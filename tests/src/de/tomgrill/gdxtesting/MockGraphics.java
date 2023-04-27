package de.tomgrill.gdxtesting;

public class MockGraphics extends com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics {
    @Override
    public int getWidth() {
        return 1920;
    }

    @Override
    public int getHeight() {
        return 1080;
    }
}
