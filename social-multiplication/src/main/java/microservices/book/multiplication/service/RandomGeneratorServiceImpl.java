package microservices.book.multiplication.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomGeneratorServiceImpl implements RandomGeneratorService {
    private static final int MINIMUM_FACTOR = 11;
    private static final int MAXIMUM_FACTOR = 99;

    @Override
    public int generateRandomFactor() {
        return new SecureRandom().nextInt(MINIMUM_FACTOR, MAXIMUM_FACTOR + 1);
    }
}
