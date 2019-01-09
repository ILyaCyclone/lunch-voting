package cyclone.lunchvoting.web;

import cyclone.lunchvoting.dto.RestaurantVotes;
import cyclone.lunchvoting.dto.VotingStatus;
import cyclone.lunchvoting.entity.Restaurant;
import cyclone.lunchvoting.service.RestaurantService;
import cyclone.lunchvoting.service.VoteService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cyclone.lunchvoting.config.CacheConfiguration.VOTING_CHOICE_CACHE;
import static cyclone.lunchvoting.util.DateTimeUtils.*;
import static cyclone.lunchvoting.web.UserController.URL;

@RestController
@RequestMapping(URL)
public class UserController {
    static final String URL = "/api/user";

    private VoteService voteService;
    private RestaurantService restaurantService;

    public UserController(VoteService voteService, RestaurantService restaurantService) {
        this.voteService = voteService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/list")
    @Cacheable(value = VOTING_CHOICE_CACHE, keyGenerator = "todayCacheKeyGenerator")
    public List<Restaurant> getTodaysRestaurants() {
        return restaurantService.findAllWithMenuByDate(today());
    }


    @PostMapping("/vote/{idRestaurant}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void vote(@PathVariable int idRestaurant) {
        int currentUserId = SecurityUtil.getCurrentUserId();
        voteService.vote(currentUserId, idRestaurant, dateTime());
    }

    @GetMapping("/voting-status")
    public VotingStatus getVotingStatus() {
        return voteService.getVotingStatus(now());
    }



    @GetMapping("/voting-result")
    public List<RestaurantVotes> getVotingResult() {
        return voteService.getVotingResult(dateTime());
    }
}
