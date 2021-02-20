package pt.upskills.projeto.objects.creatures;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

import java.awt.event.KeyEvent;
import java.util.Observable;

public class Skeleton extends Enemy {
    private static final int DISTANCE_TO_HERO = 2;
    private static final int HEALTH = 2;

    public Skeleton(Position position) {
        super(position, HEALTH);
    }

    @Override
    public String getName() {
        return "Skeleton";
    }

    @Override
    public void update(Observable o, Object arg) {
        int randomMove = (int) Math.floor(Math.random() * 11);

        Integer keyCode = (Integer) arg;
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            int distanceToHero = Engine.hero.getPosition().getDistance(this.position);
            if (distanceToHero <= DISTANCE_TO_HERO) {
                move(chaseHero());
            } else {
                if (randomMove < 3) {
                    move(position.plus(new Vector2D(1, 0)));
                }
                if (randomMove >= 3 && randomMove < 6) {
                    move(position.plus(new Vector2D(0, 1)));
                }
                if (randomMove >= 6 && randomMove < 9) {
                    move(position.plus(new Vector2D(-1, 0)));
                }
                if (randomMove >= 9 && randomMove < 11) {
                    move(position.plus(new Vector2D(0, -1)));
                }
            }
        }
        deadEnemy();
    }
}
