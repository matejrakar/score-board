package com.scoreboard;

import org.junit.jupiter.api.Test;

import static com.scoreboard.TestConstants.TEST_AWAY_TEAM_NAME;
import static com.scoreboard.TestConstants.TEST_HOME_TEAM_NAME;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {

    @Test
    public void addMatch_ValidParam_NoError() {
        ScoreBoard scoreBoard = new ScoreBoard();
        Match match = new Match(TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME);
        scoreBoard.addMatch(match);
        assertTrue(scoreBoard.getScoreBoard().containsKey(match.hashCode()));
        assertEquals(scoreBoard.getScoreBoard().get(match.hashCode()), match);
    }

    @Test
    public void addMatch_InvalidParam_ExceptionThrown() {
        ScoreBoard scoreBoard = new ScoreBoard();
        assertThrows(IllegalArgumentException.class, () -> scoreBoard.addMatch(null));
    }

}
