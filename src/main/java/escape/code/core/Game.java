package escape.code.core;

import com.google.inject.Inject;
import escape.code.core.engine.Engine;
import escape.code.core.engine.EngineImpl;
import escape.code.enums.Level;
import escape.code.models.User;
import escape.code.services.userService.UserService;
import escape.code.utils.Constants;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class Game {
    private static final int LEVEL_INCREMENTER = 1;
    private static final int NUMBER_OF_LEVELS = Level.values().length;
    @Inject
    private static StageManager stageManager;

    @Inject
    private static UserService userService;

    private static User user;
    private static Engine engine;
    private static FXMLLoader fxmlLoader;
    private static AnimationTimer timeline;
    private static Stage currentStage;
    private static MediaPlayer mediaPlayer;

     public static void initialize(Stage stage){
         currentStage = stage;
         login();
     }

    public static void run() {
        fxmlLoader = stageManager.loadSceneToPrimaryStage(currentStage, Level.getByNum(user.getLevel()).getPath());
        engine = new EngineImpl(fxmlLoader, user, userService, stageManager);
        playAudio();
        timeline = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    engine.play();
                } catch (IllegalStateException e) {
                    mediaPlayer.stop();
                    user.setLevel((user.getLevel() + LEVEL_INCREMENTER) % NUMBER_OF_LEVELS);
                    //TODO pop - up to ask do you want to continue
                    fxmlLoader = stageManager.loadSceneToPrimaryStage(currentStage, Level.getByNum(user.getLevel()).getPath());
                    userService.updateUser(user);
                    engine = new EngineImpl(fxmlLoader, user, userService, stageManager);
                    mediaPlayer.play();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    timeline.stop();
                }
            }
        };
        timeline.start();
    }

    public static void setUser(User currentUser) {
        user = currentUser;
    }

    public static void loadMainMenu() {
        fxmlLoader = stageManager.loadSceneToPrimaryStage(currentStage, Constants.MENU_FXML_PATH);
    }

    private static void login() {
        fxmlLoader = stageManager.loadSceneToPrimaryStage(currentStage, Constants.LOGIN_FXML_PATH);
    }

    private static void playAudio() {
        File file = new File(Constants.SOUNDS_PATH);
        String MEDIA_URL = file.toURI().toString();
        Media media = new Media(MEDIA_URL);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}
