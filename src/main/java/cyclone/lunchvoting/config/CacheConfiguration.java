package cyclone.lunchvoting.config;

import cyclone.lunchvoting.entity.MenuItem;
import cyclone.lunchvoting.exception.NotFoundException;
import cyclone.lunchvoting.repository.MenuItemRepository;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
@EnableCaching
public class CacheConfiguration {
    public static final String VOTING_CHOICE_BY_DATE = "voting-choice-by-date";
    public static final String RESTAURANT_BY_ID_AND_MENU_DATE = "restaurant-by-id-and-menu-date";
    public static final String USER_BY_ID = "user-by-id";
    public static final String USER_BY_EMAIL = "user-by-email";

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
        return (Object target, Method method, Object... params) ->
                menuItemRepository.findById((Integer) params[0])
                        .orElseThrow(() -> new NotFoundException("Menu item ID " + params[0] + " not found"))
                        .getDate();
    }

    @Bean
    public KeyGenerator restaurantIdAndMenuDateByMenuItemKeyGenerator() {
        return (Object target, Method method, Object... params) ->
                SimpleKeyGenerator.generateKey(((MenuItem) params[0]).getRestaurant().getId(), ((MenuItem) params[0]).getDate());
    }

    @Bean
    public KeyGenerator restaurantIdAndMenuDateByMenuItemIdKeyGenerator() {
        return (Object target, Method method, Object... params) -> {
            Integer idMenuItem = (Integer) params[0];
            MenuItem menuItem = menuItemRepository.findById(idMenuItem).orElseThrow(() -> new NotFoundException("Menu item ID " + idMenuItem + " not found."));
            return SimpleKeyGenerator.generateKey(menuItem.getRestaurant().getId(), menuItem.getDate());
        };
    }
}
