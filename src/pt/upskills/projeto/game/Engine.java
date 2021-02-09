package pt.upskills.projeto.game;

import pt.upskills.projeto.gui.ImageMatrixGUI;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.*;
import pt.upskills.projeto.objects.ReadFiles;
import pt.upskills.projeto.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Character;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Engine {
    //lists to update the gui
    private static List<ImageTile> tiles = new ArrayList<>();
    private static List<ImageTile> statusTilesFire = new ArrayList<>();
    private static List<ImageTile> statusTilesLife = new ArrayList<>();
    private static List<ImageTile> statusTilesItems = new ArrayList<>();
    //auxiliary booleans that will be used to trigger updates
    private static boolean tilesAux = false;
    private static boolean statusAux = false;
    private static boolean isHeroAlive = true;
    private static boolean passedDoor = false;
    private static boolean wasSaved = false;
    private String currentRoom = "";
    Hero hero = new Hero(new Position(2, 7));

    private static List<String> enteredDoorInfo = new ArrayList<>();

    public static List<ImageTile> getTiles() {
        return tiles;
    }
    public static List<ImageTile> getStatusTilesFire() {
        return statusTilesFire;
    }
    public static List<ImageTile> getStatusTilesLife() {
        return statusTilesLife;
    }
    public static List<ImageTile> getStatusTilesItems() {
        return statusTilesItems;
    }
    public static boolean getIsHeroAlive() { return isHeroAlive; }

    public static List<String> getEnteredDoorInfo() {
        return enteredDoorInfo;
    }

    public static void setTilesAux(boolean val) {
        tilesAux = val;
    }
    public static void setStatusAux(boolean val) {
        statusAux = val;
    }

    public static void setWasSaved(boolean wasSaved) {
        Engine.wasSaved = wasSaved;
    }

    public static void setPassedDoor(boolean passedDoor) {
        Engine.passedDoor = passedDoor;
    }

    public static void setIsHeroAlive(boolean val) { isHeroAlive = val;}

    //function to reset the life of the enemies
    void resetEnemy(List<ImageTile> tiles) {
        for(ImageTile tile : tiles) {
            if(tile.getName().equals("Skeleton")) {
                Skeleton skeleton = (Skeleton) tile;
                skeleton.setLife(60);
            } else if(tile.getName().equals("Bat")) {
                Bat bat = (Bat) tile;
                bat.setLife(30);
            } else if(tile.getName().equals("Thief")) {
                Thief thief = (Thief) tile;
                thief.setLife(100);
            }
        }
    }

    //checks if the given string has any whitespaces
    public boolean nameHasSpace(String name) {
        for(char character : name.toCharArray()) {
            if(Character.isWhitespace(character)) {
                return true;
            }
        }
        return false;
    }

    //when the game ends, the user is asked for a name to put in the leaderboard
    //the name cannot contain spaces
    public String askName() {
        Scanner scanner = new Scanner(System.in);
        String name = null;
        while(name == null) {
            System.out.println("Provide a one word nickname.");
            name = scanner.nextLine();
            if(nameHasSpace(name)) {
                System.out.println("Only a one word name without spaces please.");
                System.out.println("----------------------------------------------");
                name = null;
            }
        }
        return name;
    }

    //At the beginning or when the hero dies, choose the correct room to restart the game
    public void chooseSavedOrInitialRoom(Save save, List<Room> rooms) {
        if(save.getHasSave()) {
            for(Room room : rooms) {
                if(room.getRoomName().equals(save.getSaveData().get(0))){
                    currentRoom = room.getRoomName();
                    tiles.addAll(room.getListTiles());
                    Position initialDoorPosition = room.getListDoors().get(0).getPosition();
                    hero.setPosition(initialDoorPosition);
                    hero.setPoints(Integer.parseInt(save.getSaveData().get(1)));
                    hero.setAttack(20);
                    hero.setLife(120);
                    tiles.add(hero);
                    break;
                }
            }
        } else {
            currentRoom = rooms.get(0).getRoomName();
            hero.setPoints(0);
            hero.setAttack(20);
            hero.setLife(120);
            tiles.addAll(rooms.get(0).getListTiles());
            tiles.add(hero);
        }
    }

    public void init(){
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

        //Read save file
        Save save = new Save(new File("save/save.txt"));

        //Read the room files and use the first one as the starting point
        ReadFiles folderContents = new ReadFiles(new File("rooms/"));
        List<Room> rooms = folderContents.fileListFolder();

        //Initiate the room. If there is no save then stars on room0.
        chooseSavedOrInitialRoom(save, rooms);

        //comparator to order the leaderboard scores
        ScoreCompare scoresOrdenate = new ScoreCompare();
        //read the leaderboard file
        ReadLeaderboard leaderboardList = new ReadLeaderboard(new File("stats/leaderboard.txt"));

        //Create the initial status bar list
        //first with a sublist with all blacks (will be background)
        List<ImageTile> statusTiles = new ArrayList<>();
        List<ImageTile> statusTilesBase = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            statusTilesBase.add(new Black(new Position(i, 0)));
        }

        //create the following sublists. one for each tipe of status
        for(int i = 0; i < 10; i++) {
            if(i < 3) {
                statusTilesFire.add(new Fire(new Position(i, 0)));
            } else if (i < 7) {
                statusTilesLife.add(new LifeGreen((new Position(i, 0))));
            } else {
                statusTilesItems.add(new Black(new Position(i, 0)));
            }
        }

        //add all list to the main status list. This is what's used to update the gui
        statusTiles.addAll(statusTilesBase);
        statusTiles.addAll(statusTilesFire);
        statusTiles.addAll(statusTilesLife);
        statusTiles.addAll(statusTilesItems);

        //create a base status list that will be used when the status bar needs to be reseted
        List<ImageTile> resetedStatusFire = new ArrayList<>();
        List<ImageTile> resetedStatusLife = new ArrayList<>();
        List<ImageTile> resetedStatusItems = new ArrayList<>();
        resetedStatusFire.addAll(statusTilesFire);
        resetedStatusLife.addAll(statusTilesLife);
        resetedStatusItems.addAll(statusTilesItems);

        gui.addObserver(hero);
        gui.newImages(tiles);
        gui.newStatusImages(statusTiles);
        gui.go();
        boolean addedScore = false;

        while (true){
            gui.update();

            // when true remove and create the new map
            if(tilesAux) {
                gui.clearImages();
                gui.newImages(tiles);
                setTilesAux(false);
            }

            // when true remove and create a new status bar
            if(statusAux) {
                statusTiles.clear();
                statusTiles.addAll(statusTilesBase);
                statusTiles.addAll(statusTilesFire);
                statusTiles.addAll(statusTilesLife);
                statusTiles.addAll(statusTilesItems);
                gui.clearStatus();
                gui.newStatusImages(statusTiles);
                setStatusAux(false);
            }

            // when a door is passed call the tiles list from the new room and create a new map.
            // if a key was used, set status aux to true in order to remove it from the status bar.
            if(passedDoor) {
                for(Room room : rooms) {
                    if(room.getRoomName().equals(enteredDoorInfo.get(1))) {
                        currentRoom = room.getRoomName();
                        tiles.clear();
                        tiles.addAll(room.getListTiles());
                        hero.setPosition(room.getListDoors().get(Integer.parseInt(enteredDoorInfo.get(2))).getPosition());
                        tiles.add(hero);
                        enteredDoorInfo.clear();
                        setStatusAux(true);
                        setTilesAux(true);
                        setPassedDoor(false);
                        break;
                    }
                }
                resetEnemy(tiles);
            }

            //if the hero dies ask the user for a name, order leaderboard and print it to the file.
            //restart game. Set room to first one or saved one and reset figures life.
            //reset the status bar
            if(!isHeroAlive) {
                String player = askName();
                leaderboardList.getScoreList().add(new Score(player, hero.getPoints()));
                Collections.sort(leaderboardList.getScoreList(), scoresOrdenate);
                leaderboardList.printLeaderboard(new File("stats/leaderboard.txt"));
                Save saveUpdated = new Save(new File("save/save.txt"));
                tiles.clear();
                chooseSavedOrInitialRoom(saveUpdated, rooms);
                resetEnemy(tiles);
                gui.clearImages();
                gui.newImages(tiles);
                System.out.println("Hero alive!");
                statusTilesFire.clear();
                statusTilesFire.addAll(resetedStatusFire);
                statusTilesItems.clear();
                statusTilesItems.addAll(resetedStatusItems);
                statusTilesLife.clear();
                statusTilesLife.addAll(resetedStatusLife);
                statusTiles.clear();
                statusTiles.addAll(statusTilesBase);
                statusTiles.addAll(statusTilesFire);
                statusTiles.addAll(statusTilesLife);
                statusTiles.addAll(statusTilesItems);
                gui.clearStatus();
                gui.newStatusImages(statusTiles);
                setIsHeroAlive(true);
            }

            //if the game was saved, a new save is registered
            if(wasSaved) {
                save.setSaveData(currentRoom, hero.getPoints());
                save.printSaveFile(new File("save/save.txt"));
                wasSaved = false;
            }

            //When the hero gets the trophy, the game ends and asks user for name and updates the leaderboard.
            if(hero.getHasTrophy() && !addedScore) {
                String player = askName();
                leaderboardList.getScoreList().add(new Score(player, hero.getPoints()));
                Collections.sort(leaderboardList.getScoreList(), scoresOrdenate);
                leaderboardList.printLeaderboard(new File("stats/leaderboard.txt"));
                addedScore = true;
            }
        }
    }

    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }
}
