package cyclone.lunchvoting.repository;

import cyclone.lunchvoting.entity.MenuItem;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static cyclone.lunchvoting.config.CacheConfiguration.VOTING_CHOICE_CACHE;

@RepositoryRestResource(path = "/menu")
@Transactional
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    @Override
    @CacheEvict(value = VOTING_CHOICE_CACHE, allEntries = true)
    <S extends MenuItem> S save(S s);

    @Override
//    @CacheEvict(value = VOTING_CHOICE_CACHE, key = "#menuItem.getDate()")
    @CacheEvict(value = VOTING_CHOICE_CACHE, allEntries = true)
    void delete(MenuItem menuItem);

    @Override
//    @CacheEvict(value = VOTING_CHOICE_CACHE, key = "findById(#id)")
    @CacheEvict(value = VOTING_CHOICE_CACHE, allEntries = true)
    void deleteById(Integer id);

    @Override
    Optional<MenuItem> findById(Integer integer);
}
