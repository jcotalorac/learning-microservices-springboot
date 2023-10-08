package microservices.book.gamification.domain;

import java.util.List;

public final class GameStats {

    private final Long userId;
    private final int score;
    private final List<Badge> badges;
}
