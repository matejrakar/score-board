package com.scoreboard;

import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;

import static com.scoreboard.TestConstants.*;
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

    @Test
    public void getMatch_MatchExists_ReturnsMatch() {
        ScoreBoard scoreBoard = new ScoreBoard();
        Match match = new Match(TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME);
        scoreBoard.addMatch(match);
        Match matchFromScoreBoard = scoreBoard.getMatch(match.hashCode());
        assertEquals(matchFromScoreBoard, match);
    }

    @Test
    public void getMatch_MatchDoesNotExist_ReturnsNull() {
        ScoreBoard scoreBoard = new ScoreBoard();
        Match matchFromScoreBoard = scoreBoard.getMatch(123);
        assertNull(matchFromScoreBoard);
    }

    @Test
    public void finishMatch_MatchExists_RemovedFromList() {
        ScoreBoard scoreBoard = new ScoreBoard();
        Match match = new Match(TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME);
        scoreBoard.addMatch(match);
        scoreBoard.removeMatch(match.hashCode());
        assertTrue(scoreBoard.getScoreBoard().isEmpty());
    }

    @Test
    public void getSummaryOfMatches_MultipleMatches_ReturnsOrderedList() {
        ScoreBoard unorderedScoreBoard = new ScoreBoard();
        Match match1 = new Match(TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME);
        Match match2 = new Match(TEST_HOME_TEAM_NAME_2, TEST_AWAY_TEAM_NAME_2);
        Match match3 = new Match(TEST_HOME_TEAM_NAME_3, TEST_AWAY_TEAM_NAME_3);
        match1.updateScore(1,2);
        match2.updateScore(3,3);
        match3.updateScore(2,1);
        unorderedScoreBoard.addMatch(match1);
        unorderedScoreBoard.addMatch(match2);
        unorderedScoreBoard.addMatch(match3);
        LinkedHashMap<Integer, Match> expectedOrderedSummary = new LinkedHashMap<>();
        expectedOrderedSummary.put(match2.hashCode(), match2);
        expectedOrderedSummary.put(match1.hashCode(), match1);
        expectedOrderedSummary.put(match3.hashCode(), match3);
        assertIterableEquals(unorderedScoreBoard.getSummaryOfMatches().entrySet(), expectedOrderedSummary.entrySet(), "The LinkedHashMaps must be equal");
    }

}
