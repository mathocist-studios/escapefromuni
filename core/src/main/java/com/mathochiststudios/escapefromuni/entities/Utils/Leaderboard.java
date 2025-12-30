package com.mathochiststudios.escapefromuni.entities.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * A simple leaderboard system that reads and writes to a text file.
 * Each entry is stored in the format "name - score-difficulty".
 * Used for maintaining high scores between game sessions.
 */
public class Leaderboard {

    //does nothing lol 
    public void Leaderboard() {
        loadLeaderboard();
    }

    // array of strings representing leaderboard entries where each line is "name - score-difficulty" for easy printing and parsing
    private List<String> leaderboardLines = new ArrayList<>();

    // opens the leaderboard file in the same directory as the exe because tryna do stuff like open program files gets fucky on linux and mac and im not doing that no
    String path = System.getProperty("user.dir");
    File leaderboardFile = new File(path, "leaderboards.txt");

    //used for rendering the leaderboard in leaderboard menu
    public List<String> getLeaderboardLines() {
        return leaderboardLines;
    }

    //does a load of funky stuff to add a new entry to the leaderboard while keeping it sorted since we have to keep the higher scores at the top
    /**
     * adds a new entry to the leaderboard if the score qualifies.
     * @param name The name of the player.
     * @param score The score achieved by the player.
     * @param difficulty The difficulty level of the game as a string (important i fucked this up before).
     */
    public void addLeaderboardEntry(String name, int score, String difficulty) {
        //mainly works by reading each line in the old list, if the new score is higher than the one its checking it puts the new score in then adds the rest of the old ones after
        List<String> newleaderboardLines = new ArrayList<>();
        System.out.println("trying to add" + name + " - " + score + "-" + difficulty);
        Boolean hasLineBeenAdded = false;
        //try catch to stop it all from imploding if i dare run it on linux which despises giving any program file permissions for me
        try (BufferedReader br = new BufferedReader(new FileReader(leaderboardFile))) {
            String line;

            //reads each line of the old leaderboard
            while ((line = br.readLine()) != null) {
                //if i fucked up this will run i beg it wont but if it does itll just ignore the empty line
                if (line.trim().isEmpty()) {
                    continue;
                }
                //splits the line into name and score parts so i can check the score
                String[] parts = line.split(" - ");
                String[] scoreParts = parts[1].split("-");
                int lineScore = Integer.parseInt(scoreParts[0]);
                //checks if the score found in this line is bigger or lower than the score just gotten
                if (lineScore <= score && !hasLineBeenAdded) {
                    newleaderboardLines.add(name + " - " + score + "-" + difficulty);
                    hasLineBeenAdded = true;
                }
                newleaderboardLines.add(line);
            }
            //oh btw fun fact it doesnt just stop adding at 10 the txt file can be infinite size but only 10 are displayed
            //pretty much just "if the new score is the lowest score add it to the end"
            if (!hasLineBeenAdded) {
                newleaderboardLines.add(name + " - " + score + "-" + difficulty);
            }
        } catch (IOException e) {
            leaderboardLines.clear();
            leaderboardLines.add("Error reading leaderboard.");
        }

        //now we write the file with the newleaderboardLines which contains the up to date leaderboard, apparently you can just put the filewriter second variable to 
        //false and it just overwrites each line instead which works brillantly here since the new leaderboard will always be bigger than the old
        try {
            if (!leaderboardFile.exists()) {
                leaderboardFile.createNewFile();
            }
            java.io.FileWriter writer = new java.io.FileWriter(leaderboardFile, false);
            for (String line : newleaderboardLines) {
                System.out.println(line);
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //fixes the leaderboardlines variable so i can stop running it every single frame dear god how didnt i notice that before
        this.loadLeaderboard();
    }


    //loads from the leaderboard file so the leaderboard is maintained between sessions
    /**
     * loads the leaderboard from the text file into memory.
     */
    public void loadLeaderboard() {
        leaderboardLines.clear();

        try {
            leaderboardFile.createNewFile();
        } catch (IOException e) {
            return;
        }

        if (!leaderboardFile.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(leaderboardFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                leaderboardLines.add(line);
                if (leaderboardLines.size() >= 20) break;
            }
        } catch (IOException e) {
            leaderboardLines.clear();
            leaderboardLines.add("Error reading leaderboard.");
        }
    }
}
