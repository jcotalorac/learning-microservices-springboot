package microservices.book.gamification.service;

import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class GameServiceImpl implements GameService {
    private ScoreCardRepository scoreCardRepository;
    private BadgeCardRepository badgeCardRepository;

    @Autowired
    public GameServiceImpl(ScoreCardRepository scoreCardRepository) {
        this.scoreCardRepository = scoreCardRepository;
    }

    @Override
    public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {
        int totalScoreForUser = scoreCardRepository.getTotalScoreForUser(userId);
        List<Badge> badges = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
        return new GameStats(userId, totalScoreForUser, badges);
    }

    @Override
    public GameStats retrieveStatsForUser(Long userId) {
        return null;
    }
}
