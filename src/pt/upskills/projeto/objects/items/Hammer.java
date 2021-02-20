package pt.upskills.projeto.objects.items;

import pt.upskills.projeto.objects.GameObject;
import pt.upskills.projeto.objects.room.RoomItem;
import pt.upskills.projeto.rogue.utils.Position;

public class Hammer extends RoomItem {

    public Hammer(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Hammer";
    }

}