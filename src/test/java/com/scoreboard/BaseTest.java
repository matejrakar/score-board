package com.scoreboard;

import java.lang.reflect.Field;
import java.time.Instant;

public abstract class BaseTest {

    public Match createTestMatch(String homeTeamName, String awayTeamName) {
        Match match = new Match(homeTeamName, awayTeamName);
        return match;
    }

    public Match createTestMatch(String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore, Instant startTime) throws NoSuchFieldException, IllegalAccessException {
        Match match = new Match(homeTeamName, awayTeamName);
        match.setHomeTeamScore(homeTeamScore);
        match.setAwayTeamScore(awayTeamScore);
        Field startTimeField = Match.class.getDeclaredField("startTime");
        startTimeField.setAccessible(true);
        startTimeField.set(match, startTime);
        return match;
    }

}
