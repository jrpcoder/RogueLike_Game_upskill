package pt.upskills.projeto.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadLeaderboard {
    private final String leaderboardFilePath = "stats/leaderboard.txt";
    private List<Score> scoreList = new ArrayList<>();

    public ReadLeaderboard(File file) {
        try (Scanner scanner = new Scanner(file)){
            List<String[]> scoresVetorList = new ArrayList<>();
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");
                scoresVetorList.add(tokens);
            }


                for(String[] scoreVetor : scoresVetorList) {
                    if(scoreVetor.length == 3) {
                        checkIfScoreInteger(scoreVetor[2]);
                        Score score = new Score(scoreVetor[1], Integer.parseInt(scoreVetor[2]));
                        scoreList.add(score);
                    }
                }

        } catch (FileNotFoundException e) {
            System.out.println("Trying to read leaderboard file.");
            System.out.println("File doesn't exist.");
        }
    }

    public List<Score> getScoreList() {
        return scoreList;
    }

    public void printLeaderboard(File file) {
        try {
            PrintWriter fileWriter = new PrintWriter(file);
            int counter = 1;
            for(Score score : scoreList) {
                fileWriter.println("" + counter + "." + score);
                counter++;
            }
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Creating leaderboard file...");
            System.out.println("It was not possible to create the file!");
        }
    }

    public boolean checkIfScoreInteger(String val){
        try {
            Integer.parseInt(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
