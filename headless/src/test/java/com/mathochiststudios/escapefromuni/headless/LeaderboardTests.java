package com.mathochiststudios.escapefromuni.headless;

import com.mathochiststudios.escapefromuni.entities.Utils.Leaderboard;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class LeaderboardTests {

    @Test
    public void testAddLeaderboardEntry() {

        Leaderboard leaderboard = new Leaderboard();

        leaderboard.clearLeaderboard();

        List<String> entries = leaderboard.getLeaderboardLines();
        int initialSize = entries.size();

        // Add a new entry
        leaderboard.addLeaderboardEntry("TestPlayer", 150, "Medium");

        // Verify the entry was added
        entries = leaderboard.getLeaderboardLines();
        assertEquals(initialSize + 1, entries.size(), "Leaderboard size should increase by 1 after adding an entry.");
        assertEquals("TestPlayer - 150-Medium", entries.get(0), "New entry should be at the top of the leaderboard.");

        leaderboard.clearLeaderboard();

    }

    @Test
    public void testLeaderboardSorting() {

        Leaderboard leaderboard = new Leaderboard();

        // Clear existing entries for testing
        leaderboard.getLeaderboardLines().clear();

        // Add multiple entries
        leaderboard.addLeaderboardEntry("Player1", 100, "Easy");
        leaderboard.addLeaderboardEntry("Player2", 200, "Hard");
        leaderboard.addLeaderboardEntry("Player3", 150, "Medium");

        // Verify the entries are sorted correctly
        List<String> entries = leaderboard.getLeaderboardLines();
        assertEquals("Player2 - 200-Hard", entries.get(0), "Highest score should be first.");
        assertEquals("Player3 - 150-Medium", entries.get(1), "Second highest score should be second.");
        assertEquals("Player1 - 100-Easy", entries.get(2), "Lowest score should be last.");

        leaderboard.clearLeaderboard();

    }

}
