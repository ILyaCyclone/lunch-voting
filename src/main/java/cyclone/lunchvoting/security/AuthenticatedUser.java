package cyclone.lunchvoting.security;


import cyclone.lunchvoting.entity.User;
import lombok.Getter;

@Getter
public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {
    private int id;

    public AuthenticatedUser(User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoles());
        this.id = user.getId();
    }


}
