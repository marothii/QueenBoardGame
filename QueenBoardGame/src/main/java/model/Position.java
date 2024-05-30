package model;

/**
 * Represents a position on a two-dimensional grid.
 */
public record Position(int row, int col) {

    /**
     * Checks if this position is equal to another object.
     *
     * @param object The object to compare to.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Position position = (Position) object;
        return row == position.row && col == position.col;
    }

    /**
     * Returns a string representation of this position.
     *
     * @return A string representing the row and column of this position.
     */
    @Override
    public String toString() {
        return "(" + this.row + "," + this.col + ")";
    }
}
