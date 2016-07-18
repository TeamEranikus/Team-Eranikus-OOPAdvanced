package escape.code.core;

import escape.code.controllers.PuzzlesController;
import escape.code.models.PuzzleRectangle;
import escape.code.models.Sprite;
import escape.code.models.User;
import escape.code.services.puzzleRectangleService.PuzzleRectangleService;
import escape.code.services.puzzleRectangleService.PuzzleRectangleServiceImpl;
import escape.code.utils.Constants;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Engine {

    // private PuzzleManager puzzleManager;
    private HashMap<KeyCode, Boolean> keys;
    private ObservableMap<String, Object> objectsInCurrentScene;
    private ArrayList<Rectangle> rectCollision;
    private Rectangle currentPuzzle;
    private boolean hasCol = false;
    private boolean hasToSetPuzzle = true;
    private Sprite sprite;
    private StageManager stageManager;
    private Stage currentLoadedStage;
    private FXMLLoader loader;
    private User user;
    private PuzzleRectangleService puzzleRectangleService;
    FXMLLoader puzzleLoader;


    public Engine(FXMLLoader loader, User user) {
        this.loader = loader;
        this.user = user;
        this.initialize();
    }

    private void initialize() {
        // this.puzzleManager = new PuzzleManager();
        Scene scene = ((Pane) this.loader.getRoot()).getScene();
        this.currentLoadedStage = (Stage) (scene.getWindow());
        this.keys = new HashMap<>();
        this.objectsInCurrentScene = loader.getNamespace();
        this.rectCollision = new ArrayList<>();
        this.stageManager = new StageManager();
        ImageView playerImage = (ImageView) this.loader.getNamespace().get("imagePlayer");
        ResizableCanvas canvas = (ResizableCanvas) this.loader.getNamespace().get("mainCanvas");
        this.sprite = new Sprite(playerImage, canvas);
        this.currentPuzzle = getCurrentPuzzleRectangle();
        this.loadRectanglesCollision();
        this.puzzleRectangleService = new PuzzleRectangleServiceImpl();
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        //puzzleManager.load(user.getLevel());
    }

    public void play() throws IllegalStateException {
        updateSpriteCoordinates();
        hasCol = checkForCol(currentPuzzle);
        if (hasCol) {
            setCurrentPuzzle();

            sprite.getImageView().setLayoutX(480.0);
            sprite.getImageView().setLayoutY(300.0);

        }
        if (user.getPuzzleRectangle().getPuzzle().isAnswerGiven()) {
            //currentPuzzle.setVisible(false);
            currentPuzzle.setDisable(true);
            long puzzleRectangleId = user.getPuzzleRectangle().getId();
            PuzzleRectangle puzzle = this.puzzleRectangleService.getOneById(puzzleRectangleId + 1); //TODO
            this.user.setPuzzleRectangle(puzzle);
            currentPuzzle = getCurrentPuzzleRectangle();
            hasToSetPuzzle = true;
        }
    }

    private void setCurrentPuzzle() throws IllegalStateException {
        if (!currentPuzzle.getId().contains("door")) {

            if (hasToSetPuzzle) {
                hasToSetPuzzle = false;

                PuzzlesController.setPuzzle(user.getPuzzleRectangle().getPuzzle());
                //TODO user.setItem(Item item)
                //TODO pop-up
                //this.setItemCount("bookOne");
            }
            puzzleLoader = stageManager.loadSceneToPrimaryStage(new Stage(), Constants.PUZZLE_FXML_PATH);
            keys.clear();
        } else {
            currentLoadedStage.close();
            PuzzleRectangle puzzle = user.getPuzzleRectangle();
            user.setPuzzleRectangle(this.puzzleRectangleService.getOneById(puzzle.getId() + 1));
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


    private void loadRectanglesCollision() {
        for (String id : this.objectsInCurrentScene.keySet()) {
            if (id.endsWith("Col")){
                Rectangle current = (Rectangle)this.objectsInCurrentScene.get(id);
                rectCollision.add(current);
            }

        }

    }

    private Rectangle getCurrentPuzzleRectangle() {
        PuzzleRectangle puzzleRectangle = user.getPuzzleRectangle();
        Rectangle current = (Rectangle) this.objectsInCurrentScene.get(puzzleRectangle.getName());
        current.setVisible(false);
        return current;
    }

    private boolean checkForCol(Rectangle current) {
        if (current.isDisabled()) {
            return false;
        }
        return current.getBoundsInParent().intersects(sprite.getImageView().getBoundsInParent());
    }

    private void setItemCount(String itemName) {
        ObservableList<Node> listOfAllElements = ((Pane) loader.getRoot()).getChildren();
        for (Node field : listOfAllElements) {
            if (field.getId().equals(itemName)) {

                field.setVisible(true);
            }
        }
    }

}
