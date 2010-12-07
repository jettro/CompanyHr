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

    public ProjectEntry() {
        // required by objectify and spring forms
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setName(String name) {
        this.name = name;
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
