package microservices.book.gamification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.service.LeaderBoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(LeaderBoardControllerIntegratedTest.class)
public class LeaderBoardControllerIntegratedTest {

    @MockBean
    private LeaderBoardService leaderBoardService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<List<LeaderBoardRow>> json;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, ObjectMapper::new);
    }

    @Test
    public void getLeaderBoardTest() throws Exception {
        given(leaderBoardService.getCurrentLeaderBoard())
                .willReturn(IntStream.range(1, 11)
                        .mapToObj(i -> new LeaderBoardRow((long) i, (long) (10 - i)))
                        .collect(Collectors.toList()));

        MockHttpServletResponse response = mvc.perform(get("/leaders")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
    }
}