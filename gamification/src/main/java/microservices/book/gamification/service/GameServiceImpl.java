package microservices.book.gamification.service;

import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class GameServiceImpl implements GameService {
    private ScoreCardRepository scoreCardRepository;
    private BadgeCardRepository badgeCardRepository;

    @Autowired
    public GameServiceImpl(ScoreCardRepository scoreCardRepository,
                           BadgeCardRepository badgeCardRepository) {
        this.scoreCardRepository = scoreCardRepository;
        this.badgeCardRepository = badgeCardRepository;
    }

    @Override
    public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {
        if (correct) {
            ScoreCard scoreCard = new ScoreCard(userId, attemptId);
            scoreCardRepository.save(scoreCard);
            log.info("User with id {} scored {} points for attempt id {}", userId,
                    scoreCard.getScore(), attemptId);
            List<Badge> badges = processForBadges(userId);
            return new GameStats(userId, scoreCard.getScore(), badges);
        }
        return GameStats.emptyStats(userId);
    }

    private List<Badge> processForBadges(Long userId) {
        int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
        log.info("New score for user {} is {}", userId, totalScore);

        List<Badge> badges = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
        return badges;
    }

    @Override
    public GameStats retrieveStatsForUser(Long userId) {
        return GameStats.emptyStats(userId);
    }
}
