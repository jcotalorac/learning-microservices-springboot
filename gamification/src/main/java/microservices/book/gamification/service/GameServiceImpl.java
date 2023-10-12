package microservices.book.gamification.service;

import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.repository.ScoreCardRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class GameServiceImpl implements GameService {

    @Autowired
    private ScoreCardRepository scoreCardRepository;

    @Override
    public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {
        int totalScoreForUser = scoreCardRepository.getTotalScoreForUser(userId);
        return new GameStats(userId, totalScoreForUser, null);
    }

    @Override
    public GameStats retrieveStatsForUser(Long userId) {
        return null;
    }
}
