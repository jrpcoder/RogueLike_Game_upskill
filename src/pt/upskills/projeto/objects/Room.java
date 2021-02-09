package pt.upskills.projeto.objects;

import pt.upskills.projeto.gui.ImageMatrixGUI;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Room {
    private List<Door> listDoors = new ArrayList<>();
    private List<ImageTile> listTiles = new ArrayList<>();
    private List listFileInfo = new ArrayList();
    private String roomName;

    public boolean checkIfDoorInteger(String val){
        try {
            Integer.parseInt(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Room(File file, String fileName) {

        this.roomName = fileName;
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                listTiles.add(new Floor(new Position(i, j)));
            }
        }
        try (Scanner scanner = new Scanner(file)){
            List<String[]> mapVetorLista = new ArrayList<>();
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.startsWith("#")) {
                    String[] tokens = line.split(" ");
                    mapVetorLista.add(tokens);
                } else {
                    String[] tokens = line.split("");
                    mapVetorLista.add(tokens);
                }
            }


            int counterInfoLines = 0;
            for(String[] arrayTest : mapVetorLista) {
                if(arrayTest[0].equals("#")) {
                    counterInfoLines++;
                }
            }

            for (int i = counterInfoLines; i < mapVetorLista.size(); i++) {
                int coordinateY = i - counterInfoLines;
                for (int j = 0; j < 10; j++) {
                    if (mapVetorLista.get(i)[j].equals("W")) {
                        listTiles.add(new Wall(new Position(j, coordinateY)));
                    } else if (mapVetorLista.get(i)[j].equals("G")) {
                        listTiles.add(new Grass(new Position(j, coordinateY)));
                    } else if (mapVetorLista.get(i)[j].equals("c")) {
                        listTiles.add(new Trophy(new Position(j, coordinateY)));
                    } else if (mapVetorLista.get(i)[j].equals("S")) {
                        Skeleton skeleton = new Skeleton(new Position(j, coordinateY));
                        skeleton.setAttack(15);
                        skeleton.setLife(60);
                        listTiles.add(skeleton);
                    } else if(mapVetorLista.get(i)[j].equals("B")) {
                        Bat bat = new Bat(new Position(j, coordinateY));
                        bat.setAttack(15);
                        bat.setLife(30);
                        listTiles.add(bat);
                    } else if(mapVetorLista.get(i)[j].equals("T")) {
                        Thief thief = new Thief(new Position(j, coordinateY));
                        thief.setAttack(30);
                        thief.setLife(100);
                        listTiles.add(thief);
                    } else if (mapVetorLista.get(i)[j].equals("e")) {
                        Sword sword = new Sword(new Position(j, coordinateY));
                        listTiles.add(sword);
                    } else if (mapVetorLista.get(i)[j].equals("m")) {
                        Hammer hammer = new Hammer(new Position(j, coordinateY));
                        listTiles.add(hammer);
                    } else if(mapVetorLista.get(i)[j].equals("k")) {
                        Key key = new Key(new Position(j, coordinateY));
                        listTiles.add(key);
                    } else if (checkIfDoorInteger(mapVetorLista.get(i)[j])) {
                        Door door = new Door(new Position(j, coordinateY), mapVetorLista.get(Integer.parseInt(mapVetorLista.get(i)[j]) + 1));
                        if(mapVetorLista.get(Integer.parseInt(mapVetorLista.get(i)[j]) + 1).length  == 6) {
                            if(mapVetorLista.get(Integer.parseInt(mapVetorLista.get(i)[j]) + 1)[5].equals("key1")) {
                                door.setKey(true);
                            }
                        }
                        listDoors.add(door);
                        listTiles.add(door);
                    }
                }
            }
            DoorCompare doorOrdenate = new DoorCompare();
            Collections.sort(listDoors, doorOrdenate);
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não existe.");
        }
    }

    public List<Door> getListDoors() {
        return listDoors;
    }

    public List<ImageTile> getListTiles() {
        return listTiles;
    }

    public String getRoomName() {
        return roomName;
    }
}
