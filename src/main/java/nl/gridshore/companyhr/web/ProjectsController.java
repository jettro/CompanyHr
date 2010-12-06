package nl.gridshore.companyhr.web;

import nl.gridshore.companyhr.app.api.project.CreateProjectCommand;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Jettro Coenradie
 */
@Controller
@RequestMapping("/project")
public class ProjectsController {

    private CommandBus commandBus;

    @Autowired
    public ProjectsController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "project/list";
    }

    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public String create(@RequestParam String name) {
        commandBus.dispatch(new CreateProjectCommand(name));
        return "project/create";
    }

}
