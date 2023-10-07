package microservices.book.gamification.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class BadgeCard {

    private final Long badgeId;
    private final Long userId;
    private final long badgeTimestamp;
    private final Badge badge;
}
