package pt.upskills.projeto.objects;

import java.util.Comparator;

public class RoomCompare implements Comparator<Room> {
    public int compare(Room room1, Room room2) {
        return extractInt(room1.getRoomName()) - extractInt(room2.getRoomName());
    }

    int extractInt(String s) {
        String num = s.replaceAll("\\D", "");
        // return 0 if no digits found
        return num.isEmpty() ? 0 : Integer.parseInt(num);
    }
}
