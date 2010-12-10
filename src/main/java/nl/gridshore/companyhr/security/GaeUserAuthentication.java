package nl.gridshore.companyhr.security;

import nl.gridshore.companyhr.query.user.UserEntry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Jettro Coenradie
 */
public class GaeUserAuthentication implements Authentication {
    private final UserEntry principal;
    private final Object details;
    private boolean authenticated;

    public GaeUserAuthentication(UserEntry principal, Object details) {
        this.principal = principal;
        this.details = details;
        authenticated = true;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();

        for (AppRole role:principal.getAuthorities()) {
            grantedAuthorities.add(new GrantedAuthorityImpl(role.getAuthority()));
        }

        return grantedAuthorities;
    }

    public Object getCredentials() {
        throw new UnsupportedOperationException();
    }

    public Object getDetails() {
        return null;
    }

    public Object getPrincipal() {
        return principal;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        authenticated = isAuthenticated;
    }

    public String getName() {
        return principal.getUserName();
    }

    @Override
    public String toString() {
        return "GaeUserAuthentication{" +
                "principal=" + principal +
                ", details=" + details +
                ", authenticated=" + authenticated +
                '}';
    }
}
