package nl.gridshore.companyhr.app.api.user;

import org.axonframework.domain.DomainEvent;

/**
 * @author Jettro Coenradie
 */
public class UserCreatedEvent extends DomainEvent {
    private String email;
    private String displayName;
    private boolean administrator;

    public UserCreatedEvent(String displayName, String email, boolean administrator) {
        this.displayName = displayName;
        this.email = email;
        this.administrator = administrator;
    }

    public String userIdentifier() {
        return getAggregateIdentifier().asString();
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdministrator() {
        return administrator;
    }
}
