package cyclone.lunchvoting.service;

import cyclone.lunchvoting.entity.Restaurant;
import cyclone.lunchvoting.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static cyclone.lunchvoting.config.CacheConfiguration.VOTING_CHOICE_CACHE;

@Service
public class RestaurantService {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    private RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Cacheable(value = VOTING_CHOICE_CACHE, key = "#date")
    public List<Restaurant> findAllWithMenuByDate(LocalDate date) {
        return restaurantRepository.findAllWithMenuByDate(date);
    }
}
