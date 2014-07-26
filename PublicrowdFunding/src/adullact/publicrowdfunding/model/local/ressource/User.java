package adullact.publicrowdfunding.model.local.ressource;

import java.util.ArrayList;
import java.util.Map;

import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.server.entities.DetailedServerUser;
import adullact.publicrowdfunding.model.server.entities.ServerUser;
import adullact.publicrowdfunding.model.server.entities.Service;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import rx.Observable;

/**
 * TODO
 * 
 * @author Ferrand
 * 
 */
public class User extends Resource<User, ServerUser, DetailedServerUser> {
	private String m_pseudo;
	private String m_name;
	private String m_firstName;
	private boolean m_authenticated;
    private boolean m_administrator;
	private ArrayList<Cache<Project>> m_supportedProjects;
	private ArrayList<Cache<Project>> m_financedProjects;

    /* ----- Resource ----- */
    @Override
    public String getResourceId() {
        return m_pseudo;
    }

    @Override
    public ServerUser toServerResource() {
        return null;
    }

    @Override
    public User fromServerResource(ServerUser serverUser) {
        return null;
    }

    @Override
    public User fromDetailedServerResource(DetailedServerUser detailedServerUser) {
        return null;
    }

    @Override
    public Observable<SimpleServerResponse> methodDELETE(Service service) {
        return null;
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
    public Observable<SimpleServerResponse> methodPOST(Service service) {
        return service.createUser(toServerResource());
    }
    /* -------------------- */
    /* -------------------- */
	
	public User() {
		this.m_pseudo = null;
		this.m_name = null;
		this.m_firstName = null;
		this.m_authenticated = false;
        this.m_administrator = false;
		this.m_supportedProjects = new ArrayList<Cache<Project>>();
		this.m_financedProjects = new ArrayList<Cache<Project>>();
	}

    public User(String pseudo, String name, String firstName) {
        this.m_pseudo = pseudo;
        this.m_name = name;
        this.m_firstName = firstName;
        this.m_authenticated = false;
        this.m_administrator = false;
        this.m_supportedProjects = new ArrayList<Cache<Project>>();
        this.m_financedProjects = new ArrayList<Cache<Project>>();
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
	
	public void getSupportedProjects(WhatToDo<Project> projectWhatToDo) {
        for(Cache<Project> project : m_supportedProjects) {
            project.toData(projectWhatToDo);
        }
	}

    public void getFinancedProjects(WhatToDo<Project> projectWhatToDo) {
        for(Cache<Project> project : m_financedProjects) {
            project.toData(projectWhatToDo);
        }
    }
	/* ------ */
	

	public boolean isAuthenticated() {
		return m_authenticated;
	}

    public void authenticate() {
        m_authenticated = true;
    }

	public void authenticate(boolean administrator) {
        m_administrator = administrator;
		m_authenticated = true;
	}
	
	/**
     * Setter for all fiels.
	 * @param pseudo
	 * @param name
	 * @param firstName
	 */
	public void defineFields(String pseudo, String name, String firstName) {
		this.m_pseudo = pseudo;
		this.m_name = name;
		this.m_firstName = firstName;
	}

	public boolean isAdmin() {
		return m_administrator;
	}
}
