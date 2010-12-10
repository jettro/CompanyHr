package nl.gridshore.companyhr.query.user;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import nl.gridshore.companyhr.app.api.user.UserCreatedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jettro Coenradie
 */
@Component
public class UserEventListener {
    private ObjectifyFactory objectifyFactory;

    @EventHandler
    public void handleCreate(UserCreatedEvent event) {
        Objectify objectify = objectifyFactory.beginTransaction();
        try {
            objectify.put(new UserEntry(event.userIdentifier(), event.getDisplayName(), event.getEmail()));
            objectify.getTxn().commit();
        } finally {
            if (objectify.getTxn().isActive()) {
                objectify.getTxn().rollback();
            }
        }
    }


    @Autowired
    public void setObjectifyFactory(ObjectifyFactory objectifyFactory) {
        this.objectifyFactory = objectifyFactory;
    }

}
