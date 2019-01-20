package cyclone.lunchvoting.security;

import cyclone.lunchvoting.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthenticatedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        return new AuthenticatedUser(userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("User "+email+" not found")));
    }
}
