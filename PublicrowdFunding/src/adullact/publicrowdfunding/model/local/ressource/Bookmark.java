package adullact.publicrowdfunding.model.local.ressource;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Map;

import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.server.entities.ServerBookmark;
import adullact.publicrowdfunding.model.server.entities.Service;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.shared.Utility;
import rx.Observable;

/**
 * Created by Ferrand on 08/07/2014.
 */
public class Bookmark extends Resource<Bookmark, ServerBookmark, ServerBookmark> {

    /* ---- Own data ---- */
    private int m_id;
    private DateTime m_creationDate;
   /* ------------------ */

    /* --- References --- */
    private Cache<User> m_user;
    private Cache<Project> m_project;
    /* ------------------ */

    public Bookmark() {
        super();

    }


    public Bookmark(int id, DateTime creationDate, User user, Project project) {
        super();

        this.m_id = id;
        this.m_creationDate = creationDate;
        this.m_user = user.getCache();
        this.m_project = project.getCache();
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

    /* --- Resource --- */
    @Override
    public String getResourceId() {
        return Integer.toString(m_id);
    }

    @Override
    protected void setResourceId(String id) {
        this.m_id = Integer.parseInt(id);
    }

    @Override
    public ServerBookmark toServerResource() {
        ServerBookmark serverBookmark = new ServerBookmark();
        serverBookmark.id = m_id;
        serverBookmark.username = m_user.getResourceId();
        serverBookmark.projectID = m_project.getResourceId();
        serverBookmark.creationDate = Utility.DateTimeToString(m_creationDate);

        return serverBookmark;
    }

    @Override
    public Bookmark makeCopyFromServer(ServerBookmark serverBookmark) {
        Bookmark bookmark = new Bookmark();
        bookmark.m_id = serverBookmark.id;
        bookmark.m_user = new User().getCache(serverBookmark.username);
        bookmark.m_project = new Project().getCache(serverBookmark.projectID);
        bookmark.m_creationDate = Utility.stringToDateTime(serverBookmark.creationDate);

        return bookmark;
    }

    @Override
    public Bookmark syncFromServer(ServerBookmark serverBookmark) {
        this.m_id = serverBookmark.id;
        this.m_user = new User().getCache(serverBookmark.username);
        this.m_project = new Project().getCache(serverBookmark.projectID);
        this.m_creationDate = Utility.stringToDateTime(serverBookmark.creationDate);

        return this;
    }

    @Override
    public Observable<ServerBookmark> methodGET(Service service) {
        return service.detailBookmark(getResourceId());
    }

    @Override
    public Observable<ArrayList<ServerBookmark>> methodGETAll(Service service, Map<String, String> filter) {
        return service.listBookmarks(filter);
    }

    @Override
    public Observable<SimpleServerResponse> methodPUT(Service service) {
        return service.modifyBookmark(getResourceId(), toServerResource());
    }

    @Override
    public Observable<SimpleServerResponse> methodPOST(Service service) {
        return service.createBookmark(toServerResource());
    }

    @Override
    public Observable<SimpleServerResponse> methodDELETE(Service service) {
        return service.deleteBookmark(getResourceId());
    }
    /* ------------ */
}
