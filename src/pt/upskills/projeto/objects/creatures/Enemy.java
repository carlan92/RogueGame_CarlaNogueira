package pt.upskills.projeto.objects.creatures;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.ImageMatrixGUI;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.room.DoorOpen;
import pt.upskills.projeto.objects.room.DoorWay;
import pt.upskills.projeto.objects.room.Wall;
import pt.upskills.projeto.rogue.utils.Position;

import java.util.Observable;

public class Enemy extends MovingObject {
    private int healthPoints;

    public Enemy(Position position, int health) {
        super(position);
        this.healthPoints = health;
    }

    public void receiveDamage(int damage) {
        healthPoints = healthPoints - damage;
    }

    private int getHealthPoints() {
        return healthPoints;
    }

    public void deadEnemy() {
        if (getHealthPoints() <= 0) {
            ImageMatrixGUI.getInstance().deleteObserver(this);
            ImageMatrixGUI.getInstance().removeImage(this);
            Engine.getActualRoom().getTiles().remove(this);
            ImageMatrixGUI.getInstance().update();
        }
    }

    public Position chaseHero() {
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int heroX = Engine.hero.getPosition().getX();
        int heroY = Engine.hero.getPosition().getY();
        if (x < heroX) {
            x++;
        } else if (x > heroX) {
            x--;
        }
        if (y < heroY) {
            y++;
        } else if (y > heroY) {
            y--;
        }
        return new Position(x, y);
    }

    public void move(Position position) {
        for (ImageTile tile : Engine.getActualRoom().getTiles()) {
            if (position.equals(tile.getPosition())) {
                if (tile instanceof Hero) {
                    Hero hero = (Hero) tile;
                    hero.setDamage();
                }
                if (tile instanceof MovingObject || tile instanceof Wall || tile instanceof DoorWay || tile instanceof DoorOpen) {
                    return;
                }
            }
        }
        this.position = position;
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    @Override
    public String getName() {
        return null;
    }
}
