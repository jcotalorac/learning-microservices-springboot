package microservices.book.gamification.controller;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class UserStatsControllerTest {

    private UserStatsController userStatsController;

    @BeforeEach
    public void setUp() {
        userStatsController = new UserStatsController();
    }
}