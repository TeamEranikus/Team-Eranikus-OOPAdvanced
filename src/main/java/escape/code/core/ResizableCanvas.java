package escape.code.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Resize the game canvas
 */
public class ResizableCanvas extends Canvas {

    /**
     * Adds width and height property event listeners
     */
    public ResizableCanvas() {
        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    /**
     * Resize the canvas when width or height property get changed
     */
    private void draw() {
        double width = getWidth();
        double height = getHeight();
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);
    }

    /**
     * Check if the canvas can be resized
     * @return if the canvas is resizable
     */
    @Override
    public boolean isResizable() {
        return true;
    }

    /**
     * Gets canvas current width
     * @return canvas current width
     */
    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    /**
     * Gets canvas current height
     * @return canvas current height
     */
    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}

