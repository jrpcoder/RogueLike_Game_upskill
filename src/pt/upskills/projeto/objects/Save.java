package pt.upskills.projeto.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Save {
    private boolean hasSave;
    private List<String> saveData = new ArrayList<>();

    public Save(File file) {
        try (Scanner scanner = new Scanner(file)){
            List<String[]> scoresVetorList = new ArrayList<>();
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(";");
                scoresVetorList.add(tokens);
            }

            for(String[] saveVetor : scoresVetorList) {
                if(saveVetor.length == 2) {
                    saveData.add(saveVetor[0]);
                    saveData.add(saveVetor[1]);
                    hasSave = true;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Trying to read leaderboard file.");
            System.out.println("File doesn't exist.");
        }
    }

    public List<String> getSaveData() {
        return saveData;
    }

    public boolean getHasSave() {return hasSave;}

    public void setHasSave(boolean hasSave) {
        this.hasSave = hasSave;
    }

    public void setSaveData(String room, int score) {
        saveData.clear();
        saveData.add(room);
        saveData.add(Integer.toString(score));
    }

    public void printSaveFile(File file) {
        try {
            PrintWriter fileWriter = new PrintWriter(file);
            fileWriter.println(saveData.get(0) + ";" + saveData.get(1));
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Creating leaderboard file...");
            System.out.println("It was not possible to create the file!");
        }
    }
}
