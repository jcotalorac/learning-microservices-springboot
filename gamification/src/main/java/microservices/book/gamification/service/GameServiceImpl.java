package microservices.book.gamification.service;

import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.client.MultiplicationResultAttemptClient;
import microservices.book.gamification.client.MultiplicationResultAttemptClientImpl;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.BadgeCard;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GameServiceImpl implements GameService {
    public static final int LUCKY_NUMBER = 42;
    private ScoreCardRepository scoreCardRepository;
    private BadgeCardRepository badgeCardRepository;
    private MultiplicationResultAttemptClient multiplicationResultAttemptClient;

    @Autowired
    public GameServiceImpl(ScoreCardRepository scoreCardRepository,
                           BadgeCardRepository badgeCardRepository,
                           MultiplicationResultAttemptClientImpl multiplicationResultAttemptClient) {
        this.scoreCardRepository = scoreCardRepository;
        this.badgeCardRepository = badgeCardRepository;
        this.multiplicationResultAttemptClient = multiplicationResultAttemptClient;
    }

    @Override
    public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {
        if (correct) {
            ScoreCard scoreCard = new ScoreCard(userId, attemptId);
            scoreCardRepository.save(scoreCard);
            log.info("User with id {} scored {} points for attempt id {}", userId,
                    scoreCard.getScore(), attemptId);
            List<BadgeCard> badges = processForBadges(userId, attemptId);
            return new GameStats(userId, scoreCard.getScore(), badges.stream()
                    .map(BadgeCard::getBadge)
                    .collect(Collectors.toList()));
        }
        return GameStats.emptyStats(userId);
    }

    private List<BadgeCard> processForBadges(Long userId, Long attemptId) {
        int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
        log.info("New score for user {} is {}", userId, totalScore);

        List<BadgeCard> badgeCardsAssigned = new ArrayList<>();
        List<BadgeCard> badgeCardsRetrieved = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

        checkAndGiveBadgeBasedOnScore(badgeCardsRetrieved, Badge.BRONZE_MULTIPLICATOR, totalScore, 100,
                userId).ifPresent(badgeCardsAssigned::add);
        checkAndGiveBadgeBasedOnScore(badgeCardsRetrieved, Badge.SILVER_MULTIPLICATOR, totalScore, 500,
                userId).ifPresent(badgeCardsAssigned::add);
        checkAndGiveBadgeBasedOnScore(badgeCardsRetrieved, Badge.GOLD_MULTIPLICATOR, totalScore, 999,
                userId).ifPresent(badgeCardsAssigned::add);

        List<ScoreCard> scoreCards = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
        if (scoreCards.size() == 1 && !containsBadge(badgeCardsRetrieved, Badge.FIRST_WON)) {
            BadgeCard firstWonBadge = giveBadgeToUser(userId, Badge.FIRST_WON);
            badgeCardsAssigned.add(firstWonBadge);
        }

        MultiplicationResultAttempt resultAttempt = multiplicationResultAttemptClient.retrieveMultiplicationResultAttemptById(attemptId);

        if (resultAttempt.getMultiplicationFactorA() == LUCKY_NUMBER || resultAttempt.getMultiplicationFactorB() == LUCKY_NUMBER) {
            badgeCardsAssigned.add(new BadgeCard(userId, Badge.LUCKY_NUMBER));
        }
        return badgeCardsAssigned;
    }

    private boolean containsBadge(List<BadgeCard> badgeCards, Badge badge) {
        return badgeCards.stream()
                .anyMatch(badgeCard -> badgeCard.getBadge().equals(badge));
    }

    private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(List<BadgeCard> badges, Badge badge,
                                                              int score, int scoreThreshold,
                                                              Long userId) {
        if (score >= scoreThreshold && !containsBadge(badges, badge)) {
            return Optional.of(giveBadgeToUser(userId, badge));
        }
        return Optional.empty();
    }

    private BadgeCard giveBadgeToUser(Long userId, Badge badge) {
        BadgeCard badgeCard = new BadgeCard(userId, badge);
        badgeCardRepository.save(badgeCard);
        log.info("User with id {} won a new badge: {}", userId, badge);
        return badgeCard;
    }

    @Override
    public GameStats retrieveStatsForUser(Long userId) {
        int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
        List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
        return new GameStats(userId, totalScore, badgeCards.stream()
                .map(BadgeCard::getBadge)
                .collect(Collectors.toList()));
    }
}
