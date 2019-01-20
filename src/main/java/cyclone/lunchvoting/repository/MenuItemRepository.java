package cyclone.lunchvoting.repository;

import cyclone.lunchvoting.entity.MenuItem;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static cyclone.lunchvoting.config.CacheConfiguration.RESTAURANT_BY_ID_AND_MENU_DATE;
import static cyclone.lunchvoting.config.CacheConfiguration.VOTING_CHOICE_BY_DATE;

@RepositoryRestResource(path = "/menu")
@Transactional
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    @Override
//    @CacheEvict(value = VOTING_CHOICE_BY_DATE, allEntries = true)
    @Caching(evict = {
            @CacheEvict(value = VOTING_CHOICE_BY_DATE, key = "#p0.date")
            , @CacheEvict(value = RESTAURANT_BY_ID_AND_MENU_DATE, allEntries = true)
    })
    <S extends MenuItem> S save(S s);

    @Override
//    @RestResource(exported = false)
//    @CacheEvict(value = VOTING_CHOICE_BY_DATE, allEntries = true)
//    @CacheEvict(value = VOTING_CHOICE_BY_DATE, key = "#menuItem.date")
    @Caching(evict = {
            @CacheEvict(value = VOTING_CHOICE_BY_DATE, key = "#p0.date")
            , @CacheEvict(value = RESTAURANT_BY_ID_AND_MENU_DATE, allEntries = true)
    })
    void delete(MenuItem menuItem);

    @Override
//    @CacheEvict(value = VOTING_CHOICE_BY_DATE, allEntries = true)
//    @CacheEvict(value = VOTING_CHOICE_BY_DATE, key = "findById(#id)")
    @Caching(evict = {
            @CacheEvict(value = VOTING_CHOICE_BY_DATE, keyGenerator = "menuDateByIdKeyGenerator")
            , @CacheEvict(value = RESTAURANT_BY_ID_AND_MENU_DATE, allEntries = true)
    })
    void deleteById(Integer id);



    @Override
    Optional<MenuItem> findById(Integer integer);
}
