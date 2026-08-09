package com.microfit.microfit.security;

import com.microfit.microfit.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Returns the User behind the currently authenticated request's JWT.
     * Every controller that needs to know "who is calling me" should go
     * through here rather than trusting a userId passed in the request body.
     */
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new IllegalStateException("No authenticated user found");
        }

        return principal.getUser();
    }
}
