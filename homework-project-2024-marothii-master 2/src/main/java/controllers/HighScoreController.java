package controllers;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import gameresult.manager.TwoPlayerGameResultManager;
import gameresult.manager.json.JsonTwoPlayerGameResultManager;

/**
 * Controller for the High Score table.
 * Displays the players with the most wins.
 */
public class HighScoreController {

    private static final int NUMBER_OF_ROWS_TO_SHOW = 5;

    @FXML
    private TableView<TwoPlayerGameResultManager.Wins> tableView;

    @FXML
    private TableColumn<TwoPlayerGameResultManager.Wins, String> playerName;

    @FXML
    private TableColumn<TwoPlayerGameResultManager.Wins, Integer> numberOfWins;

    @FXML
    private void initialize() throws IOException {
        playerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        numberOfWins.setCellValueFactory(new PropertyValueFactory<>("numberOfWins"));
        ObservableList<TwoPlayerGameResultManager.Wins> observableList = FXCollections.observableArrayList();
        observableList.addAll(new JsonTwoPlayerGameResultManager(Path.of("queen-game-results.json")).getPlayersWithMostWins(NUMBER_OF_ROWS_TO_SHOW));
        tableView.setItems(observableList);
    }

}