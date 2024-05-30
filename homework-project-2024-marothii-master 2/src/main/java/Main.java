import javafx.application.Application;
import game.console.TwoPhaseMoveGame;
import lombok.extern.java.Log;
import model.GameState;
import model.Position;
import org.tinylog.Logger;

import java.util.Scanner;
import java.util.function.Function;

/**
 * Main class to launch the Queen Board Game application.
 * Calls the launch() method of the JavaFX Application class.
 */
public class Main {

    /**
     * Main method to launch the application.
     * Calls the launch() method of the JavaFX Application class with the QueenBoardGame class.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        startGameWithGUI();
    }

    static  void startGameWithGUI(){Application.launch(QueenBoardGame.class);
    }

    static void startGameWithConsole(){
        GameState gameState = new GameState();
        var parser = new Function<String, Position>() {

          //  String s = "7 0";
            @Override
            public Position apply(String s) {
                s = s.trim();
                if (!s.matches("\\d+\\s+\\d+")) {
                    throw new IllegalArgumentException();
                }
                var scanner = new Scanner(s);
                return new Position(scanner.nextInt(), scanner.nextInt());
            }
        };
        try {
            TwoPhaseMoveGame<Position> game = new TwoPhaseMoveGame<>(gameState, parser);
            game.start();
        }catch (Exception e){
            Logger.error(e.getMessage());
        }

    }
}

