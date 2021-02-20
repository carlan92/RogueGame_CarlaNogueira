package pt.upskills.projeto.objects.topBar;

import pt.upskills.projeto.objects.GameObject;
import pt.upskills.projeto.rogue.utils.Position;

public class Black extends GameObject {

    public Black(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Black";
    }
}