package escape.code.models;

import escape.code.core.ResizableCanvas;
import escape.code.utils.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {

    private final Image RIGHT_IMAGE_VIEW = new Image(getClass().getResource(Constants.SPRITE_IMAGE_RIGHT_PATH).toExternalForm());
    private final Image LEFT_IMAGE_VIEW = new Image(getClass().getResource(Constants.SPRITE_IMAGE_LEFT_PATH).toExternalForm());

    private ImageView imageView;
    private ResizableCanvas currentCanvas;

    public ImageView getImageView() {
        return imageView;
    }

    public Sprite(ImageView image, ResizableCanvas canvas) {
        this.imageView = image;
        this.currentCanvas = canvas;
    }

    public void moveX(int x) {

        boolean right = x > 0 ? true : false;
        for (int i = 0; i < Math.abs(x); i++) {
            if (right) {
                double rightBound = currentCanvas.getLayoutX() + currentCanvas.getWidth() - (imageView.getX() + imageView.getFitWidth());

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

    public void moveY(int y) {

        boolean down = y > 0 ? true : false;
        for (int i = 0; i < Math.abs(y); i++) {
            if (down) {

                double downBound = currentCanvas.getLayoutY() + currentCanvas.getHeight() - (imageView.getY() + imageView.getFitHeight());

                imageView.setLayoutY(imageView.getLayoutY() + 1 > downBound ?
                        downBound : imageView.getLayoutY() + 1);

            } else {

                double upBound = currentCanvas.getLayoutY();

                imageView.setLayoutY(imageView.getLayoutY() - 1 < upBound ?
                        upBound : imageView.getLayoutY() - 1);

            }
        }
    }
}
