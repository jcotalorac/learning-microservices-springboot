package microservices.book.gamification.controller;

import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.service.LeaderBoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LeaderBoardControllerTest {

    @Mock
    private LeaderBoardServiceImpl leaderBoardService;

    private LeaderBoardController leaderBoardController;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        leaderBoardController = new LeaderBoardController(leaderBoardService);
    }

    @Test
    public void retrieveLeaderBoardTest() {

        given(leaderBoardService.getCurrentLeaderBoard())
                .willReturn(IntStream.range(1, 11)
                        .mapToObj(i -> new LeaderBoardRow((long) i, (long) (10 - i)))
                        .collect(Collectors.toList()));

        List<LeaderBoardRow> leaderBoard = leaderBoardController.getLeaderBoard();

        assertThat(leaderBoard.size()).isEqualTo(10);
        assertThat(leaderBoard).isSortedAccordingTo(Comparator
                .comparingLong(LeaderBoardRow::getTotalScore)
                .reversed());
    }
}