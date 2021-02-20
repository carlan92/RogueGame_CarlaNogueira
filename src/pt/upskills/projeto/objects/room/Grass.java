package pt.upskills.projeto.objects.room;

import pt.upskills.projeto.objects.GameObject;
import pt.upskills.projeto.rogue.utils.Position;

public class Grass extends GameObject {

    public Grass(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Grass";
    }

}