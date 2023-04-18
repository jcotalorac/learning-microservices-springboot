package microservices.book.multiplication.controller;

import microservices.book.multiplication.service.MultiplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MultiplicationResultAttemptController.class)
public class MultiplicationResultAttemptControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postResultReturnCorrect(){
        genericParameterizedTest(true);
    }

    @Test
    public void postResultReturnNotCorrect(){
        genericParameterizedTest(false);
    }

    private void genericParameterizedTest(boolean b) {
    }
}
