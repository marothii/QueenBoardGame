package model;

import game.TwoPhaseMoveState;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents the state of the Queen Board Game.
 * Implements the TwoPhaseMoveState interface with Position objects.
 */
public class GameState implements TwoPhaseMoveState<Position> {

    /** The size of the board (8x8). */
    public static final int BOARD_SIZE = 8;

    /** The position of the queen on the board. */
    public  Position queenPosition = new Position(0,7);

    private final Position goalPosition = new Position(7,0);

    /** The current player in the game. */
    public Player currentPlayer = Player.PLAYER_1;

    /**
     * Checks if it is legal to move from a given position.
     *
     * @param position The position to move from.
     * @return True if it's legal to move from the given position, false otherwise.
     */
    @Override
    public boolean isLegalToMoveFrom(Position position) {
        return position.equals(queenPosition) && !position.equals(goalPosition);
    }

    /**
     * Checks if a move from one position to another is legal.
     *
     * @param from The position to move from.
     * @param to The position to move to.
     * @return True if the move is legal, false otherwise.
     */
    @Override
    public boolean isLegalMove(Position from, Position to) {
        List<Position> legalMoves = this.getQueenMovablePositions(from);
        return legalMoves.contains(to);
    }

    public List<Position> getLegalMovesFor(Position position){
        return this.getQueenMovablePositions(position);
    }

    /**
     * Makes a move from one position to another.
     *
     * @param from The position to move from.
     * @param to The position to move to.
     */
    @Override
    public void makeMove(Position from, Position to) {
        if (isLegalMove(from, to)){
            queenPosition = to;
            currentPlayer = getNextPlayer();
        }
    }

    /**
     * Gets the next player to make a move.
     *
     * @return The next player.
     */
    @Override
    public Player getNextPlayer() {
        return currentPlayer.opponent();
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    @Override
    public boolean isGameOver() {
        return queenPosition.equals(goalPosition);
    }

    /**
     * Gets the status of the game.
     *
     * @return The status of the game (IN_PROGRESS, PLAYER_1_WINS, or PLAYER_2_WINS).
     */
    @Override
    public Status getStatus() {
        boolean gameIsOver = isGameOver();
        if(!gameIsOver ){
            return Status.IN_PROGRESS;
        }
        else {
             if (currentPlayer == Player.PLAYER_1){
                 return Status.PLAYER_1_WINS;
             }
             return Status.PLAYER_2_WINS;
        }
    }

    /**
     * Gets the movable positions for the queen from a given position.
     *
     * @param position The position of the queen.
     * @return A list of movable positions.
     */
    private List<Position> getQueenMovablePositions(Position position){
        // (-1,-1) (0,-1) (-1,0)

        Position leftPosition = new Position(position.row(), position.col() -1 );
        Position downPosition = new Position(position.row() + 1 , position.col());
        Position diagonalPosition = new Position(position.row() + 1 , position.col() - 1);

        List<Position> movablePositions = new ArrayList<>();
        movablePositions.add(leftPosition);
        movablePositions.add(downPosition);
        movablePositions.add(diagonalPosition);


        movablePositions.removeIf(pos -> !this.isPositionOnBoard(pos));
        return movablePositions;
    }

    /**
     * Checks if a position is on the board.
     *
     * @param position The position to check.
     * @return True if the position is on the board, false otherwise.
     */
    private boolean isPositionOnBoard(Position position){
        Boolean isRowOnBoard = position.row() <= GameState.BOARD_SIZE && position.row() >= 0;
        Boolean isColOnBoard = position.col() <= GameState.BOARD_SIZE && position.col() >= 0;
        return isRowOnBoard && isColOnBoard;
    }
}
