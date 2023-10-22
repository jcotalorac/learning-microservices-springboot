package microservices.book.gamification.service;

import microservices.book.gamification.repository.ScoreCardRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LeaderBoardServiceImplTest {

    @Mock
    private ScoreCardRepository scoreCardRepository;

    private LeaderBoardServiceImpl leaderBoardService;

    @BeforeAll
    public void setUp() {
        initMocks(this);
        leaderBoardService = new LeaderBoardServiceImpl();
    }

    @Test
    public void retrieveLeaderBoardTest() {
    }
}