package microservices.book.multiplication.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/results")
public final class MultiplicationResultAttemptController {

    private final MultiplicationService multiplicationService;

    private int serverPort;

    @Autowired
    public MultiplicationResultAttemptController(final MultiplicationService multiplicationService,
                                                 @Value("${server.port}") int serverPort) {
        this.multiplicationService = multiplicationService;
        this.serverPort = serverPort;
    }

    @PostMapping
    public ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt attempt){
        boolean isCorrect = multiplicationService.checkAttempt(attempt);

        MultiplicationResultAttempt resultAttempt = new MultiplicationResultAttempt(attempt.getUser(),
                attempt.getMultiplication(), attempt.getResultAttempt(), isCorrect);
        return ResponseEntity.ok(resultAttempt);
    }

    @GetMapping
    public ResponseEntity<List<MultiplicationResultAttempt>> getStatistics(@RequestParam("alias") String alias) {
        return ResponseEntity.ok(multiplicationService.getStatsForUser(alias));
    }

    @GetMapping("/{idResult}")
    public ResponseEntity<MultiplicationResultAttempt> getResultById(@PathVariable Long idResult) {
        log.info("Retrieving result {} from server @ {}", idResult, serverPort);
        return ResponseEntity.ok(multiplicationService.getResultById(idResult));
    }

    @RequiredArgsConstructor
    @Getter
    static final class ResultResponse {

        private final boolean correct;
    }
}
