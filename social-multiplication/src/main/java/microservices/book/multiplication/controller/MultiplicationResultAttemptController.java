package microservices.book.multiplication.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import microservices.book.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequiredArgsConstructor
    @Getter
    static final class ResultResponse {

        private final boolean correct;
    }
}
