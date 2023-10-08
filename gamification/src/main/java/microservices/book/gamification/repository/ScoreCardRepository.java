package microservices.book.gamification.repository;

import microservices.book.gamification.domain.ScoreCard;
import org.springframework.data.repository.CrudRepository;

public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {
}
