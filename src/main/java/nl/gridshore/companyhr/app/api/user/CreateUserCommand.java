package nl.gridshore.companyhr.app.api.user;

/**
 * @author Jettro Coenradie
 */
public class CreateUserCommand {
    private String userName;
    private String email;
    private String displayname;
    private boolean administrator;

    public CreateUserCommand(String userName, String displayname, String email, boolean administrator) {
        this.displayname = displayname;
        this.email = email;
        this.userName = userName;
        this.administrator = administrator;
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

    public boolean isAdministrator() {
        return administrator;
    }
}
