package com.techtraversal.ood.games.chess.game;

public class Player {

    private final String name;
    private final int color;

    public Player(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String name()    {   return this.name;   }

    public int color()      {    return color;      }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return color == ((Player) o).color;
    }

    @Override
    public int hashCode() {
        return color;
    }
}
