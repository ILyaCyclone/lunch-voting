package cyclone.lunchvoting.config;

import cyclone.lunchvoting.util.DateTimeUtils;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
@EnableCaching
public class CacheConfiguration {
    public static final String VOTING_CHOICE_CACHE = "voting-choice";

    @Bean
    public KeyGenerator todayCacheKeyGenerator() {
        return (Object target, Method method, Object... params) -> DateTimeUtils.today();
    }
}
