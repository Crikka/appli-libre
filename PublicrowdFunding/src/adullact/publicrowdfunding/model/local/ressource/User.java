package adullact.publicrowdfunding.model.local.ressource;

import java.util.ArrayList;
import java.util.Map;

import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.server.entities.DetailedServerUser;
import adullact.publicrowdfunding.model.server.entities.ServerUser;
import adullact.publicrowdfunding.model.server.entities.Service;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.model.server.event.ListerEvent;
import rx.Observable;

/**
 * 
 * @author Ferrand
 * 
 */
public class User extends Resource<User, ServerUser, DetailedServerUser> {
	private String m_pseudo;
	private String m_name;
	private String m_firstName;

    /* ----- Resource ----- */
    @Override
    public String getResourceId() {
        return m_pseudo;
    }

    @Override
    public void setResourceId(String id) {
        this.m_pseudo = id;
    }

    @Override
    public ServerUser toServerResource() {
        ServerUser serverUser = new ServerUser();
        serverUser.pseudo = getResourceId();
        serverUser.mail = "";
        serverUser.name = this.m_name;
        serverUser.firstName = this.m_firstName;

        return serverUser;
    }

    @Override
    public User makeCopyFromServer(ServerUser serverUser) {
        if(((m_pseudo != serverUser.pseudo) || (m_name != serverUser.name) || (m_firstName != serverUser.firstName))) {
            changed();
        }

        m_pseudo = serverUser.pseudo;
        m_name = serverUser.name;
        m_firstName = serverUser.firstName;

        return this;
    }

    @Override
    public User syncFromServer(DetailedServerUser detailedServerUser) {
        makeCopyFromServer(detailedServerUser);

        return this;
    }

    /* ------ Method ------ */
    @Override
    public Observable<DetailedServerUser> methodGET(Service service) {
        return service.detailUser(m_pseudo);
    }

    @Override
    public Observable<ArrayList<ServerUser>> methodGETAll(Service service, Map<String, String> filter) {
        return service.listUsers(filter);
    }

    @Override
    public Observable<SimpleServerResponse> methodPUT(Service service) {
        return service.modifyUser(toServerResource(), m_pseudo);
    }

    @Override
    public Observable<SimpleServerResponse> methodPOST(Service service){
        return service.createUser(toServerResource());
    }

    @Override
    public Observable<SimpleServerResponse> methodDELETE(Service service) {
        return service.deleteUser(getResourceId());
    }
    /* -------------------- */
	
	public User() {
		this.m_pseudo = null;
		this.m_name = null;
		this.m_firstName = null;
	}

    public User(String pseudo, String name, String firstName) {
        this.m_pseudo = pseudo;
        this.m_name = name;
        this.m_firstName = firstName;
    }

	/* Getter */
	public String getName() {
		return m_name;
	}

	public String getFirstName() {
		return m_firstName;
	}

	public String getPseudo() {
		return m_pseudo;
	}
	
	public void getBookmarkedProjects(final WhatToDo<Project> projectWhatToDo) {
        new Bookmark().serverListerByUser(m_pseudo, new ListerEvent<Bookmark>() {
            @Override
            public void onLister(ArrayList<Bookmark> bookmarks) {
                for (Bookmark bookmark : bookmarks) {
                    bookmark.getProject(projectWhatToDo);
                }
            }

            @Override
            public void errorNetwork() {

            }
        });
	}

    public void getFinancedProjects(final WhatToDo<Project> projectWhatToDo) {
        new Funding().serverListerByUser(m_pseudo, new ListerEvent<Funding>() {
            @Override
            public void onLister(ArrayList<Funding> funding) {
                for (Funding fund : funding) {
                    fund.getProject(projectWhatToDo);
                }
            }

            @Override
            public void errorNetwork() {

            }
        });
    }
	/* ------ */


	/* Setters */
    public void setPseudo(String pseudo) {
        m_pseudo = pseudo;
    }

    public void setName(String name){
        m_name = name;
    }

    public void setFirstName(String firstName) {
        m_firstName = firstName;
    }
    /* ------- */
    
    public String toString(){
    	return "Pseudo:"+m_pseudo+
    			"Name:"+m_name+
    			"First Name:"+m_firstName;
    }
}
