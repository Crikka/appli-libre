package adullact.publicrowdfunding.model.local.utilities;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Pattern;

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
    private SyncServerToLocal() {
        this.m_projects = new TreeSet<Project>(new Comparator<Project>() {
            @Override
            public int compare(Project project1, Project project2) {
                return project1.getResourceId().compareTo(project2.getResourceId());
            }
        });
    }
    public static SyncServerToLocal getInstance() {
        if(m_instance == null) {
            m_instance = new SyncServerToLocal();
        }

        return m_instance;
    }
    /* ----------------- */

    private TreeSet<Project> m_projects;

    public TreeSet<Project> getProjects() {
        return m_projects;
    }

    public void sync() {
        sync(new NothingToDo<Project>());
    }

    public void sync(final WhatToDo<Project> projectWhatToDo) {
        final DateTime now = DateTime.now();
        ListerEvent<Project> event = new ListerEvent<Project>() {
            @Override
            public void onLister(ArrayList<Project> projects) {
                Account.getOwnOrAnonymous().setLastSync(now);
                m_projects.addAll(projects);
                for(Project project : projects) {
                    projectWhatToDo.hold(project);
                }
                projectWhatToDo.eventually();
            }

            @Override
            public void errorNetwork() {

            }
        };
        if(Account.getOwnOrAnonymous().getLastSync() == null) {
            (new Project()).serverLister(event);
        }
        else {
            (new Project()).serverListerToSync(event, Account.getOwnOrAnonymous().getLastSync());
        }
    }

    private interface Searcher {
        boolean searchTest(Project project, String motif);
    }

    public ArrayList<Project> searchInName(final String motif) {
        return search(motif, new Searcher() {
            @Override
            public boolean searchTest(Project project, String motif) {
                return Pattern.compile(Pattern.quote(motif), Pattern.CASE_INSENSITIVE).matcher(project.getName()).find();
            }
        });
    }

    public ArrayList<Project> searchInDescription(final String motif) {
        return search(motif, new Searcher() {
            @Override
            public boolean searchTest(Project project, String motif) {
                return Pattern.compile(Pattern.quote(motif), Pattern.CASE_INSENSITIVE).matcher(project.getDescription()).find();
            }
        });
    }

    private ArrayList<Project> search(final String motif, Searcher searcher) {
        ArrayList<Project> res = new ArrayList<Project>();

        for(Project project : m_projects) {
            if(searcher.searchTest(project, motif)) {
                res.add(project);
            }
        }

        return res;
    }
}
