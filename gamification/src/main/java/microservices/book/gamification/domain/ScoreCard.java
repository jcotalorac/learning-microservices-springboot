package microservices.book.gamification.domain;

public final class ScoreCard {

    private static final int DEFAULT_SCORE = 10;

    private final Long cardId;
    private final Long userId;
    private final Long attemptId;
    private final long scoreTimestamp;
    private final int score;
}
