package nl.gridshore.companyhr.query.user;

import com.googlecode.objectify.ObjectifyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jettro Coenradie
 */
@Component("userEntryProvider")
public class ObjectifyUserEntryProvider implements UserEntryProvider {
    private ObjectifyFactory objectifyFactory;

    @Override
    public UserEntry findUser(String userName) {
        return objectifyFactory.begin().find(UserEntry.class, userName);
    }

    @Autowired
    public void setObjectifyFactory(ObjectifyFactory objectifyFactory) {
        this.objectifyFactory = objectifyFactory;
    }
}
