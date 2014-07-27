package adullact.publicrowdfunding.model.local.ressource;

import org.joda.time.DateTime;

import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;

/**
 * Created by Ferrand on 08/07/2014.
 */
public class Bookmark {

    /* ---- Own data ---- */
    private int m_id;
    private DateTime m_creationDate;
   /* ------------------ */

    /* --- References --- */
    private Cache<User> m_user;
    private Cache<Project> m_project;
    /* ------------------ */

    public Bookmark(int id, DateTime creationDate, User user, Project m_project) {
        super();

        this.m_id = id;
        this.m_creationDate = creationDate;
        this.m_user = new Cache<User>(user);
        this.m_project = new Cache<Project>(m_project);
    }

    public int id() {
        return m_id;
    }

    public DateTime creationDate() {
        return m_creationDate;
    }

    public void getUser(WhatToDo<User> userWhatToDo) {
        m_user.toResource(userWhatToDo);
    }

    public void getProject(WhatToDo<Project> projectWhatToDo) {
        m_project.toResource(projectWhatToDo);
    }

}
