package nl.gridshore.companyhr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Jettro Coenradie
 */
@Controller
@RequestMapping("/project")
public class ProjectsController {

    @RequestMapping(method = RequestMethod.GET)
    public String list() {

        return "project/list";
    }
}
