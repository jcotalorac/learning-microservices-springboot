package microservices.book.gamification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebMvcTest(ScoreController.class)
public class ScoreControllerIntegrationTest {

    @MockBean
    private GameService gameService;

    private JacksonTester<ScoreCard> json;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, ObjectMapper::new);
    }

    @Test
    public void successfulScoreForAttemptTest() throws Exception {

        ScoreCard scoreCard = new ScoreCard();

        given(gameService.getScoreForAttempt(1L))
                .willReturn(scoreCard);

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                .get("/scores/{attemptId}", 1))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(json.write(scoreCard).getJson());

    }
}
