package com.techtraversal.ood.games.chess.piece;

import com.techtraversal.ood.games.chess.game.Square;

import java.util.Collection;

public interface Piece {

    int getColor();

    Square position();

    Collection<Square> moves();
}
