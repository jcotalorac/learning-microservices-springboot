package microservices.book.gamification.repository;

import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {

    List<Badge> findByUserIdOrderByBadgeTimestampDesc(final Long userId);
}
