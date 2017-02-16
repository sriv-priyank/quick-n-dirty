package com.techtraversal.ood.games.chess.game;

public class Square {

    private final int row;
    private final int col;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int row() {  return row; }
    public int col() {  return col; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Square square = (Square) o;
        return (row == square.row) && (col == square.col);
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }
}
