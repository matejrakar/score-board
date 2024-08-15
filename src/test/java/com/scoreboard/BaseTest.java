package com.scoreboard;

public abstract class BaseTest {

    public Match createTestMatch(String homeTeamName, String awayTeamName) {
        Match match = new Match(homeTeamName, awayTeamName);
        return match;
    }

    public Match createTestMatch(String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore) {
        Match match = new Match(homeTeamName, awayTeamName);
        match.setHomeTeamScore(homeTeamScore);
        match.setAwayTeamScore(awayTeamScore);
        return match;
    }

}
