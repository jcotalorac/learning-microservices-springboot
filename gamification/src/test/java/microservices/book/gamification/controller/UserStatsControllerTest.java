package microservices.book.gamification.controller;

import microservices.book.gamification.service.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class UserStatsControllerTest {

    @Mock
    private GameServiceImpl gameService;
    private UserStatsController userStatsController;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        userStatsController = new UserStatsController(gameService);
    }
}