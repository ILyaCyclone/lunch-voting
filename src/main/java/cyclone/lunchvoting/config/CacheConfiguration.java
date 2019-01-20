package cyclone.lunchvoting.config;

import cyclone.lunchvoting.exception.NotFoundException;
import cyclone.lunchvoting.repository.MenuItemRepository;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
@EnableCaching
public class CacheConfiguration {
    public static final String VOTING_CHOICE_BY_DATE = "voting-choice-by-date";
    public static final String RESTAURANT_BY_ID_AND_MENU_DATE = "restaurant-by-id-and-menu-date";
    public static final String USER_BY_ID = "user-by-id";

    private final MenuItemRepository menuItemRepository;

    public CacheConfiguration(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

//    @Bean
//    public KeyGenerator todayCacheKeyGenerator() {
//        return (Object target, Method method, Object... params) -> DateTimeUtils.today();
//    }

    @Bean
    public KeyGenerator menuDateByIdKeyGenerator() {
        return (Object target, Method method, Object... params) -> {
            return menuItemRepository.findById((Integer) params[0])
                    .orElseThrow(() -> new NotFoundException("Menu item ID " + params[0] + " not found"))
                    .getDate();
        };
    }
}
