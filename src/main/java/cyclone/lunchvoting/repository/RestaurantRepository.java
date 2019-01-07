package cyclone.lunchvoting.repository;

import cyclone.lunchvoting.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT distinct r FROM Restaurant r INNER JOIN FETCH r.menu m WHERE m.date = ?1 order by r.name")
    List<Restaurant> findAllWithMenuByDate(LocalDate date);

}
