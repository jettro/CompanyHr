package nl.gridshore.companyhr.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Jettro Coenradie
 */
public enum AppRole implements GrantedAuthority {
    ADMIN (0),
    NEW_USER (1),
    USER (2);

    private int bit;

    AppRole(int bit) {
        this.bit = bit;
    }

    public String getAuthority() {
        return toString();
    }
}
