package nl.gridshore.companyhr.query;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jettro Coenradie
 */
@Component
public class ProjectEntryProvider {
    private ObjectifyFactory objectifyFactory;

    public List<ProjectEntry> listAllProjects() {

        Query<ProjectEntry> query = objectifyFactory.begin().query(ProjectEntry.class);
        return query.list();
    }


    @Autowired
    public void setObjectifyFactory(ObjectifyFactory objectifyFactory) {
        this.objectifyFactory = objectifyFactory;
    }

}
