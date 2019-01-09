package cyclone.lunchvoting.repository;

import cyclone.lunchvoting.dto.RestaurantVotes;
import cyclone.lunchvoting.entity.User;
import cyclone.lunchvoting.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    void deleteByUserAndDate(User user, LocalDate date);

    @Query(value = "select t.id, t.name, t.votes " +
            "from ( " +
            "       select r.id, r.name, count(v.id_user) as votes " +
            "       from restaurants r, " +
            "            user_votes v " +
            "       where v.id_restaurant = r.id " +
            "         and v.date = ?1 " +
            "       group by r.id, r.name " +
            "     ) t " +
            "where t.votes = " +
            "      (select max(votes) " +
            "       from (select count(v2.id_user) as votes " +
            "             from user_votes v2 " +
            "             where v2.date = ?1 " +
            "             group by v2.id_restaurant)) ", nativeQuery = true)
    List<RestaurantVotes> getVotingWinners(LocalDate date);

}
