package nl.gridshore.companyhr.query;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Jettro Coenradie
 */
@Entity
public class ProjectEntry {
    private @Id Long id;

    private String identifier;
    private String name;

    public ProjectEntry(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    private ProjectEntry() {
        // required by objectify
    }

    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }
}
