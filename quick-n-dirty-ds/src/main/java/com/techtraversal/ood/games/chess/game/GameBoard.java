package com.techtraversal.ood.games.chess.game;

import com.techtraversal.ood.games.chess.piece.*;

import java.util.HashSet;
import java.util.Set;


public class GameBoard {

    private final Square[][] board;
    private Set<Piece> inPlay;
    private Set<Piece> evicted;

    public static GameBoard create() {
        return new GameBoard();
    }

    private GameBoard() {
        this.board   = new Square[8][8];
        this.inPlay  = new HashSet<>();
        this.evicted = new HashSet<>();
        populate();
    }

    private void populate() {
        int[] rows = { 0, 1, 6, 7 };
        int i = 0, r;
        while (i < rows.length) {
            r = rows[i];
            for (int c = 0; c < 8; c++) {
                if (r == 0)
                    spawnPiece(PieceColor.WHITE, r, c);
                else if (r == 1)
                    inPlay.add(Pawn.spawn(PieceColor.WHITE,
                            new Square(r, c)));
                else if (r == 6)
                    inPlay.add(Pawn.spawn(PieceColor.BLACK,
                            new Square(r, c)));
                else if (r == 7)
                    spawnPiece(PieceColor.BLACK, r, c);
            }
        }
    }

    private void spawnPiece(int col, int r, int c) {
        Square pos = new Square(r, c);
        if (c == 0 || c == 7)
            inPlay.add(Rook.spawn(col, pos));

        else if (c == 1 || c == 6)
            inPlay.add(Knight.spawn(col, pos));

        else if (c == 2 || c == 5)
            inPlay.add(Bishop.spawn(col, pos));

        else if (c == 3)
            inPlay.add(Queen.spawn(col, pos));

        else if (c == 4)
            inPlay.add(King.spawn(col, pos));
    }


    public Set<Piece> piecesInPlay() {
        return this.inPlay;
    }

    public Set<Piece> piecesEvicted() {
        return this.evicted;
    }

    public Square square(int r, int c) {
        return this.board[r][c];
    }
}
