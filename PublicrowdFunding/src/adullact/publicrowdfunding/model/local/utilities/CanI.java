package adullact.publicrowdfunding.model.local.utilities;

import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;

/**
 * @author Ferrand and Nelaupe
 */
public abstract class CanI {
    public void validate(Project project) {
        try {
            Account.getOwn();
            if(Account.getOwnOrAnonymous().isAdmin()){
                yes();
            }
            else {
                no();
            }
        } catch (NoAccountExistsInLocal noAccountExistsInLocal) {
            no();
        }
    }

    public void modify(User user) {
        try {
            Account account = Account.getOwn();
            if(account.isAdmin() || account.getPseudo().equals(user.getPseudo())) {
                yes();
            }
            else {
                no();
            }
        } catch (NoAccountExistsInLocal noAccountExistsInLocal) {
            no();
        }
    }

    public void modify(Project project) {
        try {
            Account account = Account.getOwn();
            if(account.isAdmin() || account.getPseudo().equals(project.getUser().getResourceId())) {
                yes();
            }
            else {
                no();
            }
        } catch (NoAccountExistsInLocal noAccountExistsInLocal) {
            no();
        }
    }

    public void bookmark(final Project project) {
        try {
            Account account = Account.getOwn();
            account.getUser().toResource(new HoldToDo<User>() {

                @Override
                public void hold(User resource) {
                    resource.getBookmarkedProjects(new WhatToDo<Project>() {
                        private boolean isBookmarked = false;

                        @Override
                        public void hold(Project resource) {
                            if((resource.getResourceId().equals(project.getResourceId()))) {
                                isBookmarked = true;
                            }
                        }

                        @Override
                        public void eventually() {
                            if(isBookmarked) {
                                no();
                            }
                            else {
                                yes();
                            }
                        }
                    });
                }
            });
        } catch (NoAccountExistsInLocal noAccountExistsInLocal) {
            no();
        }
    }

    protected abstract void yes();
    protected abstract void no();
}
