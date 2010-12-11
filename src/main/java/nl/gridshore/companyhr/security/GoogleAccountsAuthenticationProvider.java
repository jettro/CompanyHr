package nl.gridshore.companyhr.security;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import nl.gridshore.companyhr.query.user.UserEntry;
import nl.gridshore.companyhr.query.user.UserEntryProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * @author Jettro Coenradie
 */
public class GoogleAccountsAuthenticationProvider implements AuthenticationProvider {
    private UserEntryProvider userEntryProvider;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User googleUser = (User) authentication.getPrincipal();

        UserEntry user = userEntryProvider.findUser(googleUser.getUserId());

        if (user == null) {
            // User not in registry. Needs to register
            boolean admin = false;
            if (UserServiceFactory.getUserService().isUserAdmin()) {
                admin = true;
            }
            user = new UserEntry(googleUser.getUserId(), googleUser.getNickname(), googleUser.getEmail(),admin);
        }

        if (!user.isEnabled()) {
            throw new DisabledException("Account is disabled");
        }

        return new GaeUserAuthentication(user, authentication.getDetails());
    }

    public final boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserEntryProvider(UserEntryProvider userEntryProvider) {
        this.userEntryProvider = userEntryProvider;
    }
}
