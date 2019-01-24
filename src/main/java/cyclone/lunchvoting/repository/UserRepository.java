package cyclone.lunchvoting.repository;

import cyclone.lunchvoting.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static cyclone.lunchvoting.config.CacheConfiguration.USER_BY_EMAIL;
import static cyclone.lunchvoting.config.CacheConfiguration.USER_BY_ID;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Cacheable(value = USER_BY_EMAIL)
    Optional<User> findByEmailIgnoreCase(String email);

    @Override
    @Cacheable(value = USER_BY_ID)
    Optional<User> findById(Integer integer);
}
