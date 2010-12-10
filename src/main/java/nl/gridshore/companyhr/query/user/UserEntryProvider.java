package nl.gridshore.companyhr.query.user;

/**
 * @author Jettro Coenradie
 */
public interface UserEntryProvider {
    UserEntry findUser(String userName);
}
