package microservices.book.gamification.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LeaderBoardServiceImplTest {

    private LeaderBoardServiceImpl leaderBoardService;

    @BeforeAll
    public void setUp() {
        leaderBoardService = new LeaderBoardServiceImpl();
    }

    @Test
    public void retrieveLeaderBoardTest() {
    }
}