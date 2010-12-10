package nl.gridshore.companyhr.app.api.user;

import org.axonframework.domain.DomainEvent;

/**
 * @author Jettro Coenradie
 */
public class UserCreatedEvent extends DomainEvent {
    private String email;
    private String displayName;

    public UserCreatedEvent(String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
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
}
