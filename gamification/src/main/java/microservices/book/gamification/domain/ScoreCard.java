package microservices.book.gamification.domain;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class ScoreCard {

    private static final int DEFAULT_SCORE = 10;

    private final Long cardId;
    private final Long userId;
    private final Long attemptId;
    private final long scoreTimestamp;
    private final int score;
}
