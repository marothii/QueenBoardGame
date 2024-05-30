import game.State;
import model.GameState;
import model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    GameState state;

    @BeforeEach
    void beforeEach(){
        state = new GameState();
    }

    @Test
    void testIsLegalMoveFrom() {
        state.queenPosition = new Position(0, 3);

        // queen position
        boolean actual = state.isLegalToMoveFrom(new Position(0,3));
        // random position
        boolean actual2 = state.isLegalToMoveFrom(new Position(2,3));
        // goal position
        boolean actual3 = state.isLegalToMoveFrom(new Position(7,0));

        assertTrue(actual);
        assertFalse(actual2);
        assertFalse(actual3);
    }

    @Test
    void testIsLegalMove() {
        state.queenPosition = new Position(0, 3);

        boolean actual = state.isLegalMove(new Position(0,3) , new Position(1,3));
        // random position not part of movable positions
        boolean actual2 = state.isLegalMove(new Position(0,3),new Position(1,5));

        assertTrue(actual);
        assertFalse(actual2);
    }
    @Test
    void testGetLegalMoveFor() {


        List<Position> actual = state.getLegalMovesFor(new Position(0,3 ));
        List<Position> expected = List.of(
                new Position(0, 2),
                new Position(1,3),
                new Position(1, 2)
        );

        assertEquals(expected, actual);
    }
    @Test
    void testMakeMoveValid() {
        Position initialQueenPosition = new Position(0,3);
        state.queenPosition = initialQueenPosition;
        state.currentPlayer = State.Player.PLAYER_1;

        // move queen to (0,2)
        state.makeMove(new Position(0,3), new Position(0,2));

        assertEquals(new Position(0,2), state.queenPosition);
        assertEquals(State.Player.PLAYER_2, state.currentPlayer);
    }

    @Test
    void testMakeMoveInvalid() {
        Position initialQueenPosition = new Position(0,3);
        // move queen to (0,5)
        state.queenPosition = initialQueenPosition;
        state.currentPlayer = State.Player.PLAYER_1;
        state.makeMove(new Position(0,3), new Position(0,5));

        assertEquals(initialQueenPosition, state.queenPosition);
        assertEquals(State.Player.PLAYER_1, state.currentPlayer);
    }

    @Test
    void testGetNextPlayer() {
        state.currentPlayer = State.Player.PLAYER_1;
        State.Player actual = state.getNextPlayer();

        assertEquals(State.Player.PLAYER_2, actual);
    }

    @Test
    void testIsGameOver() {
        state.queenPosition = new Position(0,3);
        boolean actual = state.isGameOver();

        assertFalse(actual);

        state.queenPosition = new Position(7,0);
        boolean actual2 = state.isGameOver();

        assertTrue(actual2);
    }

    @Test
    void testGetStatusValid() {
        state.queenPosition = new Position(7,0);
        state.currentPlayer = State.Player.PLAYER_1;

        State.Status actual = state.getStatus();

        assertEquals(State.Status.PLAYER_1_WINS, actual);
    }

    @Test
    void testGetStatusInValid() {
        state.queenPosition = new Position(6,0);
        state.currentPlayer = State.Player.PLAYER_1;

        State.Status actual = state.getStatus();

        assertEquals(State.Status.IN_PROGRESS, actual);
    }

}
