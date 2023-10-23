package microservices.book.gamification.service;

import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.repository.ScoreCardRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LeaderBoardServiceImplTest {

    @Mock
    private ScoreCardRepository scoreCardRepository;

    private LeaderBoardServiceImpl leaderBoardService;

    @BeforeAll
    public void setUp() {
        initMocks(this);
        leaderBoardService = new LeaderBoardServiceImpl(scoreCardRepository);
    }

    @Test
    public void retrieveLeaderBoardTest() {

        given(scoreCardRepository.findFirst10())
                .willReturn(IntStream.range(1, 11)
                        .mapToObj(i -> new LeaderBoardRow((long) i, (long) (10 - i)))
                        .collect(Collectors.toList()));

        List<LeaderBoardRow> currentLeaderBoard = leaderBoardService.getCurrentLeaderBoard();

        assertThat(currentLeaderBoard.size()).isEqualTo(10);
    }
}