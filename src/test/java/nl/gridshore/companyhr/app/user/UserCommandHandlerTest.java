package nl.gridshore.companyhr.app.user;

import nl.gridshore.companyhr.app.api.user.CreateUserCommand;
import nl.gridshore.companyhr.app.api.user.UserCreatedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jettro Coenradie
 */
public class UserCommandHandlerTest {
    private FixtureConfiguration fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture();
        UserCommandHandler handler = new UserCommandHandler();
        handler.setUserRepository(fixture.createGenericRepository(User.class));
        fixture.registerAnnotatedCommandHandler(handler);
    }

    @Test
    public void createNewUser() {
        fixture.given()
                .when(new CreateUserCommand("12345678", "gridshore", "email@gridshore.nl",false))
                .expectEvents(new UserCreatedEvent("gridshore", "email@gridshore.nl",false));
    }

}
