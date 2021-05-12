package codesquad.baseball.controller;

import codesquad.baseball.ApiResponse;
import codesquad.baseball.DTO.*;
import codesquad.baseball.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> initialize(@RequestBody HashMap<String, String> teamInfo) {
        ApiResponse apiResponse = gameService.getGameInfo(teamInfo);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/{matchId}/exchange")
    public ResponseEntity<ApiResponse> exchangePlayer(@PathVariable Long matchId, @RequestBody PlayerLogDTO playerLog) {
        ApiResponse apiResponse = gameService.exchangePlayer(matchId, playerLog);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{matchId}/playerListPopUp")
    public ResponseEntity<PlayerListPopUpDTO[]> showPlayerList(@PathVariable Long matchId) {
        return new ResponseEntity<>(gameService.getPlayerInfo(matchId), HttpStatus.OK);
    }

    @GetMapping("/{matchId}/detailScore")
    public ResponseEntity<TeamGameScoreDTO[]> showDetailScoreList(@PathVariable Long matchId) {
        return new ResponseEntity<>(gameService.getTeamGameScores(matchId), HttpStatus.OK);
    }
}
