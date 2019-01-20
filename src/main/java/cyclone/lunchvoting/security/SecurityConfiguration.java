package cyclone.lunchvoting.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Override
//    public void configure(WebSecurity web) {
//        web
//                .ignoring()
//                .antMatchers("/h2-console", "/h2-console/**");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/api/user/**").hasRole("USER")
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .httpBasic();
    }
}
