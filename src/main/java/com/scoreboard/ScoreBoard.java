package com.scoreboard;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Holds a score board of type ConcurrentHashMap which can have Match hash codes as keys and Match objects as values
 */
public class ScoreBoard {
    private ConcurrentHashMap<Integer, Match> scoreBoard;

    /**
     * Constructs a new empty scoreBoard of type ConcurrentHashMap
     */
    public ScoreBoard() {
        this.scoreBoard = new ConcurrentHashMap<>();
    }

    /**
     * Adds a Match to scoreBoard
     * @param match
     * @throws IllegalArgumentException if Match object is null
     */
    public void addMatch (Match match) {
        if (match != null) {
            this.scoreBoard.put(match.hashCode(), match);
        }
        else {
            throw new IllegalArgumentException("Scoreboard cannot add a null value");
        }
    }

    /**
     * Gets a Match object with specific key from scoreBoard
     * @param key
     * @return Match object
     */
    public Match getMatch(int key) {
        return this.scoreBoard.get(key);
    }

    /**
     * Removes a Match object with specific key from scoreBoard
     * @param key
     */
    public void removeMatch(int key) {
        this.scoreBoard.remove(key);
    }

    /**
     * Get summary of matches based on each match's total combined score, descending. If total score is the same between two Match objects, orders by startTime descending.
     *
     * @return LinkedHashMap holding a summary of matches
     */
    public LinkedHashMap<Integer, Match> getSummaryOfMatches() {
        Comparator<Match> combinedScoreComparator = Comparator
                .comparingInt((Match m) -> m.getHomeTeamScore() + m.getAwayTeamScore()).reversed();
        Comparator<Match> secondaryComparator = Comparator.comparing(Match::getStartTime).reversed();
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
