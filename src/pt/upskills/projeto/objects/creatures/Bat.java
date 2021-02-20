package pt.upskills.projeto.objects.creatures;

import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Random;

public class Bat extends Enemy {
    private static final int HEALTH = 3;

    public Bat(Position position) {
        super(position, HEALTH);
    }

    @Override
    public String getName() {
        return "Bat";
    }

    @Override
    public void update(Observable o, Object arg) {
        Random random = new Random();
        int random01 = random.nextInt(3);
        Integer keyCode = (Integer) arg;
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            if (random01 > 1) {
                move(position.plus(new Vector2D(1, 0)));
            } else if (random01 < 1) {
                move(position.plus(new Vector2D(-1, 0)));
            }
        }
        deadEnemy();
    }
}