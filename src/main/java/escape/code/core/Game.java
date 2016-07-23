package escape.code.core;

import com.google.inject.Inject;
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

    @Inject
    private static UserService userService;

    private static User user;
    private static Engine engine;
    private static FXMLLoader fxmlLoader;
    private static StageManager stageManager;
    private static AnimationTimer timeline;
    private static Stage currentStage;
    private static MediaPlayer mediaPlayer;

     public static void initialize(Stage stage){
         currentStage = stage;
         stageManager = new StageManager();
         login();
     }

    public static void run() {
        fxmlLoader = stageManager.loadSceneToPrimaryStage(currentStage, Level.getByNum(user.getLevel()).getPath());
        engine = new Engine(fxmlLoader, user, userService);
        playAudioClip();
        timeline = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    engine.play();
                } catch (IllegalStateException e) {
                    mediaPlayer.stop();
                    user.setLevel((user.getLevel() + 1) % 2);
                    //TODO pop - up to ask do you want to continue
                    fxmlLoader = stageManager.loadSceneToPrimaryStage(currentStage, Level.getByNum(user.getLevel()).getPath());
                    userService.updateUser(user);
                    engine = new Engine(fxmlLoader, user, userService);
                    mediaPlayer.play();
                } catch (NullPointerException ex) {
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

    private static void playAudioClip() {

        File file = new File(Constants.SOUNDS_PATH);
        String MEDIA_URL = file.toURI().toString();
        Media media = new Media(MEDIA_URL);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}
