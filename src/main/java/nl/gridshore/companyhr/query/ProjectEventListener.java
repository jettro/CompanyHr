package nl.gridshore.companyhr.query;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import nl.gridshore.companyhr.app.api.project.ProjectCreatedEvent;
import nl.gridshore.companyhr.app.api.project.ProjectNameChangedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jettro Coenradie
 */
@Component
public class ProjectEventListener {

    private ObjectifyFactory objectifyFactory;

    @EventHandler
    public void handleCreate(ProjectCreatedEvent event) {
        Objectify objectify = objectifyFactory.beginTransaction();
        try {
            objectify.put(new ProjectEntry(event.getProjectId(),event.newProjectName()));
            objectify.getTxn().commit();
        } finally {
            if (objectify.getTxn().isActive()) {
                objectify.getTxn().rollback();
            }
        }
    }

    @EventHandler
    public void handleChangeProjectName(ProjectNameChangedEvent event) {
        Objectify objectify = objectifyFactory.begin();
        ProjectEntry projectEntry = objectify.query(ProjectEntry.class).filter("identifier =", event.getProjectId()).get();

        Objectify transactional = objectifyFactory.beginTransaction();
        try {
            projectEntry.setName(event.getProjectNewName());
            transactional.put(projectEntry);
            transactional.getTxn().commit();
        } finally {
            if (transactional.getTxn().isActive()) {
                transactional.getTxn().rollback();
            }
        }
    }


    @Autowired
    public void setObjectifyFactory(ObjectifyFactory objectifyFactory) {
        this.objectifyFactory = objectifyFactory;
    }
}
