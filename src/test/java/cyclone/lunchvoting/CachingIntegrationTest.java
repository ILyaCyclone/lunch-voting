package cyclone.lunchvoting;

import cyclone.lunchvoting.config.CacheConfiguration;
import cyclone.lunchvoting.entity.Restaurant;
import cyclone.lunchvoting.repository.RestaurantRepository;
import cyclone.lunchvoting.util.DateTimeUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class CachingIntegrationTest {
    // Your repository interface
//    interface RestaurantRepositoryMock extends RestaurantRepository {
//
//        @Cacheable("sample")
//        Object findByEmail(String email);
//    }

    @Configuration
    @EnableCaching
    static class Config {

        // Simulating your caching configuration
        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager(CacheConfiguration.RESTAURANT_BY_ID_AND_MENU_DATE);
        }

        // A repository mock instead of the real proxy
        @Bean
        RestaurantRepository myRepo() {
            return Mockito.mock(RestaurantRepository.class);
        }
    }

    @Autowired
    CacheManager manager;
    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    void testFindByIdAndMenuDate() {
        Integer id1 = 1;
        Integer id2 = 2;
        LocalDate today = DateTimeUtils.today();
        Optional<Restaurant> rest1 = Optional.of(new Restaurant());
        Optional<Restaurant> rest2 = Optional.of(new Restaurant());

        Mockito.when(restaurantRepository.findByIdAndMenuDate(id1, today)).thenReturn(rest1);
        Mockito.when(restaurantRepository.findByIdAndMenuDate(id2, today)).thenReturn(rest2);

        restaurantRepository.findByIdAndMenuDate(id1, today);
        restaurantRepository.findByIdAndMenuDate(id1, today);

//        Mockito.verify(restaurantRepository, Mockito.times(1)).findByIdAndMenuDate(Mockito.anyInt(), Mockito.any(LocalDate.class));

        restaurantRepository.findByIdAndMenuDate(id2, today);
        restaurantRepository.findByIdAndMenuDate(id2, today);

//        Mockito.verify(restaurantRepository, Mockito.times(1)).findByIdAndMenuDate(id1, today);
//        Mockito.verify(restaurantRepository, Mockito.times(1)).findByIdAndMenuDate(id2, today);
        Mockito.verify(restaurantRepository, Mockito.times(2)).findByIdAndMenuDate(Mockito.anyInt(), Mockito.any(LocalDate.class));


    }

//    @Test
//    public void methodInvocationShouldBeCached() {
//
//        Object first = new Object();
//        Object second = new Object();
//
//        // Set up the mock to return *different* objects for the first and second call
//        Mockito.when(restaurantRepository.findByEmail(Mockito.any(String.class))).thenReturn(first, second);
//
//        // First invocation returns object returned by the method
//        Object result = restaurantRepository.findByEmail("foo");
//        assertThat(result, is(first));
//
//        // Second invocation should return cached value, *not* second (as set up above)
//        result = restaurantRepository.findByEmail("foo");
//        assertThat(result, is(first));
//
//        // Verify repository method was invoked once
//        Mockito.verify(restaurantRepository, Mockito.times(1)).findByEmail("foo");
//        assertThat(manager.getCache("sample").get("foo"), is(notNullValue()));
//
//        // Third invocation with different key is triggers the second invocation of the restaurantRepository method
//        result = restaurantRepository.findByEmail("bar");
//        assertThat(result, is(second));
//    }
}