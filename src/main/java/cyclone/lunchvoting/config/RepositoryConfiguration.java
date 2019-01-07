package cyclone.lunchvoting.config;

import cyclone.lunchvoting.entity.MenuItem;
import cyclone.lunchvoting.entity.Restaurant;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Restaurant.class, MenuItem.class);
    }
}