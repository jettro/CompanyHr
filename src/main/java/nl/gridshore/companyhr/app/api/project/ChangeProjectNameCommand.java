package nl.gridshore.companyhr.app.api.project;

/**
 * @author Jettro Coenradie
 */
public class ChangeProjectNameCommand {
    private String projectNewName;
    private String projectId;

    public ChangeProjectNameCommand(String projectId, String projectNewName) {
        this.projectId = projectId;
        this.projectNewName = projectNewName;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getProjectNewName() {
        return projectNewName;
    }
}
