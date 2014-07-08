package adullact.publicrowdfunding.shared;

import java.util.Date;

public class Commentary {

    // Meta-data
    private int m_id;
    private Date m_creationDate;

    // Reference
	private User m_user;
	private Project m_project;

    // Content
    private String m_title;
	private String m_message;
	private double m_mark; // Sur 5

    public Commentary(int id, Date creationDate, User user, Project m_project, String title, String message, double mark) {
        super();

        this.m_id = id;
        this.m_creationDate = creationDate;
        this.m_user = user;
        this.m_project = m_project;
        this.m_title = title;
        this.m_message = message;
        this.m_mark = mark;
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

    public String title() {
        return m_title;
    }

    public String message() {
        return m_message;
    }

	public double mark() {
		return m_mark;
	}

}
