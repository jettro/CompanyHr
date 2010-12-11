package nl.gridshore.companyhr.web;

import com.google.appengine.api.users.UserServiceFactory;
import nl.gridshore.companyhr.app.api.project.CreateProjectCommand;
import nl.gridshore.companyhr.app.api.user.CreateUserCommand;
import nl.gridshore.companyhr.query.user.UserEntry;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jettro Coenradie
 */
@Controller
public class SecurityController {
    private CommandBus commandBus;

    @RequestMapping(value = "/logout", method= RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();

        String logoutUrl = UserServiceFactory.getUserService().createLogoutURL("/");

        response.sendRedirect(logoutUrl);
    }

    @RequestMapping(value = "/register", method= RequestMethod.GET)
    public String register(ModelMap modelMap) throws IOException {
        modelMap.addAttribute("userEntry", new UserEntry());
        return "user/register";
    }

    @RequestMapping(value = "/register", method= RequestMethod.POST)
    public String doRegister(UserEntry userEntry, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "user/register";
        }
        UserEntry loggedInUser = (UserEntry) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CreateUserCommand command = new CreateUserCommand(loggedInUser.getUserName(),
                userEntry.getDisplayName(),
                loggedInUser.getEmail(),
                loggedInUser.isAdministrator());
        commandBus.dispatch(command);
        // TODO hier moeten we iets met een FUTURE doen zodat we de nieuwe user terug krijgen
        return "redirect:/project";
    }

    @Autowired
    public void setCommandBus(CommandBus commandBus) {
        this.commandBus = commandBus;
    }
}
