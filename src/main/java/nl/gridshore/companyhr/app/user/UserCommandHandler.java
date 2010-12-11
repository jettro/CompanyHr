package nl.gridshore.companyhr.app.user;

import nl.gridshore.companyhr.app.api.user.CreateUserCommand;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jettro Coenradie
 */
@Component
public class UserCommandHandler {
    private Repository<User> userRepository;

    @CommandHandler
    public void createNewUser(CreateUserCommand command) {
        userRepository.add(
                new User(command.getUserName(), command.getEmail(), command.getDisplayname(),command.isAdministrator()));
    }

    @Autowired
    public void setUserRepository(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }
}
