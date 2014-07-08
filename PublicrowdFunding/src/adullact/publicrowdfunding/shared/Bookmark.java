package adullact.publicrowdfunding.shared;

import java.util.Date;

/**
 * Created by ferrand on 08/07/2014.
 */
public class Bookmark {

    // Meta-data
    private int m_id;
    private Date m_creationDate;

    // Reference
    private User m_user;
    private Project m_project;

    public Bookmark(int id, Date creationDate, User user, Project m_project) {
        super();

        this.m_id = id;
        this.m_creationDate = creationDate;
        this.m_user = user;
        this.m_project = m_project;
    }

    public int id() {
        return m_id;
    }

    public Date creationDate() {
        return m_creationDate;
    }

    public User user() {
        return m_user;
    }

    public Project project() {
        return m_project;
    }

}
