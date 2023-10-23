package microservices.book.gamification.controller;

import microservices.book.gamification.domain.LeaderBoardRow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {

    @GetMapping
    public List<LeaderBoardRow> getLeaderBoard(){
        return null;
    }
}
