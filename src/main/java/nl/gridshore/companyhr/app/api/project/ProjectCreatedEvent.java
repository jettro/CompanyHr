package nl.gridshore.companyhr.app.api.project;

import org.axonframework.domain.DomainEvent;

/**
 * @author Jettro Coenradie
 */
public class ProjectCreatedEvent extends DomainEvent {
    private String newProjectName;

    public String getProjectId() {
        return getAggregateIdentifier().asString();
    }

    public ProjectCreatedEvent(String newProjectName) {
        this.newProjectName = newProjectName;
    }

    public String newProjectName() {
        return newProjectName;
    }
}
