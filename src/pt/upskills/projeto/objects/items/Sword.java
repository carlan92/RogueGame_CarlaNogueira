package pt.upskills.projeto.objects.items;

import pt.upskills.projeto.objects.GameObject;
import pt.upskills.projeto.objects.room.RoomItem;
import pt.upskills.projeto.rogue.utils.Position;

public class Sword extends RoomItem {

    public Sword(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Sword";
    }

}