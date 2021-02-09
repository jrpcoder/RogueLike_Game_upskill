package pt.upskills.projeto.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReadFiles {
    private final File folderFiles;
    private List<Room> roomList = new ArrayList<>();

    public ReadFiles(File folderFiles) {
        this.folderFiles = folderFiles;
    }

    public List<Room> fileListFolder() {
        for(File file : folderFiles.listFiles()) {
            Room room = new Room(file, file.getName());
            roomList.add(room);
        }
        RoomCompare roomOrdenate = new RoomCompare();
        Collections.sort(roomList, roomOrdenate);
        return roomList;
    }

}
