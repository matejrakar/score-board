package com.scoreboard;

import java.time.Instant;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Holds fields with information about a match and proprietary methods
 */
public class Match {
    private String homeTeamName;
    private String awayTeamName;
    private int homeTeamScore;
    private int awayTeamScore;
    private final Instant startTime;

    private static final Logger logger = Logger.getLogger(Match.class.getName());


    /**
     * Constructs a new match with given team names, with default team scores of 0 and Instant timestamp
     *
     * @param homeTeamName
     * @param awayTeamName
     * @throws IllegalArgumentException if team names are null or blank
     */
    public Match(String homeTeamName, String awayTeamName) {
        if (homeTeamName == null || awayTeamName == null
                || homeTeamName.isBlank() || awayTeamName.isBlank()) {
            logger.warning(Match.class.getName() + " constructor not initialized.");
            throw new IllegalArgumentException("Team names cannot be null or empty");
        }
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
        this.startTime = Instant.now();
    }

    /**
     * Updates the score of a match
     *
     * @param homeTeamScore
     * @param awayTeamScore
     * @throws IllegalArgumentException if scores are negative integers
     */
    public boolean updateScore (int homeTeamScore, int awayTeamScore) {
        boolean isUpdated = false;
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            logger.warning("Negative integers in updateScore method.");
            throw new IllegalArgumentException("Scores must be non-negative integers");
        }
        if (this.homeTeamScore != homeTeamScore || this.awayTeamScore != awayTeamScore) {
            this.setHomeTeamScore(homeTeamScore);
            this.setAwayTeamScore(awayTeamScore);
            isUpdated = true;
        }
        logger.info("Scores updated successfully");
        return isUpdated;
    }

    /**
     * Compares two Match objects, which are considered equal if homeTeamName and awayTeamName fields are equal
     *
     * @param obj
     * @return true if objects are the same and false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Match match = (Match) obj;
        if (this.homeTeamName == match.getHomeTeamName() && this.awayTeamName == match.getAwayTeamName()) {
            return true;
        }
        return false;
    }

    /**
     * HashCode is a hash of homeTeamName and awayTeamName, which makes a match unique
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(homeTeamName, awayTeamName);
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public Instant getStartTime() {
        return startTime;
    }
}
