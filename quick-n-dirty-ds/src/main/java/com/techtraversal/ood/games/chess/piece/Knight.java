package com.techtraversal.ood.games.chess.piece;

import com.techtraversal.ood.games.chess.game.Square;

import java.util.Collection;


public class Knight implements Piece {

    private final int color;
    private final Square position;

    private Knight(int col, Square pos) {
        this.color = col;
        this.position = pos;
    }

    public static Knight spawn(int col, Square pos) {
        return new Knight(col, pos);
    }

    @Override
    public Square position() {
        return this.position;
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public Collection<Square> moves() {
        return null;
    }
}
