package escape.code.core;

import escape.code.models.Sprite;
import escape.code.models.User;
import escape.code.utils.Constants;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Engine {

    private PuzzleManager puzzleManager;
    private HashMap<KeyCode, Boolean> keys;
    private LinkedList<Rectangle> rectangles;
    private ArrayList<Rectangle> rectCollision;
    private Rectangle currentPuzzle;
    private boolean hasCol = false;
    private boolean hasToSetPuzzle = true;
    private Sprite sprite;
    private StageManager stageManager;
    private Stage currentLoadedStage;
    private FXMLLoader loader;
    private User user;


    public Engine(FXMLLoader loader, User user) {
        this.loader = loader;
        this.user = user;
        this.initialize();
    }

    private void initialize() {
        this.puzzleManager = new PuzzleManager();
        this.keys = new HashMap<>();
        this.rectangles = new LinkedList<>();
        this.rectCollision = new ArrayList<>();
        this.stageManager = new StageManager();
        ImageView playerImage = (ImageView) this.loader.getNamespace().get("imagePlayer");
        ResizableCanvas canvas = (ResizableCanvas) this.loader.getNamespace().get("mainCanvas");
        this.sprite = new Sprite(playerImage, canvas);
        this.loadRectanglesPuzzles();
        this.loadRectanglesCollision();

        Scene scene = ((Pane) this.loader.getRoot()).getScene();
        this.currentLoadedStage = (Stage) (scene.getWindow());
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        puzzleManager.load(user.getLevel());
    }

    public void play() throws IllegalStateException {
        updateSpriteCoordinates();
        hasCol = checkForCol(currentPuzzle);

        if (hasCol) {
            setCurrentPuzzle();

            sprite.getImageView().setLayoutX(480.0);
            sprite.getImageView().setLayoutY(300.0);

        }
        if (Constants.IS_ANSWER_CORRECT) {
            currentPuzzle.setVisible(false);
            currentPuzzle.setDisable(true);
            rectangles.removeFirst();
            if (this.rectangles.size() == 1){

                    this.setItemCount("keyOne");

            }
            currentPuzzle = getCurrentPuzzleRectangle();
            Constants.IS_ANSWER_CORRECT = false;
            hasToSetPuzzle = true;
        }
    }


    private void setCurrentPuzzle() throws IllegalStateException {
        if (!currentPuzzle.getId().contains("door")) {
            try {
                if (hasToSetPuzzle) {
                    hasToSetPuzzle = false;
                    puzzleManager.setPuzzle();
                    //TODO user.setItem(Item item)
                    //TODO pop-up
                    //this.setItemCount("bookOne");
                }
                stageManager.loadNewStage(Constants.PUZZLE_FXML_PATH);
                keys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            currentLoadedStage.close();
            throw new IllegalStateException("STOP");
        }
    }

    private void updateSpriteCoordinates() {
        if (isPressed(KeyCode.UP)) {
            sprite.moveY(-2);
            checkBounds("U");
        } else if (isPressed(KeyCode.DOWN)) {
            sprite.moveY(2);
            checkBounds("D");
        } else if (isPressed(KeyCode.RIGHT)) {
            sprite.moveX(2);
            checkBounds("R");
        } else if (isPressed(KeyCode.LEFT)) {
            sprite.moveX(-2);
            checkBounds("L");
        }
    }

    private void checkBounds(String direction) {
        for (Rectangle rectangle : rectCollision) {
            if (checkForCol(rectangle)) {
                switch (direction) {
                    case "U":
                        sprite.getImageView().setLayoutY(sprite.getImageView().getLayoutY() + 2);
                        break;
                    case "D":
                        sprite.getImageView().setLayoutY(sprite.getImageView().getLayoutY() - 2);
                        break;
                    case "R":
                        sprite.getImageView().setLayoutX(sprite.getImageView().getLayoutX() - 2);
                        break;
                    case "L":
                        sprite.getImageView().setLayoutX(sprite.getImageView().getLayoutX() + 2);
                        break;
                }
            }
        }
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    private void loadRectanglesPuzzles() {
        ObservableList<Node> listOfAllElements = ((Pane) loader.getRoot()).getChildren();
        for (Node element : listOfAllElements) {
            if (element != null && element.getId().endsWith("Puzzle")) {
                Rectangle current = (Rectangle) element;
                current.setDisable(true);
                current.setVisible(false);
                rectangles.add(current);
            }
        }

        rectangles = rectangles.stream()
                .sorted((a, b) -> a.getId().compareTo(b.getId()))
                .collect(Collectors.toCollection(LinkedList<Rectangle>::new));
        currentPuzzle = getCurrentPuzzleRectangle();
    }

    private void loadRectanglesCollision() {
        ObservableList<Node> listOfAllElements = ((Pane) loader.getRoot()).getChildren();
        for (Node element : listOfAllElements) {
            if (element != null && element.getId().endsWith("Col")) {
                Rectangle current = (Rectangle) element;
                rectCollision.add(current);
            }
        }

    }

    private Rectangle getCurrentPuzzleRectangle() {
        if (rectangles.size() > 0) {
            Rectangle current = rectangles.peekFirst();
         //   current.setVisible(true);
            current.setDisable(false);
            return current;
        }
        return new Rectangle();
    }

    private boolean checkForCol(Rectangle current) {
        if (current.isDisabled()) {
            return false;
        }
        return current.getBoundsInParent().intersects(sprite.getImageView().getBoundsInParent());
    }

    private void setItemCount(String itemName){
        ObservableList<Node> listOfAllElements = ((Pane) loader.getRoot()).getChildren();
        for (Node field : listOfAllElements) {
            if (field.getId().equals(itemName)){

                field.setVisible(true);
            }
        }
    }

}
