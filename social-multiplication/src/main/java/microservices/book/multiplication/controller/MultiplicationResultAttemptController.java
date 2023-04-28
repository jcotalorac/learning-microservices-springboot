package microservices.book.multiplication.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/results")
public final class MultiplicationResultAttemptController {

    private final MultiplicationService multiplicationService;

    @Autowired
    public MultiplicationResultAttemptController(final MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    @PostMapping
    public ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt attempt){
        boolean isCorrect = multiplicationService.checkAttempt(attempt);

        MultiplicationResultAttempt resultAttempt = new MultiplicationResultAttempt(attempt.getUser(),
                attempt.getMultiplication(), attempt.getResultAttempt(), isCorrect);
        return ResponseEntity.ok(resultAttempt);
    }

    @RequiredArgsConstructor
    @Getter
    static final class ResultResponse {

        private final boolean correct;
    }
}
