package nl.gridshore.companyhr.query.user;

import nl.gridshore.companyhr.security.AppRole;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jettro Coenradie
 */
@Entity
public class UserEntry implements Serializable {
    private @Id String userName;
    private String email;
    private String displayName;
    private Set<AppRole> authorities;

    public UserEntry(String userName, String displayName, String email, boolean administrator) {
        this.userName = userName;
        this.email = email;
        this.displayName = displayName;
        authorities = new HashSet<AppRole>();
        authorities.add(AppRole.NEW_USER);
        if (administrator) {
            authorities.add(AppRole.ADMIN);
        }
    }

    public UserEntry() {
        // required by Objectify and spring forms
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<AppRole> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AppRole> authorities) {
        this.authorities = authorities;
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isAdministrator() {
        return authorities.contains(AppRole.ADMIN);
    }
}
