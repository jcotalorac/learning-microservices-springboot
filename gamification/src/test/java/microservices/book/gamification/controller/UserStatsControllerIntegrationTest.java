package microservices.book.gamification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.BDDMockito.given;

@WebMvcTest(UserStatsController.class)
public class UserStatsControllerIntegrationTest {

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<GameStats> json;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, ObjectMapper::new);
    }

    @Test
    public void getStatsForUser() throws Exception {
        Long userId = 2L;
        int userScore = 100;

        given(gameService.retrieveStatsForUser(userId))
                .willReturn(new GameStats(userId, userScore,
                        List.of(Badge.BRONZE_MULTIPLICATOR, Badge.SILVER_MULTIPLICATOR)));

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/stats"))
                .andReturn().getResponse();
    }
}