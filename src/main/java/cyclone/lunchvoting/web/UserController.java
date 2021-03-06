package cyclone.lunchvoting.web;

import cyclone.lunchvoting.dto.RestaurantVotes;
import cyclone.lunchvoting.dto.VotingStatus;
import cyclone.lunchvoting.entity.Restaurant;
import cyclone.lunchvoting.service.RestaurantService;
import cyclone.lunchvoting.service.VoteService;
import cyclone.lunchvoting.service.VotingStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cyclone.lunchvoting.util.DateTimeUtils.*;
import static cyclone.lunchvoting.web.UserController.URL;

@RestController
@RequestMapping(URL)
public class UserController {
    static final String URL = "/api/user";

    private final VoteService voteService;
    private final VotingStatusService votingStatusService;
    private final RestaurantService restaurantService;

    public UserController(VoteService voteService, VotingStatusService votingStatusService, RestaurantService restaurantService) {
        this.voteService = voteService;
        this.votingStatusService = votingStatusService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/list")
//    @Cacheable(value = VOTING_CHOICE_BY_DATE, keyGenerator = "todayCacheKeyGenerator") // moved to repository layer
    public List<Restaurant> getTodaysRestaurants() {
        return restaurantService.findAllWithMenuByDate(today());
    }


    @PostMapping("/vote/{idRestaurant}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void vote(@PathVariable int idRestaurant) {
        Integer currentUserId = SecurityUtil.getCurrentUserId();
        if (currentUserId == null) {
            throw new AccessDeniedException("User ID not found.");
        }
        voteService.vote(currentUserId, idRestaurant, dateTime());
    }

    @GetMapping("/voting-status")
    public VotingStatus getVotingStatus() {
        return votingStatusService.getVotingStatus(now());
    }



    @GetMapping("/voting-result")
    public List<RestaurantVotes> getVotingResult() {
        return voteService.getVotingResult(dateTime());
    }
}
