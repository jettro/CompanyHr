package nl.gridshore.companyhr.app.api.user;

/**
 * @author Jettro Coenradie
 */
public class CreateUserCommand {
    private String userName;
    private String email;
    private String displayname;

    public CreateUserCommand(String userName, String displayname, String email) {
        this.displayname = displayname;
        this.email = email;
        this.userName = userName;
    }

    public String getDisplayname() {
        return displayname;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }
}
