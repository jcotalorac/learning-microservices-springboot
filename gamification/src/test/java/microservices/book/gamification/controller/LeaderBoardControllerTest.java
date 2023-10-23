package microservices.book.gamification.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LeaderBoardControllerTest {

    private LeaderBoardController leaderBoardController;

    @BeforeEach
    public void setUp() {
        leaderBoardController = new LeaderBoardController();
    }
}