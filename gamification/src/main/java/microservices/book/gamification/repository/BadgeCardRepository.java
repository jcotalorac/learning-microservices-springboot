package microservices.book.gamification.repository;

import microservices.book.gamification.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {
}
