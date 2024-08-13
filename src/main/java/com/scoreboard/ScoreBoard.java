package com.scoreboard;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ScoreBoard {
    private ConcurrentHashMap<Integer, Match> scoreBoard;

    public ScoreBoard() {
        this.scoreBoard = new ConcurrentHashMap<>();
    }

    public void addMatch (Match match) {
        if (match != null) {
            this.scoreBoard.put(match.hashCode(), match);
        }
        else {
            throw new IllegalArgumentException("Scoreboard cannot add a null value");
        }
    }

    public Match getMatch(int key) {
        return this.scoreBoard.get(key);
    }

    public void removeMatch(int key) {
        this.scoreBoard.remove(key);
    }

    public LinkedHashMap<Integer, Match> getSummaryOfMatches() {
        Comparator<Match> combinedScoreComparator = Comparator
                .comparingInt((Match m) -> m.getHomeTeamScore() + m.getAwayTeamScore()).reversed();
        Comparator<Match> secondaryComparator = Comparator.comparing(Match::getStartTime);
        Comparator<Match> combinedComparator = combinedScoreComparator.thenComparing(secondaryComparator);

        LinkedHashMap<Integer, Match> orderedSummaryOfMatches = this.scoreBoard.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(combinedComparator))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        return orderedSummaryOfMatches;
    }

    public ConcurrentHashMap<Integer, Match> getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(ConcurrentHashMap<Integer, Match> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
}
