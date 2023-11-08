package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.event.EventDispatcher;
import microservices.book.multiplication.event.MultiplicationSolvedEvent;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MultiplicationServiceImplTest {

    @Mock
    private RandomGeneratorService randomGeneratorService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MultiplicationResultAttemptRepository multiplicationResultAttemptRepository;
    @Mock
    private EventDispatcher eventDispatcher;
    private MultiplicationServiceImpl multiplicationService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        multiplicationService = new MultiplicationServiceImpl(randomGeneratorService,
                multiplicationResultAttemptRepository, userRepository, eventDispatcher);
    }

    @Test
    public void createRandomMultiplicationTest() {

        given(randomGeneratorService.generateRandomFactor())
                .willReturn(50, 30);

        Multiplication randomMultiplication = multiplicationService.createRandomMultiplication();

        assertThat(randomMultiplication.getFactorA()).isEqualTo(50);
        assertThat(randomMultiplication.getFactorB()).isEqualTo(30);
    }

    @Test
    public void checkCorrectAttemptTest() {

        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);

        MultiplicationSolvedEvent multiplicationSolvedEvent = new MultiplicationSolvedEvent(attempt.getId(),
                user.getId(), true);
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());

        boolean attemptResult = multiplicationService.checkAttempt(attempt);

        assertThat(attemptResult).isTrue();
        verify(multiplicationResultAttemptRepository).save(verifiedAttempt);
        verify(eventDispatcher).send(eq(multiplicationSolvedEvent));
    }

    @Test
    public void checkWrongAttemptTest() {

        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);

        MultiplicationSolvedEvent multiplicationSolvedEvent = new MultiplicationSolvedEvent(attempt.getId(),
                user.getId(), false);
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());

        boolean attemptResult = multiplicationService.checkAttempt(attempt);

        assertThat(attemptResult).isFalse();
        verify(multiplicationResultAttemptRepository).save(attempt);
        verify(eventDispatcher).send(eq(multiplicationSolvedEvent));
    }

    @Test
    public void retrieveStatsTest() {

        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication,
                3010, false);
        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication,
                3051, false);
        List<MultiplicationResultAttempt> latestAttempts = List.of(attempt1, attempt2);

        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());

        given(multiplicationResultAttemptRepository.findTop5ByUserAliasOrderByIdDesc("john_doe"))
                .willReturn(latestAttempts);

        List<MultiplicationResultAttempt> latestAttemptsResult = multiplicationService.getStatsForUser("john_doe");

        assertThat(latestAttemptsResult).isEqualTo(latestAttempts);
    }

    @Test
    public void getResultByIdTest() {

        Long attemptId = 1L;

        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication,
                3010, false);

        given(multiplicationResultAttemptRepository.findById(attemptId))
                .willReturn(Optional.of(attempt));

        MultiplicationResultAttempt multiplicationResultAttempt = multiplicationService.getResultById(attemptId);

        assertThat(multiplicationResultAttempt).isEqualTo(attempt);
    }
}
