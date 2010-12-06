package nl.gridshore.companyhr.app.project;

import nl.gridshore.companyhr.app.api.project.CreateProjectCommand;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jettro Coenradie
 */
@Component
public class ProjectCommandHandler {
    private Repository<Project> projectRepository;

    @CommandHandler
    public void createNewProject(CreateProjectCommand command) {
        projectRepository.add(new Project(command.newProjectName()));
    }

    @Autowired
    public void setProjectRepository(Repository<Project> projectRepository) {
        this.projectRepository = projectRepository;
    }
}
