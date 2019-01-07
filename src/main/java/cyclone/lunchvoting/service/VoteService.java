package cyclone.lunchvoting.service;

import cyclone.lunchvoting.dto.VotingStatus;
import cyclone.lunchvoting.entity.Restaurant;
import cyclone.lunchvoting.entity.User;
import cyclone.lunchvoting.entity.Vote;
import cyclone.lunchvoting.exception.NotFoundException;
import cyclone.lunchvoting.exception.VotingIsNotActiveException;
import cyclone.lunchvoting.repository.RestaurantRepository;
import cyclone.lunchvoting.repository.UserRepository;
import cyclone.lunchvoting.repository.VoteRepository;
import cyclone.lunchvoting.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class VoteService {

    private VoteRepository voteRepository;
    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;


    @Value("${cyclone.lunchvoting.voting-ends}")
    private String votingEnds;

    public VoteService(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public void vote(int idUser, int idRestaurant, LocalDateTime dateTime) {
        if (!getVotingStatus(dateTime.toLocalTime()).isVotingActive()) {
            throw new VotingIsNotActiveException("Voting is not active at " + dateTime);
        } else {
            User user;
            try {
                user = userRepository.getOne(idUser);
            } catch (EntityNotFoundException e) {
                throw new NotFoundException("User ID" + idUser + " not found.");
            }
            // clear other today's user votes
            voteRepository.deleteByUserAndDate(user, dateTime.toLocalDate());

            Restaurant restaurantRef;
            try {
                restaurantRef = restaurantRepository.getOne(idRestaurant);
            } catch (EntityNotFoundException e) {
                throw new NotFoundException("Restaurant ID" + idRestaurant + " not found.");
            }

            Vote vote = new Vote(user, restaurantRef, dateTime.toLocalDate());
            voteRepository.save(vote);
        }
    }

    public VotingStatus getVotingStatus(LocalTime time) {
        return new VotingStatus(time, getVotingEndTime(), isVotingActive(time));
    }

    private boolean isVotingActive(LocalTime time) {
        return getVotingEndTime().isAfter(time);
    }

    private LocalTime getVotingEndTime() {
        return DateTimeUtils.hhDashMmToLocalTime(votingEnds);
    }
}
