import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {

    @Test
    public void constructor_ValidParameters_InitializeCorrectly() {
        Match match = new Match("home_team_name", "away_team_name");
        assertEquals(match.getHomeTeamName(), "home_team_name");
        assertEquals(match.getAwayTeamName(), "away_team_name");
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
        Match match1 = new Match ("home_team_name", "away_team_name");
        Match match2 = new Match ("home_team_name", "away_team_name");
        assertTrue(match1.equals(match2));
        assertTrue(match1.hashCode() == match2.hashCode());
    }

    @Test
    public void updateScore_ValidScores_CorrectScoresSet() {
        Match match = new Match ("home_team_name", "away_team_name");
        match.updateScore(1, 0);
        assertEquals(match.getHomeTeamScore(), 1);
        assertEquals(match.getAwayTeamScore(), 0);
    }

    @Test
    public void updateScore_InvalidScores_ExceptionThrown() {
        Match match = new Match ("home_team_name", "away_team_name");
        assertThrows(IllegalArgumentException.class, () -> match.updateScore(0, -1));
    }
}
