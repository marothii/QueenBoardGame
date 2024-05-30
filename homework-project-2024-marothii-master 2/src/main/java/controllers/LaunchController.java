package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

/**
 * Controller for the launch screen of the game.
 * Handles the input of player names and launches the game.
 */
public class LaunchController {

    @FXML
    private TextField playerNameTextField1;


    @FXML
    private TextField playerNameTextField2;

    /**
     * Launches the game with the player names entered in the text fields.
     * Switches the scene to the game board.
     *
     * @param actionEvent the event triggered by clicking the launch button.
     */
    public void launchGame(ActionEvent actionEvent){
        String playerName1 = playerNameTextField1.getText();
        String playerName2 = playerNameTextField2.getText();
        Logger.info(playerName1);
        Logger.info(playerName2);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
        try {
            Parent root = fxmlLoader.load();
            fxmlLoader.<GameController>getController().setPlayerName1(playerName1);
            fxmlLoader.<GameController>getController().setPlayerName2(playerName2);
            Logger.info(playerName1);
            Logger.info(playerName2);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }catch (IOException exception){
            Logger.info("An Error Occurred");
        }
        Logger.info("Player Name: " + playerName1);
    }
}
