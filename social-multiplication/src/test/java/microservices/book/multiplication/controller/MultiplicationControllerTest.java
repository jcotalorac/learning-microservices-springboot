package microservices.book.multiplication.controller;

import microservices.book.multiplication.service.MultiplicationService;
import org.springframework.boot.test.mock.mockito.MockBean;

public class MultiplicationControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;

}