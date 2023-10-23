package microservices.book.gamification.controller;

import microservices.book.gamification.service.LeaderBoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LeaderBoardControllerTest {

    @Mock
    private LeaderBoardServiceImpl leaderBoardService;

    private LeaderBoardController leaderBoardController;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        leaderBoardController = new LeaderBoardController(leaderBoardService);
    }

    @Test
    public void retrieveLeaderBoardTest() {}
}