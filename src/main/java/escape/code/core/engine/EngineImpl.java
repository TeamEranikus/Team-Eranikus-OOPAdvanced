package escape.code.core.engine;

import com.google.inject.Inject;
import escape.code.controllers.PuzzleController;
import escape.code.core.ResizableCanvas;
import escape.code.core.StageManager;
import escape.code.enums.Item;
import escape.code.models.Puzzle;
import escape.code.models.PuzzleRectangle;
import escape.code.models.User;
import escape.code.models.sprite.Sprite;
import escape.code.models.sprite.SpriteImpl;
import escape.code.services.puzzleRectangleService.PuzzleRectangleService;
import escape.code.services.userService.UserService;
import escape.code.utils.Constants;
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

public class EngineImpl implements Engine {

    private static final int PUZZLE_INCREMENTER = 1;
    private static final int DEFAULT_SPRITE_X_POSITION = 480;
    private static final int DEFAULT_SPRITE_Y_POSITION = 300;
    private static final String END_LEVEL_RECTANGLE_ID = "door";
    private static final String END_GAME_RECTANGLE_ID = "exit";
    private static final String STOP_TIMER = "STOP";
    private static final String IMAGE_PLAYER_ID = "imagePlayer";
    private static final String CANVAS_ID = "mainCanvas";

    @Inject
    private static PuzzleRectangleService puzzleRectangleService;

    private UserService userService;
    private HashMap<KeyCode, Boolean> keys;
    private ObservableMap<String, Object> objectsInCurrentScene;
    private ArrayList<Rectangle> rectCollision;
    private Rectangle currentPuzzleRectangle;
    private boolean hasToSetPuzzle;
    private Sprite sprite;
    private StageManager stageManager;
    private Stage currentLoadedStage;
    private FXMLLoader loader;
    private User user;

    public EngineImpl(FXMLLoader loader, User user, UserService userService, StageManager stageManager) {
        this.userService = userService;
        this.loader = loader;
        this.user = user;
        this.stageManager = stageManager;
        this.hasToSetPuzzle = true;
        this.initialize();
    }


    public void play() throws IllegalStateException {
        this.sprite.updateSpriteCoordinates(this.keys, this.rectCollision);
        boolean hasCollision = this.sprite.checkForCol(this.currentPuzzleRectangle);
        Puzzle currentPuzzle = this.user.getPuzzleRectangle().getPuzzle();
        if (hasCollision) {
            this.setCurrentPuzzle();
            this.sprite.getImageView().setLayoutX(DEFAULT_SPRITE_X_POSITION);
            this.sprite.getImageView().setLayoutY(DEFAULT_SPRITE_Y_POSITION);
        }

        if (currentPuzzle.isAnswerGiven()) {
            this.currentPuzzleRectangle.setDisable(true);
            long puzzleRectangleId = this.user.getPuzzleRectangle().getId();
            PuzzleRectangle puzzle = puzzleRectangleService.getOneById(puzzleRectangleId + PUZZLE_INCREMENTER);
            this.user.setPuzzleRectangle(puzzle);
            this.currentPuzzleRectangle = getCurrentPuzzleRectangle();
            this.userService.updateUser(this.user);
            this.hasToSetPuzzle = true;
            setItem(currentPuzzle.getItem());
        }
    }

    private void initialize() {
        this.keys = new HashMap<>();
        this.rectCollision = new ArrayList<>();
        Scene scene = ((Pane) this.loader.getRoot()).getScene();
        this.currentLoadedStage = (Stage) (scene.getWindow());
        this.objectsInCurrentScene = loader.getNamespace();
        ImageView playerImage = (ImageView) this.objectsInCurrentScene.get(IMAGE_PLAYER_ID);
        ResizableCanvas canvas = (ResizableCanvas) this.objectsInCurrentScene.get(CANVAS_ID);
        this.sprite = new SpriteImpl(playerImage, canvas);
        this.currentPuzzleRectangle = this.getCurrentPuzzleRectangle();
        this.loadRectanglesCollision();
        scene.setOnKeyPressed(event -> this.keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> this.keys.put(event.getCode(), false));
    }

    private void setCurrentPuzzle() throws IllegalStateException {
        String currentPuzzleRectangleId = this.currentPuzzleRectangle.getId();
        if (!currentPuzzleRectangleId.contains(END_LEVEL_RECTANGLE_ID)
                && !currentPuzzleRectangleId.contains(END_GAME_RECTANGLE_ID)) {
            if (this.hasToSetPuzzle) {
                this.hasToSetPuzzle = false;
                Puzzle currentPuzzle = this.user.getPuzzleRectangle().getPuzzle();
                PuzzleController.setPuzzle(currentPuzzle);
            }
            stageManager.loadSceneToPrimaryStage(new Stage(), Constants.PUZZLE_FXML_PATH);
        }else if (currentPuzzleRectangleId.contains(END_GAME_RECTANGLE_ID)){
            this.userService.updateUser(user);
            //TODO here is the end of the game
            System.exit(0);
        } else {
            this.currentLoadedStage.close();
            PuzzleRectangle puzzle = user.getPuzzleRectangle();
            long puzzleId = puzzle.getId();
            this.user.setPuzzleRectangle(puzzleRectangleService.getOneById(puzzleId + PUZZLE_INCREMENTER));
            throw new IllegalStateException(STOP_TIMER);
        }
        keys.clear();
    }


    private void loadRectanglesCollision() {
        this.objectsInCurrentScene.keySet().stream().filter(id -> id.endsWith("Col")).forEach(id -> {
            Rectangle current = (Rectangle) this.objectsInCurrentScene.get(id);
            this.rectCollision.add(current);
        });

    }

    private Rectangle getCurrentPuzzleRectangle() {
        PuzzleRectangle puzzleRectangle = user.getPuzzleRectangle();
        Rectangle current = (Rectangle) this.objectsInCurrentScene.get(puzzleRectangle.getName());
        current.setVisible(false);
        return current;
    }

    private void setItem(Item currentItem) {
        if (!currentItem.name().equals("NONE")) {
            Node node = (Node) this.objectsInCurrentScene.get(currentItem.name());
            node.setVisible(currentItem.hasItem());
        }
    }
}