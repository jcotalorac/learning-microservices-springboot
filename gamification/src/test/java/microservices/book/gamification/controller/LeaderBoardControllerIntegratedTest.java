package microservices.book.gamification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.gamification.domain.LeaderBoardRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(LeaderBoardControllerIntegratedTest.class)
public class LeaderBoardControllerIntegratedTest {

    @Autowired
    private MockMvc mvc;

    private JacksonTester<List<LeaderBoardRow>> json;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, ObjectMapper::new);
    }

    @Test
    public void getLeaderBoard() {
    }
}