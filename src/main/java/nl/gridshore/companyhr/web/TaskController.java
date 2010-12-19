package nl.gridshore.companyhr.web;

import nl.gridshore.companyhr.app.axon.GaeSnapshotter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Jettro Coenradie
 */
@Controller
@RequestMapping("/task")
public class TaskController {
    private final static Logger logger = LoggerFactory.getLogger(TaskController.class);

    private GaeSnapshotter gaeSnapshotter;

    @Autowired
    public TaskController(GaeSnapshotter gaeSnapshotter) {
        this.gaeSnapshotter = gaeSnapshotter;
    }

    @RequestMapping(value = "/snapshot", method = RequestMethod.POST)
    public void handleSnapshotTask(@RequestParam String typeIdentifier, @RequestParam String aggregateIdentifier,
                                   HttpServletResponse response) {
        logger.debug("Received a task to create a snapshot for type {} and aggregate {}", typeIdentifier, aggregateIdentifier);

        gaeSnapshotter.createSnapshot(typeIdentifier, aggregateIdentifier);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
