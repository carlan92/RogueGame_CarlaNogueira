package pt.upskills.projeto.objects.creatures;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.game.FireBallThread;
import pt.upskills.projeto.gui.ImageMatrixGUI;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.items.FireBall;
import pt.upskills.projeto.objects.items.GoodMeat;
import pt.upskills.projeto.objects.items.Hammer;
import pt.upskills.projeto.objects.items.Sword;
import pt.upskills.projeto.objects.room.DoorOpen;
import pt.upskills.projeto.objects.room.DoorWay;
import pt.upskills.projeto.objects.room.RoomItem;
import pt.upskills.projeto.objects.room.Wall;
import pt.upskills.projeto.rogue.utils.Direction;
import pt.upskills.projeto.rogue.utils.Position;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Hero extends MovingObject implements Observer {
    private Direction lastDir = Direction.RIGHT;
    private int heroHealthPoints = 8;
    private int numFireBalls = 0;
    private final List<RoomItem> inventory = new ArrayList<>();

    public Hero(Position position) {
        super(position);
    }

    public int getHealth() {
        return heroHealthPoints;
    }

    public int getNumFireBalls() {
        return numFireBalls;
    }

    public List<RoomItem> getInventory() {
        return inventory;
    }

    @Override
    public void move(Position newPosition) {
        for (ImageTile tile : Engine.getActualRoom().getTiles()) {
            if (newPosition.equals(tile.getPosition())) {
                if (tile instanceof RoomItem) {
                    if (tile instanceof GoodMeat) {
                        gainHealth();
                        RoomItem roomItem = (RoomItem) tile;
                        roomItem.pick();
                    } else if (tile instanceof FireBall) {
                        if (numFireBalls < 3) {
                            numFireBalls++;
                            RoomItem roomItem = (RoomItem) tile;
                            roomItem.pick();
                        }
                    } else {
                        if (inventory.size() < 3) {
                            RoomItem roomItem = (RoomItem) tile;
                            roomItem.pick();
                            inventory.add(roomItem);
                        }
                    }
                }
                if (tile instanceof Enemy) {
                    ((Enemy) tile).receiveDamage(2);
                }

                if (tile instanceof MovingObject || tile instanceof Wall) {
                    return;
                }
                if (tile instanceof DoorOpen) {
                    Engine.goToNextLevel();
                    return;
                }
                if (tile instanceof DoorWay) {
                    Engine.goToLowerLevel();
                    return;
                }
            }
        }
        setPosition(newPosition);
    }

    private void useItem(int itemPos) {
        if (inventory.size() >= itemPos) {
            RoomItem item = inventory.get(itemPos - 1);
            if (item instanceof Sword || item instanceof Hammer) {
                boolean attacked = attackEnemies();
                if (attacked) {
                    inventory.remove(item);
                }
            }
        }
    }

    private void gainHealth() {
        if (heroHealthPoints < 8) {
            heroHealthPoints++;
        }
    }

    private boolean attackEnemies() {
        boolean attacked = false;
        for (int x = getPosition().getX() - 1; x <= getPosition().getX() + 1; x++) {
            for (int y = getPosition().getY() - 1; y <= getPosition().getY() + 1; y++) {
                Position attackRangePos = new Position(x, y);
                if (!attackRangePos.equals(getPosition())) {
                    for (ImageTile tile : Engine.getActualRoom().getTiles()) {
                        if (attackRangePos.equals(tile.getPosition())) {
                            if (tile instanceof Enemy) {
                                ((Enemy) tile).receiveDamage(5);
                                attacked = true;
                            }
                        }
                    }
                }
            }
        }
        return attacked;
    }

    public void setDamage() {
        heroHealthPoints--;
    }

    @Override
    public String getName() {
        return "Hero";
    }

    /**
     * This method is called whenever the observed object is changed. This function is called when an
     * interaction with the graphic component occurs {{@link ImageMatrixGUI}}
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        Integer keyCode = (Integer) arg;
        if (keyCode == KeyEvent.VK_DOWN) {
            if (getPosition().getY() <= 8) {
                move(position.plus(Direction.DOWN.asVector()));
                lastDir = Direction.DOWN;
            }
        }
        if (keyCode == KeyEvent.VK_UP) {
            if (getPosition().getY() > 0) {
                move(position.plus(Direction.UP.asVector()));
                lastDir = Direction.UP;
            }
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            if (getPosition().getX() <= 8) {
                move(position.plus(Direction.LEFT.asVector()));
                lastDir = Direction.LEFT;
            }
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            if (getPosition().getX() > 0) {
                move(position.plus(Direction.RIGHT.asVector()));
                lastDir = Direction.RIGHT;
            }
        }

        if (keyCode == KeyEvent.VK_SPACE) {
            if (numFireBalls > 0) {
                numFireBalls--;
                FireBall fireBall = new FireBall(position);
                FireBallThread fireBallThread = new FireBallThread(lastDir, fireBall);
                fireBallThread.start();
                Engine.getActualRoom().getTiles().add(fireBall);
                ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
                gui.addImage(fireBall);
            }
        }

        if (keyCode == KeyEvent.VK_1) {
            useItem(1);
        }
        if (keyCode == KeyEvent.VK_2) {
            useItem(2);
        }
        if (keyCode == KeyEvent.VK_3) {
            useItem(3);
        }

        if (heroHealthPoints <= 0) {
            ImageMatrixGUI.getInstance().clearImages();
            ImageMatrixGUI.getInstance().deleteObservers();
            Engine.init();
        }
        Engine.updateStatus();
    }
}
