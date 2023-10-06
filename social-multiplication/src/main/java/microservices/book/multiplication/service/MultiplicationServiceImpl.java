package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.event.EventDispatcher;
import microservices.book.multiplication.event.MultiplicationSolvedEvent;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private RandomGeneratorService randomGeneratorService;

    private UserRepository userRepository;

    private MultiplicationResultAttemptRepository multiplicationResultAttemptRepository;

    private EventDispatcher eventDispatcher;

    @Autowired
    public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService,
                                     MultiplicationResultAttemptRepository multiplicationResultAttemptRepository,
                                     UserRepository userRepository,
                                     EventDispatcher eventDispatcher) {
        this.randomGeneratorService = randomGeneratorService;
        this.multiplicationResultAttemptRepository = multiplicationResultAttemptRepository;
        this.userRepository = userRepository;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Override
    public boolean checkAttempt(MultiplicationResultAttempt multiplicationResultAttempt) {

        Optional<User> user = userRepository.findByAlias(multiplicationResultAttempt.getUser().getAlias());

        int product = multiplicationResultAttempt.getMultiplication().getFactorA()
                * multiplicationResultAttempt.getMultiplication().getFactorB();

        boolean correct = multiplicationResultAttempt.getResultAttempt() == product;

        Assert.isTrue(!multiplicationResultAttempt.isCorrect(), "You can't send an attempt " +
                "marked as correct!!");

        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(user.orElse(multiplicationResultAttempt.getUser()),
                multiplicationResultAttempt.getMultiplication(), multiplicationResultAttempt.getResultAttempt(),
                correct);

        multiplicationResultAttemptRepository.save(checkedAttempt);

        eventDispatcher.send(new MultiplicationSolvedEvent(checkedAttempt.getId(),
                checkedAttempt.getId(), checkedAttempt.isCorrect()));
        return correct;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
        return multiplicationResultAttemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }
}
