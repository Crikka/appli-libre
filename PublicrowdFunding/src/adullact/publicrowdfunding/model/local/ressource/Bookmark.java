package adullact.publicrowdfunding.model.local.ressource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import rx.Observable;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import adullact.publicrowdfunding.model.server.entities.RowAffected;
import adullact.publicrowdfunding.model.server.entities.ServerBookmark;
import adullact.publicrowdfunding.model.server.entities.Service;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.model.server.event.ListerEvent;
import adullact.publicrowdfunding.model.server.request.ListerRequest;

/**
 * @author Ferrand and Nelaupe
 */
public class Bookmark extends Resource<Bookmark, ServerBookmark, ServerBookmark> {

    /* ---- Own data ---- */
    private Integer m_id;
    private DateTime m_creationDate;
   /* ------------------ */

    /* --- References --- */
    private Cache<User> m_user;
    private Cache<Project> m_project;
    /* ------------------ */

    public Bookmark() {
        super();

    }


    public Bookmark(User user, Project project) {
        super();

        this.m_id = null;
        this.m_creationDate = DateTime.now();
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

    public Cache<User> getUser() {
        return m_user;
    }

    public Cache<Project> getProject() {
        return m_project;
    }

    /* --- Resource --- */
    @Override
    public String getResourceId() {
        if(m_id == null) {
            return null;
        }

        return Integer.toString(m_id);
    }

    @Override
    public void setResourceId(String id) {
        this.m_id = Integer.parseInt(id);
    }

    @Override
    public ServerBookmark toServerResource() {
        ServerBookmark serverBookmark = new ServerBookmark();
        serverBookmark.id = m_id == null ? -1 : m_id;
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
        bookmark.getCache().declareUpToDate();

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
    public Observable<RowAffected> methodPOST(Service service) {
        return service.createBookmark(toServerResource());
    }

    @Override
    public Observable<SimpleServerResponse> methodDELETE(Service service) {
        return service.deleteBookmark(getResourceId());
    }
    /* ------------ */

    public void serverListerByUser(String pseudo, ListerEvent<Bookmark> bookmarkListerEvent) {
        HashMap<String, String> filter = new HashMap<String, String>();
        filter.put("user", pseudo);

        (new ListerRequest<Bookmark, ServerBookmark, ServerBookmark>(this, filter, bookmarkListerEvent)).execute();
    }
}
