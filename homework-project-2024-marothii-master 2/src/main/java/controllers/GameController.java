package controllers;

import game.util.TwoPhaseMoveSelector;
import gameresult.TwoPlayerGameResult;
import gameresult.manager.TwoPlayerGameResultManager;
import gameresult.manager.json.JsonTwoPlayerGameResultManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.GameState;

import model.Position;
import org.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;


/**
 * Controller for the Queen Board Game.
 * Manages the game state, player interactions, and game board UI.
 */
public class GameController {

    /** The current state of the game. */
    GameState state;

    /** Selector for handling two-phase moves. */
    TwoPhaseMoveSelector<Position> selector;

    /** The start time of the game. */
    LocalDateTime gameStartTime;

    /** Manager for storing game results. */
    TwoPlayerGameResultManager gameResultManager;

    private  String playerName1;

    private  String playerName2;

    private  int numberOfSteps = 0;

    @FXML
    private Label playerNameLabel;

    @FXML
    private GridPane gameBoard;


    /**
     * Initializes the game controller.
     * Sets up the game board, initializes game state, and prepares for player interactions.
     */
    @FXML
    void initialize(){
        // Main entry point of the controller
        createGameBoard();
        state = new GameState();
        selector = new TwoPhaseMoveSelector<>(state);
        addQueenToStartingPosition();
        gameStartTime = LocalDateTime.now();
        gameResultManager = new JsonTwoPlayerGameResultManager(Path.of("queen-game-results.json"));
    }

    /**
     * Sets the name of player 1.
     *
     * @param playerName1 the name of player 1.
     */
    public void setPlayerName1(String playerName1) {
        Logger.info("Player Name ->: " + playerName1);
        this.playerName1 = playerName1;
        playerNameLabel.setText("Current Player:  " + playerName1);
    }

    /**
     * Sets the name of player 2.
     *
     * @param playerName2 the name of player 2.
     */
    public void setPlayerName2(String playerName2) {
        Logger.info("Player Name ->: " + playerName2);
        this.playerName2 = playerName2;
    }

    /**
     * Shows the high score table.
     *
     * @param actionEvent the action event triggered by the user.
     * @throws IOException if an I/O error occurs.
     */
    public void showHighScoreTable(ActionEvent actionEvent) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/highscores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    private void saveGameResult(){
        TwoPlayerGameResult gameResult = TwoPlayerGameResult
                .builder()
                .player1Name(playerName1)
                .player2Name(playerName2)
                .status(state.getStatus())
                .numberOfTurns(numberOfSteps)
                .created(ZonedDateTime.now())
                .duration(Duration.between(gameStartTime, LocalDateTime.now()))
                .build();
        try {
            this.gameResultManager.add(gameResult);
        }catch (IOException exception){
            Logger.error("An Error occurred saving game result! " + exception.getMessage());
        }

    }

    private void createGameBoard(){
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                StackPane square;
                if (i % 2 == 0){
                    square = j % 2 == 0 ? createSquare("purple-square"): createSquare("blue-square");
                }else {
                    square = j % 2 == 0 ? createSquare("blue-square"):  createSquare("purple-square");
                }
                gameBoard.add(square, i , j);
            }
        }
    }

    private StackPane createSquare(String cssClass) {
        var square = new StackPane();
        square.getStyleClass().add(cssClass);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    /**
     * Handles mouse click events on the game board.
     *
     * @param event the mouse event.
     */
    @FXML
    void handleMouseClick(MouseEvent event){
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var position = new Position(row, col);
        Logger.info("Clicked on square"  + position);
        handleSelectionAtPosition(position);
    }

    private void handleSelectionAtPosition(Position position){
        switch (selector.getPhase()){
            case SELECT_FROM -> {
                selector.select(position);
                Logger.info("Selected :" + position);

                if (selector.isInvalidSelection()){
                    Logger.error("Invalid Selection from :" + position + "!");
                }else {
                    // show queen movable positions
                    List<Position> movablePositions = state.getLegalMovesFor(position);
                    Logger.info(movablePositions);
                    movablePositions.forEach(pos -> {
                        StackPane pane = getBoardSquareAt(pos);
                        pane.getStyleClass().add("movable-square");
                    });
                }
            }
            case SELECT_TO -> {
                selector.select(position);
                if (selector.isInvalidSelection()){
                    Logger.error("Invalid Selection to :" + position + "!");
                }else {
                    selector.makeMove();

                    // remove the circle
                    removeMovableSquareClass();
                    removeCircle();
                    addQueenToPosition(position);
                    updateCurrentPlayer();
                }

            }
        }
    }

    private void updateCurrentPlayer(){
        switch (state.currentPlayer){
            case PLAYER_1 -> {
                playerNameLabel.setText("Current Player:  " + playerName1);
            }
            case PLAYER_2 -> {
                playerNameLabel.setText("Current Player:  " + playerName2);
            }
        }
        switch (state.getStatus()){
            case IN_PROGRESS -> {
                Logger.info("No Winner!");
            }
            case PLAYER_1_WINS -> {
                Logger.info(playerName1 + " wins!");
                saveGameResult();
            }

            case PLAYER_2_WINS ->  {
                Logger.info(playerName2 + " wins!");
                saveGameResult();
            }
        }
        numberOfSteps += 1;
    }

    private void addQueenToStartingPosition(){
        var queen = createCircle();
        GridPane.setHalignment(queen, HPos.CENTER);
        GridPane.setValignment(queen, VPos.CENTER);
        gameBoard.add(queen, state.queenPosition.col(),state.queenPosition.row());
    }

    private void addQueenToPosition(Position position){
        var queen = createCircle();
        GridPane.setHalignment(queen, HPos.CENTER);
        GridPane.setValignment(queen, VPos.CENTER);
        gameBoard.add(queen, position.col(),position.row());
    }

    private Circle createCircle(){
        Circle queen = new Circle(10);
        queen.setFill(Color.rgb(255,255,255));
        return queen;
    }

    private void removeCircle(){
        gameBoard.getChildren().removeIf(child -> child instanceof Circle);
    }

    private void removeMovableSquareClass(){
        List<Position> allPositions = this.
                getAllSquaresOnGameBoard()
                .stream()
                .map(child -> new Position(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)))
                .toList();

        allPositions.forEach(pos -> {
            StackPane pane = getBoardSquareAt(pos);
            pane.getStyleClass().remove("movable-square");
        });
    }

    private StackPane getBoardSquareAt(Position position){
        return getAllSquaresOnGameBoard()
                .stream()
                .filter(child -> {
                    Position childPos = new Position(GridPane.getRowIndex(child), GridPane.getColumnIndex(child));
                    return position.equals(childPos);
                })
                .findFirst()
                .get();
    }

    private List<StackPane> getAllSquaresOnGameBoard(){
        return gameBoard.getChildren().stream()
                .filter(child -> child instanceof StackPane)
                .map(child -> (StackPane) child)
                .toList();
    }

}
