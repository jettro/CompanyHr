package nl.gridshore.companyhr.web;

import com.google.appengine.api.users.UserServiceFactory;
import nl.gridshore.companyhr.app.api.user.CreateUserCommand;
import nl.gridshore.companyhr.query.user.UserEntry;
import nl.gridshore.companyhr.query.user.UserEntryProvider;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.callbacks.VoidCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger logger = LoggerFactory.getLogger(SecurityController.class);

    private CommandBus commandBus;
    private UserEntryProvider userEntryProvider;

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();

        String logoutUrl = UserServiceFactory.getUserService().createLogoutURL("/");
        response.sendRedirect(logoutUrl);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelMap modelMap) throws IOException {
        modelMap.addAttribute("userEntry", new UserEntry());
        return "user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String doRegister(UserEntry userEntry, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "user/register";
        }
        UserEntry loggedInUser = (UserEntry) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CreateUserCommand command = new CreateUserCommand(loggedInUser.getUserName(),
                userEntry.getDisplayName(),
                loggedInUser.getEmail(),
                loggedInUser.isAdministrator());
        CommandCallback callback = new CreateUserCommandCallback();
        commandBus.dispatch(command,callback);

        //noinspection SpringMVCViewInspection
        return "redirect:/project";
    }

    @Autowired
    public void setCommandBus(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Autowired
    public void setUserEntryProvider(UserEntryProvider userEntryProvider) {
        this.userEntryProvider = userEntryProvider;
    }

    /**
     * Callback used to update the loggedInUser with the new acquired roles and display name
     */
    private class CreateUserCommandCallback extends VoidCallback {

        @Override
        protected void onSuccess() {
            UserEntry loggedInUser = (UserEntry) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserEntry cheangedUserEntry = userEntryProvider.findUser(loggedInUser.getUserName());
            loggedInUser.setAuthorities(cheangedUserEntry.getAuthorities());
            loggedInUser.setDisplayName(cheangedUserEntry.getDisplayName());
        }

        @Override
        public void onFailure(Throwable cause) {
            UserEntry loggedInUser = (UserEntry) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            logger.warn("Problem while registering the user {}",loggedInUser.getDisplayName(),cause);
        }
    }
}
