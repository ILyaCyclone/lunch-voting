package cyclone.lunchvoting.web;

import cyclone.lunchvoting.security.AuthenticatedUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

class SecurityUtil {
    private SecurityUtil(){}

    static Integer getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        return ((AuthenticatedUser)auth.getPrincipal()).getId();
    }
}
