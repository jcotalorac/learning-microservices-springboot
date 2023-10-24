package microservices.book.gamification.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(LeaderBoardControllerIntegratedTest.class)
public class LeaderBoardControllerIntegratedTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void getLeaderBoard() {
    }
}