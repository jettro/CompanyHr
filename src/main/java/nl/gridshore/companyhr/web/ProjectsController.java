package nl.gridshore.companyhr.web;

import nl.gridshore.companyhr.app.api.project.CreateProjectCommand;
import nl.gridshore.companyhr.query.ProjectEntryProvider;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    private ProjectEntryProvider projectEntryProvider;

    @Autowired
    public ProjectsController(CommandBus commandBus, ProjectEntryProvider projectEntryProvider) {
        this.commandBus = commandBus;
        this.projectEntryProvider = projectEntryProvider;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(ModelMap modelMap) {
        modelMap.addAttribute("projects",projectEntryProvider.listAllProjects());

        return "project/list";
    }

    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public String create(@RequestParam String name) {
        commandBus.dispatch(new CreateProjectCommand(name));
        return "project/create";
    }

}
