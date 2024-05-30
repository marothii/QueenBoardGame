import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class for launching the Queen Board Game application.
 * Extends the JavaFX Application class.
 */
public class QueenBoardGame extends Application  {

    /**
     * The entry point for the JavaFX application.
     * Loads the launch screen FXML file and sets up the primary stage.
     *
     * @param stage the primary stage for the application.
     * @throws IOException if an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
        stage.setTitle("Queen Board Game");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
