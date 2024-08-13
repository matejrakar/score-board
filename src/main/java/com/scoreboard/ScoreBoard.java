package com.scoreboard;

import java.util.concurrent.ConcurrentHashMap;

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

    public ConcurrentHashMap<Integer, Match> getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(ConcurrentHashMap<Integer, Match> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
}
