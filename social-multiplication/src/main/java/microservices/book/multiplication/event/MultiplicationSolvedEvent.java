package microservices.book.multiplication.event;

import java.io.Serializable;

public class MultiplicationSolvedEvent implements Serializable {

    private final Long multiplicationResultAttemptId;
    private final Long userId;
    private final boolean correct;
}
