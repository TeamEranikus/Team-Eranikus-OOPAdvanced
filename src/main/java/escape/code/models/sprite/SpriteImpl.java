package escape.code.models.sprite;

import escape.code.core.ResizableCanvas;
import escape.code.utils.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;

public class SpriteImpl implements Sprite {

    private final Image RIGHT_IMAGE_VIEW = new Image(getClass().getResource(Constants.SPRITE_IMAGE_RIGHT_PATH).toExternalForm());
    private final Image LEFT_IMAGE_VIEW = new Image(getClass().getResource(Constants.SPRITE_IMAGE_LEFT_PATH).toExternalForm());
    private ImageView imageView;
    private ResizableCanvas currentCanvas;

    public SpriteImpl(ImageView image, ResizableCanvas canvas) {
        this.imageView = image;
        this.currentCanvas = canvas;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public void updateSpriteCoordinates(HashMap<KeyCode, Boolean> keys, ArrayList<Rectangle> rectCollision) {
        if (this.isPressed(KeyCode.UP, keys)) {
            this.moveY(-2);
            this.checkBounds("U", rectCollision);
        } else if (this.isPressed(KeyCode.DOWN, keys)) {
            this.moveY(2);
            this.checkBounds("D", rectCollision);
        } else if (this.isPressed(KeyCode.RIGHT, keys)) {
            this.moveX(2);
            this.checkBounds("R", rectCollision);
        } else if (this.isPressed(KeyCode.LEFT, keys)) {
            this.moveX(-2);
            this.checkBounds("L", rectCollision);
        }
    }

    public boolean checkForCol(Rectangle current) {
        if (current.isDisabled()) {
            return false;
        }
        return current.getBoundsInParent().intersects(this.getImageView().getBoundsInParent());
    }

    private void moveX(int x) {
        boolean right = x > 0 ? true : false;
        for (int i = 0; i < Math.abs(x); i++) {
            if (right) {
                double rightBound = currentCanvas.getLayoutX() + currentCanvas.getWidth() -
                        (imageView.getX() + imageView.getFitWidth());
                imageView.setLayoutX(imageView.getLayoutX() + 1 > rightBound ?
                        rightBound : imageView.getLayoutX() + 1);
                imageView.setImage(RIGHT_IMAGE_VIEW);
            } else {
                double leftBound = currentCanvas.getLayoutX();
                imageView.setLayoutX(imageView.getLayoutX() - 1 < leftBound ?
                        leftBound : imageView.getLayoutX() - 1);
                imageView.setImage(LEFT_IMAGE_VIEW);
            }
        }
    }

    private void moveY(int y) {
        boolean down = y > 0 ? true : false;
        for (int i = 0; i < Math.abs(y); i++) {
            if (down) {
                double downBound = currentCanvas.getLayoutY() + currentCanvas.getHeight() -
                        (imageView.getY() + imageView.getFitHeight());
                imageView.setLayoutY(imageView.getLayoutY() + 1 > downBound ?
                        downBound : imageView.getLayoutY() + 1);
            } else {
                double upBound = currentCanvas.getLayoutY();
                imageView.setLayoutY(imageView.getLayoutY() - 1 < upBound ?
                        upBound : imageView.getLayoutY() - 1);
            }
        }
    }

    private boolean isPressed(KeyCode key, HashMap<KeyCode, Boolean> keys) {
        return keys.getOrDefault(key, false);
    }

    private void checkBounds(String direction, ArrayList<Rectangle> rectCollision) {
        for (Rectangle rectangle : rectCollision) {
            if (checkForCol(rectangle)) {
                switch (direction) {
                    case "U":
                        this.getImageView().setLayoutY(this.getImageView().getLayoutY() + 2);
                        break;
                    case "D":
                        this.getImageView().setLayoutY(this.getImageView().getLayoutY() - 2);
                        break;
                    case "R":
                        this.getImageView().setLayoutX(this.getImageView().getLayoutX() - 2);
                        break;
                    case "L":
                        this.getImageView().setLayoutX(this.getImageView().getLayoutX() + 2);
                        break;
                }
            }
        }
    }
}
