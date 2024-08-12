import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchTest {

    @Test
    public void startNewMatch() {
        Match match = new Match("home_team_name", "away_team_name");
        assertEquals(match.getHomeTeamName(), "home_team_name");
        assertEquals(match.getAwayTeamName(), "away_team_name");
        assertEquals(match.getHomeTeamScore(), 0);
        assertEquals(match.getAwayTeamScore(), 0);
    }

}
