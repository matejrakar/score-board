package com.scoreboard;

import org.junit.jupiter.api.Test;
import static com.scoreboard.TestConstants.TEST_AWAY_TEAM_NAME;
import static com.scoreboard.TestConstants.TEST_HOME_TEAM_NAME;

import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {

    @Test
    public void constructor_ValidParameters_InitializeCorrectly() {
        Match match = new Match(TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME);
        assertEquals(match.getHomeTeamName(), TEST_HOME_TEAM_NAME);
        assertEquals(match.getAwayTeamName(), TEST_AWAY_TEAM_NAME);
        assertEquals(match.getHomeTeamScore(), 0);
        assertEquals(match.getAwayTeamScore(), 0);
    }

    @Test
    public void constructor_InvalidParameters_InitializationFail() {
        assertThrows(IllegalArgumentException.class, () -> new Match("", null));
        assertThrows(IllegalArgumentException.class, () -> new Match(null, " "));
    }

    @Test
    public void equals_TwoEqualObjects_EqualsTrue() {
        Match match1 = new Match (TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME);
        Match match2 = new Match (TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME);
        assertTrue(match1.equals(match2));
        assertTrue(match1.hashCode() == match2.hashCode());
    }

    @Test
    public void updateScore_ValidScores_CorrectScoresSet() {
        Match match = new Match (TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME);
        match.updateScore(1, 0);
        assertEquals(match.getHomeTeamScore(), 1);
        assertEquals(match.getAwayTeamScore(), 0);
    }

    @Test
    public void updateScore_InvalidScores_ExceptionThrown() {
        Match match = new Match (TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME);
        assertThrows(IllegalArgumentException.class, () -> match.updateScore(-2, -1));
    }
}
