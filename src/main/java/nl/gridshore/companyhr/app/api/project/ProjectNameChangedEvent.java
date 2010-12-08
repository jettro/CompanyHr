package nl.gridshore.companyhr.app.api.project;

import org.axonframework.domain.DomainEvent;

/**
 * @author Jettro Coenradie
 */
public class ProjectNameChangedEvent extends DomainEvent {
    private String projectNewName;

    public String getProjectId() {
        return getAggregateIdentifier().asString();
    }

    public ProjectNameChangedEvent(String projectNewName) {
        this.projectNewName = projectNewName;
    }

    public String getProjectNewName() {
        return projectNewName;
    }
}
