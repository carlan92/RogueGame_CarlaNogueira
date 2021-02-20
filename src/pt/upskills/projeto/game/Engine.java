package pt.upskills.projeto.game;

import pt.upskills.projeto.gui.ImageMatrixGUI;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.creatures.*;
import pt.upskills.projeto.objects.items.*;
import pt.upskills.projeto.objects.room.*;
import pt.upskills.projeto.objects.topBar.Black;
import pt.upskills.projeto.objects.topBar.Green;
import pt.upskills.projeto.objects.topBar.Red;
import pt.upskills.projeto.objects.topBar.RedGreen;
import pt.upskills.projeto.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Engine {
    private static final List<ImageTile> status = new ArrayList<>();
    private static final List<Room> rooms = new ArrayList<>();
    private static int initLevel = 0;
    static ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
    public static Hero hero;

    public static void init() {
        initLevel = 0;
        readAllRooms();
        Room firstLevel = rooms.get(initLevel);
        hero = new Hero(firstLevel.getHeroPosition());
        firstLevel.getTiles().add(hero);

        for (ImageTile tile : Engine.getActualRoom().getTiles()) {
            if (tile instanceof Observer) {
                gui.addObserver((Observer) tile);
            }
        }
        gui.newImages(firstLevel.getTiles());
        updateStatus();
    }

    private void go() {
        gui.go();
        while (true) {
            gui.update();
        }
    }

    private static void createFireBar() {
        int numFireBalls = hero.getNumFireBalls();
        for (int i = 0; i < numFireBalls; i++) {
            status.add(new FireBall(new Position(i, 0)));
        }
    }

    private static void createItemBar() {
        int numItems = hero.getInventory().size();
        for (int i = 0; i < numItems; i++) {
            RoomItem item = hero.getInventory().get(i);
            item.setPosition(new Position(i + 7, 0));
            status.add(item);
        }
    }

    private static void createHealthBar() {
        int heroHealth = hero.getHealth();
        int numberOfGreen = heroHealth / 2;
        for (int i = 3; i < 7; i++) {
            status.add(new Red(new Position(i, 0)));
        }
        for (int i = 0; i < numberOfGreen; i++) {
            status.add(new Green(new Position(i + 3, 0)));
        }
        if (heroHealth % 2 == 1) {
            status.add(new RedGreen(new Position(numberOfGreen + 3, 0)));
        }
    }

    public static void updateStatus() {
        gui.clearStatus();
        status.clear();
        for (int i = 0; i < 10; i++) {
            status.add(new Black(new Position(i, 0)));
        }
        createHealthBar();
        createFireBar();
        createItemBar();
        gui.newStatusImages(status);
    }

    private static void readAllRooms() {
        try {
            rooms.clear();
            for (int level = 0; true; level++) {
                Scanner fileScanner = new Scanner(new File("rooms/room" + level + ".txt"));
                List<ImageTile> roomTiles = new ArrayList<>();
                List<ImageTile> enemyTiles = new ArrayList<>();

                Position heroPosition = new Position(0, 0);
                for (int x = 0; x < 10; x++) {
                    for (int y = 0; y < 10; y++) {
                        roomTiles.add(new Floor(new Position(x, y))); //Floor tiles
                    }
                }
                int y = 0;
                while (fileScanner.hasNextLine()) {
                    String nextLine = fileScanner.nextLine();
                    String[] split = nextLine.split("");
                    for (int x = 0; x < split.length; x++) {
                        if (split[x].equals("W")) {
                            roomTiles.add(new Wall(new Position(x, y)));
                        }
                        if (split[x].equals("G")) {
                            roomTiles.add(new Grass(new Position(x, y)));
                        }
                        if (split[x].equals("M")) {
                            roomTiles.add(new GoodMeat(new Position(x, y)));
                        }
                        if (split[x].equals("F")) {
                            roomTiles.add(new FireBall(new Position(x, y)));
                        }
                        if (split[x].equals("s")) {
                            roomTiles.add(new Sword(new Position(x, y)));
                        }
                        if (split[x].equals("h")) {
                            roomTiles.add(new Hammer(new Position(x, y)));
                        }
                        if (split[x].equals("H")) {
                            heroPosition = new Position(x, y);
                        }
                        if (split[x].equals("T")) {
                            Thief thief = new Thief(new Position(x, y));
                            enemyTiles.add(thief);
                        }
                        if (split[x].equals("S")) {
                            Skeleton skeleton = new Skeleton(new Position(x, y));
                            enemyTiles.add(skeleton);
                        }
                        if (split[x].equals("B")) {
                            Bat bat = new Bat(new Position(x, y));
                            enemyTiles.add(bat);
                        }
                        if (split[x].equals("0")) {
                            roomTiles.add(new DoorOpen(new Position(x, y)));
                        }
                        if (split[x].equals("1")) {
                            roomTiles.add(new DoorWay(new Position(x, y)));
                        }
                    }
                    y++;
                }
                roomTiles.addAll(enemyTiles);
                rooms.add(new Room(level, roomTiles, heroPosition));
                fileScanner.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Not possible to read the file");
        }
    }

    public static Room getActualRoom() {
        for (Room room : rooms) {
            if (room.getId() == initLevel) {
                return room;
            }
        }
        return null;
    }

    public static void goToNextLevel() {
        initLevel++;
        refreshWindow();
    }

    public static void goToLowerLevel() {
        initLevel--;
        refreshWindow();
    }

    private static void refreshWindow() {
        gui.clearImages();
        gui.deleteObservers();
        Room nextRoom = rooms.get(initLevel);
        hero.setPosition(nextRoom.getHeroPosition());
        nextRoom.getTiles().add(hero);
        gui.newImages(nextRoom.getTiles());

        for (ImageTile tile : Engine.getActualRoom().getTiles()) {
            if (tile instanceof Observer) {
                gui.addObserver((Observer) tile);
            }
        }
    }

    public static void main(String[] args) {
        Engine engine = new Engine();
        Engine.init();
        engine.go();
    }
}
