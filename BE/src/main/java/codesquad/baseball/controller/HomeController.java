package codesquad.baseball.controller;

import codesquad.baseball.ApiResponse;
import codesquad.baseball.DTO.PlayerDTO;
import codesquad.baseball.DTO.TeamDTO;
import codesquad.baseball.DTO.TeamHistoryDTO;
import codesquad.baseball.domain.History;
import codesquad.baseball.domain.Match;
import codesquad.baseball.domain.Team;
import codesquad.baseball.repository.HistoryRepository;
import codesquad.baseball.repository.MatchRepository;
import codesquad.baseball.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;

@RestController
public class HomeController {
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public HomeController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping
    public ResponseEntity home() {
        List<Team> teamList = (List<Team>) teamRepository.findAll();
        return new ResponseEntity(teamList, HttpStatus.OK);
    }

    @PostMapping
    public RedirectView home(@RequestBody HashMap<String, String> teamInfo) {
        Long myTeamId = Long.valueOf(teamInfo.get("myTeamId"));
        Long counterTeamId = Long.valueOf(teamInfo.get("counterTeamId"));
        boolean isHome = Boolean.parseBoolean(teamInfo.get("isHome"));
        Match match = new Match(myTeamId, counterTeamId, isHome);
        Long matchId = matchRepository.save(match).getId();
        return new RedirectView("/game/" + matchId);
    }

    @GetMapping("/game/{matchId}")
    public ResponseEntity<ApiResponse> game(@PathVariable Long matchId) {
        System.out.println("matchId: " + matchId);
        Match match = matchRepository.findById(matchId).orElseThrow(RuntimeException::new);
        Team myTeam = teamRepository.findById(match.getMyTeamId()).orElseThrow(RuntimeException::new);
        Team counterTeam = teamRepository.findById(match.getCounterTeamId()).orElseThrow(RuntimeException::new);

        List<History> historyList = myTeam.getHistoryList();
        TeamDTO teamLeft = new TeamDTO(myTeam.getName(), 5);
        TeamDTO teamRight = new TeamDTO(counterTeam.getName(), 5);

        PlayerDTO Pitcher = new PlayerDTO("Pitcher", "Jung", 39, 1, 0);
        PlayerDTO Hitter = new PlayerDTO("Hitter", "Jane", 0, 1, 0);

        ApiResponse apiResponse = new ApiResponse(teamLeft, teamRight, Pitcher, Hitter, new TeamHistoryDTO(myTeam));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
