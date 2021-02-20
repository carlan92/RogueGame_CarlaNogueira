package pt.upskills.projeto.objects.items;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.FireTile;
import pt.upskills.projeto.gui.ImageMatrixGUI;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.GameObject;
import pt.upskills.projeto.objects.creatures.Bat;
import pt.upskills.projeto.objects.creatures.Enemy;
import pt.upskills.projeto.objects.creatures.Skeleton;
import pt.upskills.projeto.objects.room.RoomItem;
import pt.upskills.projeto.objects.room.Wall;
import pt.upskills.projeto.rogue.utils.Position;

public class FireBall extends RoomItem implements FireTile {

    public FireBall(Position position) {
        super(position);
    }

    @Override
    public boolean validateImpact() {
        for (ImageTile tile : Engine.getActualRoom().getTiles()) {
            if (tile.getPosition().equals(getPosition())) {
                if (tile instanceof Wall) {
                    return false;
                }
                if (tile instanceof Enemy) {
                    ((Enemy) tile).receiveDamage(10);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "Fire";
    }


}
