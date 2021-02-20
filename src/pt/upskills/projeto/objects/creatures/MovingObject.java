package pt.upskills.projeto.objects.creatures;

import pt.upskills.projeto.objects.GameObject;
import pt.upskills.projeto.rogue.utils.Position;

import java.util.Observer;

public abstract class MovingObject extends GameObject implements Observer {
    public MovingObject(Position position) {
        super(position);
    }

    public abstract void move(Position position);
}
