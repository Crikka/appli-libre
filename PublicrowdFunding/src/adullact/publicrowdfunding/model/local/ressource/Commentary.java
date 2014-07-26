package adullact.publicrowdfunding.model.local.ressource;

import org.joda.time.DateTime;

import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;

public class Commentary {

    /* ---- Own data ---- */
    private int m_id;
    private DateTime m_creationDate;
    private String m_title;
    private String m_message;
    private double m_mark; // over 5
   /* ------------------ */

    /* --- References --- */
    private Cache<User> m_user;
    private Cache<Project> m_project;
    /* ------------------ */

    public Commentary(int id, DateTime creationDate, User user, Project m_project, String title, String message, double mark) {
        super();

        this.m_id = id;
        this.m_creationDate = creationDate;
        this.m_user = new Cache<User>(user);
        this.m_project = new Cache<Project>(m_project);
        this.m_title = title;
        this.m_message = message;
        this.m_mark = mark;
    }

    public int getId() {
        return m_id;
    }

    public DateTime getCreationDate() {
        return m_creationDate;
    }


    public String getTitle() {
        return m_title;
    }

    public String getMessage() {
        return m_message;
    }

    public double getMark() {
        return m_mark;
    }

    public void getUser(WhatToDo<User> userWhatToDo) {
        m_user.toData(userWhatToDo);
    }

    public void getProject(WhatToDo<Project> projectWhatToDo) {
        m_project.toData(projectWhatToDo);
    }

}
