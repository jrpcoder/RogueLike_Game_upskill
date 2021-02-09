package pt.upskills.projeto.objects;

import java.util.Comparator;

public class DoorCompare implements Comparator<Door> {
    public int compare(Door door1, Door door2) {
        if(Integer.parseInt(door1.getNumber()) < Integer.parseInt(door2.getNumber())) {
            return -1;
        } else if (Integer.parseInt(door1.getNumber()) > Integer.parseInt(door2.getNumber())) {
            return 1;
        } else {
            return 0;
        }
    }
}
