package microservices.book.gamification.controller;

import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.service.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.BDDMockito.given;
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

    @Test
    public void retrieveUserStats() {
        Long userId = 2L;

        given(gameService.retrieveStatsForUser(userId))
                .willReturn(new GameStats(userId, 100,
                        List.of(Badge.BRONZE_MULTIPLICATOR, Badge.SILVER_MULTIPLICATOR)));
    }
}