package com.techtraversal.ood.games.chess.game;

import com.techtraversal.ood.games.chess.piece.PieceColor;

public class Gameplay {

    private final GameBoard gameGameBoard;
    private final Player[] players;

    private Gameplay() {
        this.gameGameBoard =  GameBoard.create();

        this.players = new Player[2];
        this.players[PieceColor.WHITE] = new Player("W", PieceColor.WHITE);
        this.players[PieceColor.BLACK] = new Player("B", PieceColor.BLACK);
    }

    public static void setUp() {
        new Gameplay();
    }
}
