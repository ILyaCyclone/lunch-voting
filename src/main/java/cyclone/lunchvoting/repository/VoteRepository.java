package cyclone.lunchvoting.repository;

import cyclone.lunchvoting.entity.User;
import cyclone.lunchvoting.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    void deleteByUserAndDate(User user, LocalDate date);

}
