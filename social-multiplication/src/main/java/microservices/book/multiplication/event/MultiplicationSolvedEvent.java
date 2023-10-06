package microservices.book.multiplication.event;

public class MultiplicationSolvedEvent {

    private final Long multiplicationResultAttemptId;
    private final Long userId;
    private final boolean correct;
}
