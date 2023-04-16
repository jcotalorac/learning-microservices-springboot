package microservices.book.multiplication.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RandomGeneratorServiceTest {

    @Autowired
    private RandomGeneratorService randomGeneratorService;

    @Test
    public void generateRandomFactorIsBetweenExpectedLimits() {

        List<Integer> randomFactors = IntStream.range(0, 1000)
                .map(i -> randomGeneratorService.generateRandomFactor()).boxed()
                .collect(Collectors.toList());
    }
}