package pt.upskills.projeto.objects.room;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.ImageMatrixGUI;
import pt.upskills.projeto.objects.GameObject;
import pt.upskills.projeto.rogue.utils.Position;

import java.util.Observable;
import java.util.Observer;

public abstract class RoomItem extends GameObject implements Observer {
    private boolean picked = false;

    public RoomItem(Position position) {
        super(position);
    }

    public void pick() {
        picked = true;
    }

    public void update(Observable o, Object arg) {
        if (picked) {
            ImageMatrixGUI.getInstance().removeImage(this);
            Engine.getActualRoom().getTiles().remove(this);
        }
    }
}

