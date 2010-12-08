package nl.gridshore.companyhr.app.project;

import nl.gridshore.companyhr.app.api.project.ChangeProjectNameCommand;
import nl.gridshore.companyhr.app.api.project.CreateProjectCommand;
import nl.gridshore.companyhr.app.api.project.ProjectCreatedEvent;
import nl.gridshore.companyhr.app.api.project.ProjectNameChangedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jettro Coenradie
 */
public class ProjectCommandHandlerTest {

    private FixtureConfiguration fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture();
        ProjectCommandHandler handler = new ProjectCommandHandler();
        handler.setProjectRepository(fixture.createGenericRepository(Project.class));
        fixture.registerAnnotatedCommandHandler(handler);
    }

    @Test
    public void createNewProject() {
        fixture.given()
               .when(new CreateProjectCommand("Name of new project"))
               .expectEvents(new ProjectCreatedEvent("Name of new project"));
    }

    @Test
    public void changeNameProject() {
        fixture.given(new ProjectCreatedEvent("Name of new project"))
               .when(new ChangeProjectNameCommand(fixture.getAggregateIdentifier().asString(),"New name of project"))
               .expectEvents(new ProjectNameChangedEvent("New name of project"));
    }

}
