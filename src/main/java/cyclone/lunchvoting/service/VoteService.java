package cyclone.lunchvoting.service;

import cyclone.lunchvoting.dto.RestaurantVotes;
import cyclone.lunchvoting.entity.Restaurant;
import cyclone.lunchvoting.entity.User;
import cyclone.lunchvoting.entity.Vote;
import cyclone.lunchvoting.exception.NotFoundException;
import cyclone.lunchvoting.exception.VotingIsNotActiveException;
import cyclone.lunchvoting.exception.VotingIsNotFinishedException;
import cyclone.lunchvoting.repository.RestaurantRepository;
import cyclone.lunchvoting.repository.UserRepository;
import cyclone.lunchvoting.repository.VoteRepository;
import cyclone.lunchvoting.util.DateTimeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoteService {

    private VoteRepository voteRepository;
    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;

    private VotingStatusService votingStatusService;

    public VoteService(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, VotingStatusService votingStatusService) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.votingStatusService = votingStatusService;
    }

    @Transactional
//    @CacheEvict
    public void vote(int idUser, int idRestaurant, LocalDateTime dateTime) {
        if (!votingStatusService.isVotingActive(dateTime.toLocalTime())) {
            throw new VotingIsNotActiveException("Voting is not active at " + dateTime);
        } else {
//            user = userRepository.getOne(idUser); // doesn't throw Exception if not found
            User user = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException("User ID" + idUser + " not found."));
            // clear other today's user votes
            voteRepository.deleteByUserAndDate(user, dateTime.toLocalDate());

//            restaurantRef = restaurantRepository.getOne(idRestaurant); // doesn't throw Exception if not found
            Restaurant restaurant = restaurantRepository.findById(idRestaurant).orElseThrow(() -> new NotFoundException("Restaurant ID " + idRestaurant + " not found."));

            Vote vote = new Vote(user, restaurant, dateTime.toLocalDate());
            voteRepository.save(vote);
        }
    }

    public List<RestaurantVotes> getVotingResult(LocalDateTime dateTime) {
        if (DateTimeUtils.isPast(dateTime) || !votingStatusService.isVotingActive(dateTime.toLocalTime())) {
            return voteRepository.getVotingWinners(dateTime.toLocalDate());
        } else {
            throw new VotingIsNotFinishedException("Voting is not finished at " + dateTime + ".");
        }
    }
}
