package adullact.publicrowdfunding.model.local.cache;

import android.nfc.Tag;

import java.util.HashMap;
import java.util.Map;

import adullact.publicrowdfunding.model.local.ressource.Bookmark;
import adullact.publicrowdfunding.model.local.ressource.Commentary;
import adullact.publicrowdfunding.model.local.ressource.Funding;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;

/**
 * Created by Ferrand on 21/07/2014.
 */
public class CacheManager {
        /* Singleton */
        private static CacheManager m_instance = null;
        private CacheManager() {
            m_users = new HashMap<String, Cache<User>>();
            m_projects = new HashMap<String, Cache<Project>>();
            m_funding = new HashMap<String, Cache<Funding>>();
        }
        public static CacheManager getInstance() {
            if (m_instance == null) {
                m_instance = new CacheManager();
            }
            return m_instance;
        }
	/* --------- */

    private HashMap<String, Cache<User>> m_users;
    private HashMap<String, Cache<Project>> m_projects;
    private HashMap<String, Cache<Funding>> m_funding;

    public Cache<Project> getProjectById(String id) {
        Cache<Project> res = m_projects.get(id);
        if(res == null) {
            res = new Cache<Project>(new Project().fromResourceId(id)).forceRetrieve();
        }

        return res;
    }

    public Cache<User> getUserById(String id) {
        Cache<User> res = m_users.get(id);
        if(res == null) {
            res = new Cache<User>(new User().fromResourceId(id)).forceRetrieve();
        }

        return res;
    }

    public Cache<Funding> getFundingById(String id) {
        Cache<Funding> res = m_funding.get(id);
        if(res == null) {
            res = new Cache<Funding>(new Funding().fromResourceId(id)).forceRetrieve();
        }

        return res;
    }
        /*public HashMap<Integer, Cache<Tag>> tags;
        public HashMap<Integer, Cache<Commentary>> commentaries;
        public HashMap<Integer, Cache<Bookmark>> bookmarks;*/

        /*public void refreshAll() {
            refreshUsers();
            refreshProjects();
            refreshTags();
            refreshCommentaries();
            refreshBookmarks();
        }

        public void refreshUsers() {
            for(Map.Entry<String, Cache<User>> entry : users.entrySet()) {
                entry.getValue().forceRefresh();
            }
        }

        public void refreshUser(String pseudo) {
            Cache<User> user = users.get(pseudo);
            if(user != null) {
                user.forceRefresh();
            }
        }

        public void refreshProjects() {
            for(Map.Entry<String, Cache<Project>> entry : projects.entrySet()) {
                entry.getValue().forceRefresh();
            }
        }

        public void refreshProject(String id) {
            Cache<Project> project = projects.get(id);
            if(project != null) {
                project.forceRefresh();
            }
        }

        public void refreshTags() {
            for(Map.Entry<String, Cache<Tag>> entry : tags.entrySet()) {
                entry.getValue().forceRefresh();
            }
        }

        public void refreshTag(Integer id) {
            Cache<Tag> tag = tags.get(id);
            if(tag != null) {
                tag.forceRefresh();
            }
        }

        public void refreshCommentaries() {
            for(Map.Entry<String, Cache<Commentary>> entry : commentaries.entrySet()) {
                entry.getValue().forceRefresh();
            }
        }

        public void refreshCommentary(Integer id) {
            Cache<Commentary> commentary = commentaries.get(id);
            if(commentary != null) {
                commentary.forceRefresh();
            }
        }

        public void refreshBookmarks() {
            for(Map.Entry<String, Cache<Bookmark>> entry : bookmarks.entrySet()) {
                entry.getValue().forceRefresh();
            }
        }

        public void refreshBookmark(Integer id) {
            Cache<Bookmark> bookmark = bookmarks.get(id);
            if(bookmark != null) {
                bookmark.forceRefresh();
            }
        }*/
}
