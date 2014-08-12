package adullact.publicrowdfunding.model.local.ressource;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Map;

import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.server.entities.ServerCommentary;
import adullact.publicrowdfunding.model.server.entities.Service;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.shared.Utility;
import rx.Observable;

public class Commentary extends Resource<Commentary, ServerCommentary, ServerCommentary>{

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

    public Commentary() {
        super();
    }

    public Commentary(User user, Project project, String title, String message, double mark) {
        super();

        this.m_creationDate = DateTime.now();
        this.m_user = user.getCache();
        this.m_project = project.getCache();
        this.m_title = title;
        this.m_message = message;
        this.m_mark = mark;
    }

    /* --- Resource --- */
    @Override
    public String getResourceId() {
        return Integer.toString(m_id);
    }

    @Override
    protected void setResourceId(String id) {
        m_id = Integer.parseInt(id);
    }

    @Override
    public ServerCommentary toServerResource() {
        ServerCommentary serverCommentary = new ServerCommentary();
        serverCommentary.id = m_id;
        serverCommentary.title = m_title;
        serverCommentary.message = m_message;
        serverCommentary.mark = m_mark;
        serverCommentary.username = m_user.getResourceId();
        serverCommentary.projectID = m_project.getResourceId();
        serverCommentary.creationDate = Utility.DateTimeToString(m_creationDate);

        return serverCommentary;
    }

    @Override
    public Commentary makeCopyFromServer(ServerCommentary serverCommentary) {
        Commentary commentary = new Commentary();
        commentary.m_id = serverCommentary.id;
        commentary.m_title = serverCommentary.title;
        commentary.m_message = serverCommentary.message;
        commentary.m_mark = serverCommentary.mark;
        commentary.m_user = new User().getCache(serverCommentary.username);
        commentary.m_project = new Project().getCache(serverCommentary.projectID);
        commentary.m_creationDate = Utility.stringToDateTime(serverCommentary.creationDate);

        return commentary;
    }

    @Override
    public Commentary syncFromServer(ServerCommentary serverCommentary) {
        this.m_id = serverCommentary.id;
        this.m_title = serverCommentary.title;
        this.m_message = serverCommentary.message;
        this.m_mark = serverCommentary.mark;
        this.m_user = new User().getCache(serverCommentary.username);
        this.m_project = new Project().getCache(serverCommentary.projectID);
        this.m_creationDate = Utility.stringToDateTime(serverCommentary.creationDate);

        return this;
    }

    @Override
    public Observable<ServerCommentary> methodGET(Service service) {
        return service.detailCommentary(getResourceId());
    }

    @Override
    public Observable<ArrayList<ServerCommentary>> methodGETAll(Service service, Map<String, String> filter) {
        return service.listCommentaries(filter);
    }

    @Override
    public Observable<SimpleServerResponse> methodPUT(Service service) {
        return service.modifyCommentary(getResourceId(), toServerResource());
    }

    @Override
    public Observable<SimpleServerResponse> methodPOST(Service service) {
        return service.createCommentary(toServerResource());
    }

    @Override
    public Observable<SimpleServerResponse> methodDELETE(Service service) {
        return service.deleteCommentary(getResourceId());
    }
    /* ---------------- */

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
        m_user.toResource(userWhatToDo);
    }

    public void getProject(WhatToDo<Project> projectWhatToDo) {
        m_project.toResource(projectWhatToDo);
    }
}
