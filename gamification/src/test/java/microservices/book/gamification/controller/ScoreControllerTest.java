package microservices.book.gamification.controller;

import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class ScoreControllerTest {

    @Mock
    private GameService gameService;

    private ScoreController scoreController;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        scoreController = new ScoreController(gameService);
    }
    @Test
    public void retrieveScoreForAttemptTest() {

        ScoreCard scoreCard = new ScoreCard();

        given(gameService.getScoreForAttempt(1L))
                .willReturn(scoreCard);

        ScoreCard score = scoreController.getScoreForAttempt(1L);

        assertThat(score).isNotNull();
        assertThat(score.getScore()).isEqualTo(0);
    }
}