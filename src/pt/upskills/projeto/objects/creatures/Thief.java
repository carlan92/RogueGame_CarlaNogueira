package pt.upskills.projeto.objects.creatures;

import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Random;

public class Thief extends Enemy {
    private  static final int HEALTH = 4;

    public Thief(Position position) {
        super(position, HEALTH);
    }

    @Override
    public String getName() {
        return "Thief";
    }

    @Override
    public void update(Observable o, Object arg) {
        Random random = new Random();
        int random01 = random.nextInt(4);
        Integer keyCode = (Integer) arg;
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            System.out.println("random: " + random01);
            switch (random01) {
                case 0:
                    move(position.plus(new Vector2D(1, 1)));
                    break;
                case 1:
                    move(position.plus(new Vector2D(-1, -1)));
                    break;
                case 2:
                    move(position.plus(new Vector2D(1, -1)));
                    break;
                case 3:
                    move(position.plus(new Vector2D(-1, 1)));
                    break;
                default:
            }
        }
        deadEnemy();
    }
}
