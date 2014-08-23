package adullact.publicrowdfunding.model.local.ressource;

import java.util.ArrayList;
import java.util.Map;

import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.cache.CacheSet;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.server.entities.DetailedServerUser;
import adullact.publicrowdfunding.model.server.entities.RowAffected;
import adullact.publicrowdfunding.model.server.entities.ServerBookmark;
import adullact.publicrowdfunding.model.server.entities.ServerCommentary;
import adullact.publicrowdfunding.model.server.entities.ServerFunding;
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
	private String m_city;
	private String m_sexe;
	private CacheSet<Bookmark> m_bookmark;
	private CacheSet<Funding> m_funding;

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
        serverUser.city = this.m_city;
        serverUser.sexe = this.m_sexe;
    
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
        m_city = serverUser.city;
        m_sexe = serverUser.sexe;
		m_bookmark = new CacheSet<Bookmark>();
		m_funding = new CacheSet<Funding>();
        
        return this;
    }

    @Override
    public User syncFromServer(DetailedServerUser detailedServerUser) {
        makeCopyFromServer(detailedServerUser);

        for(final ServerBookmark serverBookmark : detailedServerUser.bookmarkedProjects) {
            final Cache<Bookmark> bookmarks = new Bookmark().getCache(Integer.toString(serverBookmark.id)).declareUpToDate();
            bookmarks.toResource(new HoldToDo<Bookmark>() {
                @Override
                public void hold(Bookmark resource) {
                    resource.syncFromServer(serverBookmark);
                    m_bookmark.add(bookmarks);
                }
            });
        }
        
        for(final ServerFunding serverFunding : detailedServerUser.fundedProjects) {
            final Cache<Funding> fundings = new Funding().getCache(Integer.toString(serverFunding.id)).declareUpToDate();
            fundings.toResource(new HoldToDo<Funding>() {
                @Override
                public void hold(Funding resource) {
                    resource.syncFromServer(serverFunding);
                    m_funding.add(fundings);
                }
            });
        }
        
        
        
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
    public Observable<RowAffected> methodPOST(Service service){
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
		this.m_city = null;
		this.m_sexe = null;

	}

    public User(String pseudo, String name, String firstName, String city, String sexe) {
        this.m_pseudo = pseudo;
        this.m_name = name;
        this.m_firstName = firstName;
        this.m_city = city;
        this.m_sexe = sexe;

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
	
	public String getCity() {
		return m_city;
	}
	
	public String getSexe(){
		return m_sexe;
	}
	
	public void getBookmarkedProjects(final WhatToDo<Bookmark> projectWhatToDo) {
	      m_bookmark.forEach(projectWhatToDo);
		/*
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
        });*/
	}
	
	public void getFundingProjects(final WhatToDo<Funding> projectWhatToDo) {
	      m_funding.forEach(projectWhatToDo);
	}
	      
	/*
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
    */
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
    
    public void setCity(String city){
    	m_city = city;
    }
    
    public void setSexe(String sexe){
    	m_sexe = sexe;
    }
    /* ------- */
    
    public String toString(){
    	return "Pseudo:"+m_pseudo+
    			"Name:"+m_name+
    			"First Name:"+m_firstName;
    }
}
