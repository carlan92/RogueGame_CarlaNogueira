package pt.upskills.projeto.objects.room;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.GameObject;
import pt.upskills.projeto.rogue.utils.Position;

public class Floor extends GameObject {

    public Floor(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Floor";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
