package pt.upskills.projeto.objects.room;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

import java.util.List;

public class Room {
    private final int id;
    private final List<ImageTile> tiles;
    private final Position heroPosition;

    public Room(int id, List<ImageTile> tiles, Position heroPosition) {
        this.id = id;
        this.tiles = tiles;
        this.heroPosition = heroPosition;
    }

    public int getId() {
        return id;
    }

    public List<ImageTile> getTiles() {
        return tiles;
    }

    public Position getHeroPosition() {
        return heroPosition;
    }

}