package cyclone.lunchvoting.service;

import cyclone.lunchvoting.entity.Restaurant;
import cyclone.lunchvoting.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantService {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    private RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> findAllWithMenuByDate(LocalDate date) {
        return restaurantRepository.findAllWithMenuByDate(date);
    }
}
