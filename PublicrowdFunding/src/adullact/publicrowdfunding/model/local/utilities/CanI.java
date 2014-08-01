package adullact.publicrowdfunding.model.local.utilities;

import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;

/**
 * Created by Ferrand on 29/07/2014.
 */
public abstract class CanI {
    public void validate(Project project) {
        if(Account.getOwnOrAnonymous().isAdmin()){
            yes();
        }
        else {
            no();
        }
    }

    public boolean modify(User user) {
        return true;
    }

    protected abstract void yes();
    protected abstract void no();
}
