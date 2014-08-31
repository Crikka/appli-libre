package adullact.publicrowdfunding.model.local.utilities;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.regex.Pattern;

import adullact.publicrowdfunding.model.local.callback.NothingToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.database.ProjectsDatabase;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.server.event.ListerEvent;
import rx.Scheduler;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

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

        ProjectsDatabase projectsDatabase = ProjectsDatabase.getInstance();
        ArrayList<Project> projects = projectsDatabase.get();

        for(Project project : projects) {
            m_projects.add(project);
        }
    }
    public static SyncServerToLocal getInstance() {
        if(m_instance == null) {
            m_instance = new SyncServerToLocal();
        }

        return m_instance;
    }
    /* ----------------- */

    private TreeSet<Project> m_projects;
    private Searcher m_currentSearcher;
    private Comparator<Project> m_currentComparator;

    public TreeSet<Project> getProjects() {
        return m_projects;
    }

    public void sync() {
        sync(new NothingToDo<Project>());
    }

    public void sync(final WhatToDo<Project> projectWhatToDo) {
        final DateTime now = DateTime.now();
        final SyncServerToLocal _this = this;
        ListerEvent<Project> event = new ListerEvent<Project>() {
            @Override
            public void onLister(ArrayList<Project> projects) {
                Account.getOwnOrAnonymous().setLastSync(now);

                final ArrayList<Project> newProjects = new ArrayList<Project>();
                final ArrayList<Project> updatedProjects = new ArrayList<Project>();
                final ArrayList<Project> deletedProject = new ArrayList<Project>();

                for(Project project : projects) {
                    if(project.isActive()) {
                        if(m_projects.contains(project)) {
                            updatedProjects.add(project);
                        }
                        else {
                            newProjects.add(project);
                        }
                    }
                    else {
                        deletedProject.add(project);
                    }
                    projectWhatToDo.hold(project);
                }

                m_projects.addAll(projects);
                _this.syncLocalDatabase(newProjects, updatedProjects, deletedProject);
                projectWhatToDo.eventually();
            }

            @Override
            public void errorNetwork() {
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

    private void syncLocalDatabase(final ArrayList<Project> newProjects, final ArrayList<Project> updatedProjects, final ArrayList<Project> deletedProjects) {
        Scheduler.Worker worker = Schedulers.io().createWorker();
        worker.schedule(new Action0() {

            @Override
            public void call() {
                ProjectsDatabase projectsDatabase = ProjectsDatabase.getInstance();
                for(Project project : newProjects) {
                    projectsDatabase.put(project);
                }
                for(Project project : updatedProjects) {
                    projectsDatabase.update(project);
                }
                for(Project project : deletedProjects) {
                    projectsDatabase.delete(project);
                }
            }

        });
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
        m_currentSearcher = searcher;
        ArrayList<Project> res = new ArrayList<Project>();

        for(Project project : m_projects) {
            if(searcher.searchTest(project, motif)) {
                res.add(project);
            }
        }

        return res;
    }

    public ArrayList<Project> sortByRequestingProjectMaxToMin() {
        return sort(new Comparator<Project>() {
            @Override
            public int compare(Project lhs, Project rhs) {
                return rhs.getRequestedFundingLongValue().compareTo(lhs.getRequestedFundingLongValue());
            }
        });
    }

    public ArrayList<Project> sortByRequestingProjectMinToMax() {
        return sort(new Comparator<Project>() {
            @Override
            public int compare(Project lhs, Project rhs) {
                return lhs.getRequestedFundingLongValue().compareTo(rhs.getRequestedFundingLongValue());
            }
        });
    }

    public ArrayList<Project> sortByAlmostFunded() {
        return sort(new Comparator<Project>() {
            @Override
            public int compare(Project lhs, Project rhs) {
                return rhs.getPercentOfAchievement().compareTo(lhs.getPercentOfAchievement());
            }
        });

    }

    public ArrayList<Project> sortByProximity() {
        return sort(new Comparator<Project>() {
            @Override
            public int compare(Project lhs, Project rhs) {
                return Calcul.distance(Share.position, rhs.getPosition()).compareTo(Calcul.distance(Share.position, lhs.getPosition()));
            }
        });

    }

    private ArrayList<Project> sort(final Comparator<Project> sorter) {
        m_currentComparator = sorter;
        ArrayList<Project> res = new ArrayList<Project>(m_projects);

        Collections.sort(res, sorter);

        return res;
    }
}
