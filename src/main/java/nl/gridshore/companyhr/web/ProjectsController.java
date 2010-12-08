package nl.gridshore.companyhr.web;

import nl.gridshore.companyhr.app.api.project.ChangeProjectNameCommand;
import nl.gridshore.companyhr.app.api.project.CreateProjectCommand;
import nl.gridshore.companyhr.query.ProjectEntry;
import nl.gridshore.companyhr.query.ProjectEntryProvider;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String create(ModelMap modelMap) {
        modelMap.addAttribute("projectEntry", new ProjectEntry());
        return "project/create";
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String doCreate(ProjectEntry projectEntry, BindingResult result) {
        if (result.hasErrors()) {
            return "project/create";
        }
        commandBus.dispatch(new CreateProjectCommand(projectEntry.getName()));
        return "redirect:/project";
    }

    @RequestMapping(value = "/update/{projectId}",method = RequestMethod.GET)
    public String update(@PathVariable String projectId, ModelMap modelMap) {
        ProjectEntry projectEntry = projectEntryProvider.obtainProject(projectId);
        modelMap.addAttribute("projectEntry", projectEntry);
        return "project/update";
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String doUpdate(ProjectEntry projectEntry, BindingResult result) {
        if (result.hasErrors()) {
            return "project/update";
        }
        commandBus.dispatch(new ChangeProjectNameCommand(projectEntry.getIdentifier() ,projectEntry.getName()));
        return "redirect:/project";
    }

}
