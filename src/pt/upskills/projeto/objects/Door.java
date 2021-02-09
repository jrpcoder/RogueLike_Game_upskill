package pt.upskills.projeto.objects;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Door implements ImageTile {
    private Position position;
    private String number;
    private String type;
    private String nextRoom;
    private String nextRoomHeroDoor;
    private boolean key;

    public Door(Position position, String[] doorData) {
        this.number = doorData[1];
        this.type = doorData[2];
        this.nextRoom = doorData[3];
        this.nextRoomHeroDoor = doorData[4];
        this.position = position;
    }



    @Override
    public String getName() {
        if(type.equals("D")) {
            return "DoorClosed";
        } else if (type.equals("E")) {
            return "DoorOpen";
        }
        return null;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getNextRoom() {
        return nextRoom;
    }

    public String getNextRoomHeroDoor() {
        return nextRoomHeroDoor;
    }

    public boolean getKey() {return key;}

    public void setKey(boolean key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Position: " + getPosition() + "; number: " + getNumber() + "; type: " + getType() + "; next room: " + getNextRoom() + "; next door for hero in new door: " + getNextRoomHeroDoor();
    }
}
