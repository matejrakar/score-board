package com.scoreboard;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.LinkedHashMap;

import static com.scoreboard.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest extends BaseTest {

    @Test
    public void addMatch_ValidParam_NoError() {
        ScoreBoard scoreBoard = new ScoreBoard();
        Match match = createTestMatch(TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME);

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
        Match match = createTestMatch(TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME);
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
        Match match = createTestMatch(TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME);
        scoreBoard.addMatch(match);

        scoreBoard.removeMatch(match.hashCode());

        assertTrue(scoreBoard.getScoreBoard().isEmpty());
    }

    @Test
    public void getSummaryOfMatches_MultipleMatches_ReturnsOrderedMap() throws NoSuchFieldException, IllegalAccessException{
        ScoreBoard unorderedScoreBoard = new ScoreBoard();
        Match match1 = createTestMatch(TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME, 1, 2, Instant.parse(TEST_TIME_FIRST));
        Match match2 = createTestMatch(TEST_HOME_TEAM_NAME_2, TEST_AWAY_TEAM_NAME_2, 3, 3, Instant.parse(TEST_TIME_SECOND));
        Match match3 = createTestMatch(TEST_HOME_TEAM_NAME_3, TEST_AWAY_TEAM_NAME_3, 2, 1, Instant.parse(TEST_TIME_THIRD));
        unorderedScoreBoard.addMatch(match1);
        unorderedScoreBoard.addMatch(match2);
        unorderedScoreBoard.addMatch(match3);
        LinkedHashMap<Integer, Match> expectedOrderedSummary = new LinkedHashMap<>();
        expectedOrderedSummary.put(match2.hashCode(), match2);
        expectedOrderedSummary.put(match3.hashCode(), match3);
        expectedOrderedSummary.put(match1.hashCode(), match1);

        LinkedHashMap<Integer, Match>  actualOrderedSummary = unorderedScoreBoard.getSummaryOfMatches();

        assertIterableEquals(actualOrderedSummary.entrySet(), expectedOrderedSummary.entrySet(), "The LinkedHashMaps must be equal");
    }

    @Test
    public void getSummaryOfMatches_SingleMatch_ReturnsMapSingleEntry() {
        ScoreBoard unorderedScoreBoard = new ScoreBoard();
        Match match1 = createTestMatch(TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME);
        unorderedScoreBoard.addMatch(match1);
        LinkedHashMap<Integer, Match> expectedOrderedSummary = new LinkedHashMap<>();
        expectedOrderedSummary.put(match1.hashCode(), match1);

        LinkedHashMap<Integer, Match>  actualOrderedSummary = unorderedScoreBoard.getSummaryOfMatches();

        assertIterableEquals(actualOrderedSummary.entrySet(), expectedOrderedSummary.entrySet(), "The LinkedHashMaps must be equal");
    }

    @Test
    public void getSummaryOfMatches_MultipleMatchesTiedTotalScore_ReturnsMapMostRecentFirst() throws NoSuchFieldException, IllegalAccessException {
        ScoreBoard unorderedScoreBoard = new ScoreBoard();
        Match match1 = createTestMatch(TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME, 1, 2, Instant.parse(TEST_TIME_FIRST));
        Match match2 = createTestMatch(TEST_HOME_TEAM_NAME_2, TEST_AWAY_TEAM_NAME_2, 3, 0, Instant.parse(TEST_TIME_SECOND));
        Match match3 = createTestMatch(TEST_HOME_TEAM_NAME_3, TEST_AWAY_TEAM_NAME_3, 2, 1, Instant.parse(TEST_TIME_THIRD));
        unorderedScoreBoard.addMatch(match1);
        unorderedScoreBoard.addMatch(match2);
        unorderedScoreBoard.addMatch(match3);
        LinkedHashMap<Integer, Match> expectedOrderedSummary = new LinkedHashMap<>();
        expectedOrderedSummary.put(match3.hashCode(), match3);
        expectedOrderedSummary.put(match2.hashCode(), match2);
        expectedOrderedSummary.put(match1.hashCode(), match1);

        LinkedHashMap<Integer, Match>  actualOrderedSummary = unorderedScoreBoard.getSummaryOfMatches();

        assertIterableEquals(actualOrderedSummary.entrySet(), expectedOrderedSummary.entrySet(), "The LinkedHashMaps must be equal");
    }

    @Test
    public void getSummaryOfMatches_MultipleMatchesSameTotalScoreAndTime_ReturnsMapInitialOrder() throws NoSuchFieldException, IllegalAccessException {
        Match match1 = createTestMatch(TEST_HOME_TEAM_NAME, TEST_AWAY_TEAM_NAME, 1, 2, Instant.parse(TEST_TIME_FIRST));
        Match match2 = createTestMatch(TEST_HOME_TEAM_NAME_2, TEST_AWAY_TEAM_NAME_2, 3, 0, Instant.parse(TEST_TIME_FIRST));
        Match match3 = createTestMatch(TEST_HOME_TEAM_NAME_3, TEST_AWAY_TEAM_NAME_3, 2, 1, Instant.parse(TEST_TIME_FIRST));
        Field startTimeField = Match.class.getDeclaredField("startTime");
        startTimeField.setAccessible(true);
        Instant testTime = Instant.now();
        startTimeField.set(match1, testTime);
        startTimeField.set(match1, testTime);
        startTimeField.set(match1, testTime);
        ScoreBoard unorderedScoreBoard = new ScoreBoard();
        unorderedScoreBoard.addMatch(match1);
        unorderedScoreBoard.addMatch(match2);
        unorderedScoreBoard.addMatch(match3);
        LinkedHashMap<Integer, Match> expectedOrderedSummary = new LinkedHashMap<>();
        expectedOrderedSummary.put(match1.hashCode(), match1);
        expectedOrderedSummary.put(match2.hashCode(), match2);
        expectedOrderedSummary.put(match3.hashCode(), match3);

        LinkedHashMap<Integer, Match>  actualOrderedSummary = unorderedScoreBoard.getSummaryOfMatches();

        assertIterableEquals(actualOrderedSummary.entrySet(), expectedOrderedSummary.entrySet(), "The LinkedHashMaps must be equal");
    }

    @Test
    public void getSummaryOfMatches_NoMatches_ReturnsEmptyMap() {
        ScoreBoard unorderedScoreBoard = new ScoreBoard();

        LinkedHashMap<Integer, Match>  actualOrderedSummary = unorderedScoreBoard.getSummaryOfMatches();

        assertTrue(actualOrderedSummary.entrySet().isEmpty());
    }

}
