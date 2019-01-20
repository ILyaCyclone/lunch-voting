package cyclone.lunchvoting.repository;

import cyclone.lunchvoting.entity.Restaurant;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static cyclone.lunchvoting.config.CacheConfiguration.RESTAURANT_BY_ID_AND_MENU_DATE;
import static cyclone.lunchvoting.config.CacheConfiguration.VOTING_CHOICE_BY_DATE;

//@CrossOrigin
@RepositoryRestResource
@Transactional
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Cacheable(value = VOTING_CHOICE_BY_DATE)
    @Query("SELECT distinct r FROM Restaurant r INNER JOIN FETCH r.menu m WHERE m.date = ?1 order by r.name")
    List<Restaurant> findAllWithMenuByDate(LocalDate date);

    @RestResource(path = "findByName", rel = "findByName")
    List<Restaurant> findByNameContainingIgnoreCaseOrderByName(String name);

    @Cacheable(value = RESTAURANT_BY_ID_AND_MENU_DATE)
    Optional<Restaurant> findByIdAndMenuDate(Integer id, LocalDate localDate);



    @Override
    @CacheEvict(value = {VOTING_CHOICE_BY_DATE, RESTAURANT_BY_ID_AND_MENU_DATE}, allEntries = true)
    <S extends Restaurant> S save(S s);

    @Override
    @CacheEvict(value = {VOTING_CHOICE_BY_DATE, RESTAURANT_BY_ID_AND_MENU_DATE}, allEntries = true)
    void delete(Restaurant restaurant);

    @Override
    @CacheEvict(value = {VOTING_CHOICE_BY_DATE, RESTAURANT_BY_ID_AND_MENU_DATE}, allEntries = true)
    void deleteById(Integer integer);
}
