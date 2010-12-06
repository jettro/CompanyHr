package nl.gridshore.companyhr.app.api.project;

/**
 * @author Jettro Coenradie
 */
public class CreateProjectCommand {
    private String newProjectName;

    public CreateProjectCommand(String newProjectName) {
        this.newProjectName = newProjectName;
    }

    public String newProjectName() {
        return newProjectName;
    }
}
