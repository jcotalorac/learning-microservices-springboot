package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MultiplicationServiceImplTest {

    @Mock
    private RandomGeneratorService randomGeneratorService;
    private MultiplicationServiceImpl multiplicationService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        multiplicationService = new MultiplicationServiceImpl(randomGeneratorService);
    }

    @Test
    public void createRandomMultiplication() {

        given(randomGeneratorService.generateRandomFactor())
                .willReturn(50, 30);

        Multiplication randomMultiplication = multiplicationService.createRandomMultiplication();

        assertThat(randomMultiplication.getFactorA()).isEqualTo(50);
        assertThat(randomMultiplication.getFactorB()).isEqualTo(30);
        assertThat(randomMultiplication.getResult()).isEqualTo(1500);
    }
}