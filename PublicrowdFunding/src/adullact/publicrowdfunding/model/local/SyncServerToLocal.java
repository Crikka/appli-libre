package adullact.publicrowdfunding.model.local;

import org.joda.time.DateTime;

import java.util.ArrayList;

import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.NothingToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.server.event.ListerEvent;

/**
 * Created by Ferrand on 19/07/2014.
 */
public class SyncServerToLocal {
    /* --- Singleton --- */
    private static SyncServerToLocal m_instance = null;
    private SyncServerToLocal() {}
    public static SyncServerToLocal getInstance() {
        if(m_instance == null) {
            m_instance = new SyncServerToLocal();
        }

        return m_instance;
    }
    /* ----------------- */

    public void sync() throws NoAccountExistsInLocal {
        sync(new NothingToDo<Project>());
    }

    public void sync(final WhatToDo<Project> projectWhatToDo) {
        final DateTime now = DateTime.now();
        ListerEvent<Project> event = new ListerEvent<Project>() {
            @Override
            public void onLister(ArrayList<Project> projects) {
                Account.getOwnOrAnonymous().setLastSync(now);
                for(Project project : projects) {
                    projectWhatToDo.hold(project);
                }
                projectWhatToDo.eventually();
            }

        };
        if(Account.getOwnOrAnonymous().getLastSync() == null) {
            (new Project()).serverLister(event);
        }
        else {
            (new Project()).serverListerToSync(event, Account.getOwnOrAnonymous().getLastSync());
        }
    }

    private void merge(ArrayList<Project> projects) {
        /*HashMap<String, Project> arrangement = new HashMap<String, Project>((m_importedProjects.size()+projects.size())*1.4);
        Project currentProject;
        for(Project project : m_importedProjects) {
            arrangement.put(project.getId(), project);
        }
        for(Project project : projects) {
            currentProject = arrangement.get(project.getId()));
            if(currentProject == null) {
                m_newProjects.add(project);
                arrangement.put(project.getId(), project);
            }
            else {
                m_upgradedProjects.add(project);
            }
        }

        m_importedProjects = new ArrayList<Project>(arrangement.values());*/
    }
}
